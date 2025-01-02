package renetik.android.store.type

import kotlinx.coroutines.delay
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.event.CSBackground
import renetik.android.event.CSBackground.background
import renetik.android.event.registration.CSRegistration
import renetik.android.event.registration.JobRegistration
import renetik.android.event.registration.launch
import renetik.android.json.CSJson
import java.io.File
import kotlin.time.Duration.Companion.seconds

class CSFileJsonStoreOld(
    val file: File, isJsonPretty: Boolean = isDebug,
    val isImmediateWrite: Boolean = false
) : CSJsonStoreBase(isJsonPretty) {

    companion object {
        val SAVE_DELAY = 1.seconds

        fun CSFileJsonStoreOld(
            fileName: String,
            isJsonPretty: Boolean = CSJson.isJsonPretty,
            isImmediateWrite: Boolean = false
        ) = CSFileJsonStoreOld(
            File(app.filesDir, "$fileName.json"),
            isJsonPretty, isImmediateWrite
        )
    }

    override fun loadJsonString() = file.readString()

    override fun saveJsonString(json: String) {
        file.write(json)
    }

    private var writeRegistration: JobRegistration? = null
    private var registration: CSRegistration? = null

    override fun onSave() {
        if (isImmediateWrite || CSBackground.isOff)
            saveJsonString(createJsonString(data))
        else {
            val dataCopy = data.toMap()
//            registration?.cancel()
//            registration = background(SAVE_DELAY) {
//                saveJsonString(createJsonString(dataCopy))
//            }
//            CSBackground.dispatcher.launch {
//                writeRegistration?.cancelAndWait()
//                writeRegistration = it
//                delay(SAVE_DELAY)
//                saveJsonString(createJsonString(dataCopy))
//            }
        }
    }

    suspend fun waitForWriteFinish() = writeRegistration?.waitToFinish()
}

