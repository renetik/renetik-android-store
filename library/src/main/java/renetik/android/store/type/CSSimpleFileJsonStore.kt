package renetik.android.store.type

import renetik.android.core.java.io.readString
import renetik.android.core.java.io.write
import renetik.android.core.kotlin.changeIf
import renetik.android.core.lang.CSEnvironment
import renetik.android.json.parseJsonMap
import renetik.android.json.toJson
import renetik.android.store.type.CSJsonObjectStore
import java.io.File

class CSSimpleFileJsonStore(
    val file: File,
    private val isPretty: Boolean = CSEnvironment.isDebug
) : CSJsonObjectStore() {

    override val data: MutableMap<String, Any?> by lazy {
        file.readString()?.parseJsonMap() ?: mutableMapOf()
    }

    fun save() = file.write(data.changeIf(isPretty) { it.toSortedMap() }
        .toJson(formatted = isPretty))
}