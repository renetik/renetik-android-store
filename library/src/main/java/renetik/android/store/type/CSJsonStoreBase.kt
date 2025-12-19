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
    abstract fun loadJson(): String?
    abstract fun saveJson(json: String)
    fun load() = loadJson()?.also(::loadJsonString)

    override fun onChanged() {
        super.onChanged()
        isOperation.isFalse { onSave() }
    }

    protected open fun onSave() = saveJson(getJsonString(isPretty))
}

fun CSJsonObjectStore.getJsonString(isPretty: Boolean): String =
    data.changeIf(isPretty) { toSortedMap() }
        .toJson(formatted = isPretty)

fun CSJsonObjectStore.loadJsonString(value: String) {
    value.parseJsonMap()?.also { data.reload(it) }
}