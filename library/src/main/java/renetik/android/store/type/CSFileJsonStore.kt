package renetik.android.store.type

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import renetik.android.core.base.CSApplication.Companion.app
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.writeAtomic
import renetik.android.core.kotlin.onFailureOf
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.lang.CSLang.ExitStatus.Error
import renetik.android.core.lang.CSLang.exit
import renetik.android.core.lang.result.invoke
import renetik.android.core.lang.variable.setFalse
import renetik.android.core.lang.variable.setTrue
import renetik.android.core.logging.CSLog.logError
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.onDestructed
import renetik.android.event.property.CSAtomicProperty
import renetik.android.event.registration.launch
import renetik.android.event.registration.waitForTrue
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
        load()
        parent?.onDestructed(::close)
    }

    override fun loadJson() = file.readString()
    override fun saveJson(json: String) = file.writeAtomic(json)
    private var isWriteFinished = CSAtomicProperty(parent, false)
    private val saveChannel = Channel<Unit>(capacity = CONFLATED)

    private val writerRegistration = app.IO.launch {
        runCatching {
            for (signal in saveChannel) {
                isWriteFinished.setFalse()
                delay(SAVE_DELAY)
                runCatching { saveJson(Main { createJsonString(data) }) }.onFailure {
                    if (it is OutOfMemoryError || it is CancellationException) throw it
                    else logError(it)
                }
                isWriteFinished.setTrue()
            }
        }.onFailure {
            if (it is CancellationException)
                runCatching { saveJson(createJsonString(data)) }.onFailure(::onFailure)
            else onFailure(it)
        }
    }

    private fun onFailure(it: Throwable) {
        logError(it)
        if (it is OutOfMemoryError) {
            runCatching { close(wait = false) }
            exit(Error)
        }
    }

    fun Result<*>.handleOutOfMemory() = onFailureOf<OutOfMemoryError> {
        runCatching { close(wait = false) }; exit(Error)
    }

    override fun onSave() {
        if (isImmediateWrite) saveJson(createJsonString(data))
        else saveChannel.trySend(Unit)
    }

    suspend fun waitForWriteFinish() = isWriteFinished.waitForTrue()

    fun close(wait: Boolean = true) {
        saveChannel.close()
        if (wait) runBlocking { writerRegistration.waitToFinish() }
        else writerRegistration.cancel()
    }

    override fun clear() {
        if (data.isEmpty()) return
        file.delete()
        super.clear()
    }
}