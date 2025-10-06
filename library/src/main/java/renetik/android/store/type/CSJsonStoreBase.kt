package renetik.android.store.type

import renetik.android.core.kotlin.changeIf
import renetik.android.core.kotlin.collections.reload
import renetik.android.core.kotlin.primitives.isFalse
import renetik.android.json.CSJson.isJsonPretty
import renetik.android.json.parseJsonMap
import renetik.android.json.toJson

abstract class CSJsonStoreBase(
    protected val isPretty: Boolean = isJsonPretty
) : CSJsonObjectStore() {

    override val data: MutableMap<String, Any?> = mutableMapOf()
    abstract fun loadJsonString(): String?
    abstract fun saveJsonString(json: String)
    fun load() = loadJsonString()?.parseJsonMap()?.also { data.reload(it) }

    override fun onChanged() {
        super.onChanged()
        save()
    }

    fun save() = isOperation.isFalse { onSave() }

    protected open fun onSave() = saveJsonString(createJsonString(data))

    fun createJsonString(data: Map<String, Any?>): String =
        data.changeIf(isPretty) { toSortedMap() }.toJson(formatted = isPretty)
}