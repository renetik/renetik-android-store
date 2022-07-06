package renetik.android.store.type

import renetik.android.core.kotlin.runIf
import renetik.android.json.parseJsonMap
import renetik.android.json.toJson
import java.io.Closeable

abstract class CSJsonStore(private val isPretty: Boolean = false)
    : CSJsonObjectStore(), Closeable {

    override val data: MutableMap<String, Any?> by lazy { load() }
    abstract fun loadJsonString(): String?
    abstract fun saveJsonString(json: String)
    protected fun load() = loadJsonString()?.parseJsonMap() ?: mutableMapOf()

    override fun onChanged() {
        super.onChanged()
        save()
    }

    fun save() {
//        if (isBulkSave) return
        val json = data
            .runIf(isPretty) { it.toSortedMap() }
            .toJson(formatted = isPretty)
        saveJsonString(json)
    }
}