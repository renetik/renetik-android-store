package renetik.android.store.type

import android.content.Context
import kotlinx.coroutines.delay
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.event.CSBackground
import renetik.android.event.CSBackground.background
import renetik.android.event.registration.JobRegistration
import renetik.android.event.registration.launch
import renetik.android.json.CSJson
import java.io.File
import kotlin.time.Duration.Companion.seconds

class CSFileJsonStore(
    val file: File, isJsonPretty: Boolean = isDebug,
    val isImmediateWrite: Boolean = false
) : CSJsonStoreBase(isJsonPretty) {

    companion object {
        val SAVE_DELAY = 1.seconds
    }

    constructor(
        parent: File, fileName: String, directory: String = "",
        isJsonPretty: Boolean = isDebug, isImmediateWrite: Boolean = false
    ) : this(
        File(File(parent, directory), "$fileName.json"), isJsonPretty, isImmediateWrite
    )

    //TODO... same signature without ...context...
    constructor(
        context: Context, fileName: String, directory: String = "",
        isJsonPretty: Boolean = CSJson.isJsonPretty,
        isImmediateWrite: Boolean = false
    ) : this(
        app.filesDir, fileName, directory, isJsonPretty, isImmediateWrite
    )

    constructor(
        path: String, isJsonPretty: Boolean = false,
        isImmediateWrite: Boolean = false
    ) : this(
        File(app.filesDir, path), isJsonPretty, isImmediateWrite
    )

    override fun loadJsonString() = file.readString()

    override fun saveJsonString(json: String) {
        file.write(json)
    }

    private var writeRegistration: JobRegistration? = null

    override fun onSave() {
        if (isImmediateWrite || CSBackground.isOff)
            saveJsonString(createJsonString(data))
        else {
            writeRegistration?.cancel()
            val dataCopy = data.toMap()
            writeRegistration = background.launch {
                delay(SAVE_DELAY)
                saveJsonString(createJsonString(dataCopy))
            }
        }
    }

    suspend fun waitForWriteFinish() = writeRegistration?.waitToFinish()
}

