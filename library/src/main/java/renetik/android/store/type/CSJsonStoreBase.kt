package renetik.android.store.type

import renetik.android.core.kotlin.changeIf
import renetik.android.core.kotlin.primitives.isFalse
import renetik.android.json.CSJson.isJsonPretty
import renetik.android.json.parseJsonMap
import renetik.android.json.toJson

abstract class CSJsonStoreBase(
    protected val isPretty: Boolean = isJsonPretty
) : CSJsonObjectStore() {

    override val data: MutableMap<String, Any?> = load()
    abstract fun loadJsonString(): String?
    abstract fun saveJsonString(json: String)
    protected fun load() = loadJsonString()?.parseJsonMap() ?: mutableMapOf()

    override fun onChanged() {
        super.onChanged()
        save()
    }

    fun save() = isOperation.isFalse { onSave() }

    protected open fun onSave() = saveJsonString(createJsonString(data))

    fun createJsonString(data: Map<String, Any?>): String =
        data.changeIf(isPretty) { it.toSortedMap() }.toJson(formatted = isPretty)
}