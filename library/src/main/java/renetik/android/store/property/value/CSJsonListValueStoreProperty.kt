package renetik.android.store.property.value

import renetik.android.store.CSStore
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

class CSJsonListValueStoreProperty<T : CSJsonObjectStore>(
    store: CSStore,
    key: String,
    val type: KClass<T>,
    override val default: List<T>,
    onApply: ((value: List<T>) -> Unit)? = null
) : CSValueStoreProperty<List<T>>(store, key, onApply) {
    override fun get(store: CSStore) = store.getJsonObjectList(key, type) ?: this.default
    override fun set(store: CSStore, value: List<T>) = store.set(key, value)
}

