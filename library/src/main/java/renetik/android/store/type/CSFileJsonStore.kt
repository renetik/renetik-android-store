package renetik.android.store.type

import android.content.Context
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import renetik.android.core.kotlin.primitives.second
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.event.registration.CSRegistration
import renetik.android.event.registration.task.CSBackground
import renetik.android.event.registration.task.CSBackground.background
import renetik.android.json.CSJson
import java.io.File

class CSFileJsonStore(
    val file: File, isJsonPretty: Boolean = isDebug,
    val isImmediateWrite: Boolean = false
) : CSJsonStoreBase(isJsonPretty) {

    constructor(
        parent: File, fileName: String, directory: String = "",
        isJsonPretty: Boolean = isDebug, isImmediateWrite: Boolean = false
    ) : this(
        File(File(parent, directory), "$fileName.json"), isJsonPretty, isImmediateWrite
    )

    constructor(
        context: Context, fileName: String, directory: String = "",
        isJsonPretty: Boolean = CSJson.isJsonPretty,
        isImmediateWrite: Boolean = false
    ) : this(
        context.filesDir, fileName, directory, isJsonPretty, isImmediateWrite
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

    private var backgroundWriteRegistration: CSRegistration? = null

    override fun onSave() {
        if (isImmediateWrite || CSBackground.isOff)
            saveJsonString(createJsonString(data))
        else {
            val dataCopy = data.toMap()
            backgroundWriteRegistration?.cancel()
            backgroundWriteRegistration = background(1.second) {
                saveJsonString(createJsonString(dataCopy))
            }
        }
    }
}

