package renetik.android.store.type

import android.content.Context
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.lang.CSTimeConstants.Second
import renetik.android.event.registration.CSRegistration
import renetik.android.event.registration.task.CSBackground.background
import renetik.android.event.registration.task.CSBackground.isBackgroundOff
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

    private var backgroundWriteRegistration: CSRegistration? = null

    override fun saveJsonString(json: String) {
        if (isImmediateWrite || isBackgroundOff) file.write(json)
        else {
            backgroundWriteRegistration?.cancel()
            backgroundWriteRegistration = background(1 * Second) { file.write(json) }
        }
    }
}

