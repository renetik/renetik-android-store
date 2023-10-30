package renetik.android.store.type

import renetik.android.core.kotlin.changeIf
import renetik.android.json.CSJson.isJsonPretty
import renetik.android.json.parseJsonMap
import renetik.android.json.toJson

abstract class CSJsonStoreBase(
    private val isPretty: Boolean = isJsonPretty
) : CSJsonObjectStore() {

    override val data: MutableMap<String, Any?> by lazy { load() }
    abstract fun loadJsonString(): String?
    abstract fun saveJsonString(json: String)
    protected fun load() = loadJsonString()?.parseJsonMap() ?: mutableMapOf()

    override fun onChanged() {
        super.onChanged()
        save()
    }

    fun save() { //TODO!!!!!
        if (isBulkSave) return
        val json = data
            .changeIf(isPretty) { it.toSortedMap() }
            .toJson(formatted = isPretty)
        saveJsonString(json)
    }
}