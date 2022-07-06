package renetik.android.store.type

import android.content.Context
import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import java.io.File

class CSFileJsonStore(parent: File, id: String, directory: String = "",
                      isJsonPretty: Boolean = false)
    : CSJsonStoreBase(isJsonPretty) {

    constructor(context: Context, id: String, directory: String = "",
                isJsonPretty: Boolean = false)
            : this(context.filesDir, id, directory, isJsonPretty)

    val file = File(File(parent, directory), "$id.json")
    override fun loadJsonString() = file.readString()
    override fun saveJsonString(json: String) {
        file.write(json)
    }
}

