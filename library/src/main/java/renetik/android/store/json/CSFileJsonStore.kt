package renetik.android.store.json

import android.content.Context
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import java.io.File

class CSFileJsonStore(parent: File, id: String, directory: String = "",
                      isJsonPretty: Boolean = false)
    : CSJsonStore(isJsonPretty) {

    constructor(context: Context, id: String, directory: String = "",
                isJsonPretty: Boolean = false)
            : this(context.filesDir, id, directory, isJsonPretty)

    private val file = File(File(parent, directory), "$id.json")
    override fun loadJsonString() = file.readString()
    override fun saveJsonString(json: String) {
        file.write(json)
    }
}

