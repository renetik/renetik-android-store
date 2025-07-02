package renetik.android.store.type

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import renetik.android.core.base.CSApplication.Companion.app
import renetik.android.core.extensions.content.CSToast.toast
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.writeAtomic
import renetik.android.core.kotlin.onFailureOf
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.lang.result.context
import renetik.android.core.lang.variable.setFalse
import renetik.android.core.lang.variable.setTrue
import renetik.android.core.logging.CSLog.logError
import renetik.android.event.CSBackground
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.onDestructed
import renetik.android.event.property.CSAtomicProperty
import renetik.android.event.registration.waitIsTrue
import renetik.android.json.CSJson
import java.io.File
import kotlin.time.Duration.Companion.seconds

class CSFileJsonStore(
    parent: CSHasDestruct? = null,
    val file: File, isJsonPretty: Boolean = isDebug,
    private val isImmediateWrite: Boolean = false
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

        fun CSFileJsonStore(
            fileName: String,
            isJsonPretty: Boolean = CSJson.isJsonPretty,
            isImmediateWrite: Boolean = false
        ) = CSFileJsonStore(
            null, File(app.filesDir, "$fileName.json"),
            isJsonPretty, isImmediateWrite
        )

        fun CSFileJsonStore(
            file: File, isJsonPretty: Boolean = isDebug,
            isImmediateWrite: Boolean = false
        ) = CSFileJsonStore(null, file, isJsonPretty, isImmediateWrite)
    }

    init {
        parent?.onDestructed(::close)
    }

    override fun loadJsonString() = file.readString()
    override fun saveJsonString(json: String) = file.writeAtomic(json)
    private var isSaveDisabled: Boolean = false
    private var isWriteFinished = CSAtomicProperty(parent, false)
    private val saveChannel = Channel<Unit>(capacity = CONFLATED)

    private val writerJob = CoroutineScope(Dispatchers.IO).launch {
        while (isActive) {
            saveChannel.receive()
            isWriteFinished.setFalse()
            delay(SAVE_DELAY)
            if (!isSaveDisabled) runCatching {
                saveJsonString(createJsonString(data))
            }.onFailure(::logError).onFailureOf<OutOfMemoryError> {
                toast("Out of memory, exit...")
                close()
                Main.context { app.exit() }
            }
            isWriteFinished.setTrue()
        }
    }

    override fun onSave() {
        if (isImmediateWrite || CSBackground.isOff) {
            if (!isSaveDisabled) saveJsonString(createJsonString(data))
        } else saveChannel.trySend(Unit)
    }

    suspend fun waitForWriteFinish() = isWriteFinished.waitIsTrue()

    fun close(): Unit = writerJob.cancel()

    override fun clear() {
        if (data.isEmpty()) return
        file.delete()
        super.clear()
    }
}

