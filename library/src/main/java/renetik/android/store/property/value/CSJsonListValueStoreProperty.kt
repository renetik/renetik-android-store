package renetik.android.store.property.value

import renetik.android.store.json.CSStoreJsonObject
import renetik.android.store.CSStore
import kotlin.reflect.KClass

class CSJsonListValueStoreProperty<T : CSStoreJsonObject>(
    store: CSStore,
    key: String,
    val type: KClass<T>,
    val default: List<T> = emptyList(),
    listenStoreChanged: Boolean = false,
    onApply: ((value: List<T>) -> Unit)? = null
) : CSValueStoreProperty<List<T>>(store, key, listenStoreChanged, onApply) {
    override val defaultValue = default
    override var _value = load()
    override fun get(store: CSStore) = store.getJsonObjectList(key, type) ?: default
    override fun set(store: CSStore, value: List<T>) = store.set(key, value)
}