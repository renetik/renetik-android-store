package renetik.android.store.type

import android.content.Context
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import renetik.android.core.lang.CSBackground.background
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.json.CSJson
import java.io.File

class CSFileJsonStore(
    val file: File, isJsonPretty: Boolean = isDebug
) : CSJsonStoreBase(isJsonPretty) {
    var writeImmediately = false

    constructor(
        parent: File, id: String, directory: String = "",
        isJsonPretty: Boolean = isDebug
    ) : this(File(File(parent, directory), "$id.json"), isJsonPretty)

    constructor(
        context: Context, id: String, directory: String = "",
        isJsonPretty: Boolean = CSJson.isJsonPretty
    ) : this(context.filesDir, id, directory, isJsonPretty)

    constructor(path: String, isJsonPretty: Boolean = false)
        : this(File(app.filesDir, path), isJsonPretty)

    override fun loadJsonString() = file.readString()
    override fun saveJsonString(json: String) {
        if (writeImmediately) file.write(json)
        else background { file.write(json) }
    }
}

