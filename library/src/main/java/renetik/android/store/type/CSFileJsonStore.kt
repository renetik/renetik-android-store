package renetik.android.store.type

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.event.CSBackground
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.onDestructed
import renetik.android.json.CSJson
import java.io.File
import kotlin.time.Duration.Companion.seconds

class CSFileJsonStore(
    parent: CSHasDestruct? = null,
    val file: File, isJsonPretty: Boolean = isDebug,
    val isImmediateWrite: Boolean = false
) : CSJsonStoreBase(isJsonPretty) {

    companion object {
        val SAVE_DELAY = 1.seconds

        fun CSFileJsonStore(
            parent: CSHasDestruct? = null,
            fileName: String,
            isJsonPretty: Boolean = CSJson.isJsonPretty,
            isImmediateWrite: Boolean = false
        ) = CSFileJsonStore(
            parent, File(app.filesDir, "$fileName.json"),
            isJsonPretty, isImmediateWrite
        )
    }

    init {
        parent?.onDestructed(::close)
    }

    override fun loadJsonString() = file.readString()

    override fun saveJsonString(json: String) {
        file.write(json)
    }

    private var isSaveDisabled: Boolean = false

    private val saveChannel = Channel<Unit>(capacity = CONFLATED)

    private val writerJob = CoroutineScope(Dispatchers.IO).launch {
        while (isActive) {
            saveChannel.receive()
            delay(SAVE_DELAY)
            if (!isSaveDisabled) saveJsonString(createJsonString(data.toMap()))
        }
    }

    override fun onSave() {
        if (isImmediateWrite || CSBackground.isOff) {
            if (!isSaveDisabled) saveJsonString(createJsonString(data))
        } else saveChannel.trySend(Unit)
    }

    suspend fun waitForWriteFinish() {
        saveChannel.send(Unit)
        writerJob.join()
    }

    fun close() = writerJob.cancel()
}

