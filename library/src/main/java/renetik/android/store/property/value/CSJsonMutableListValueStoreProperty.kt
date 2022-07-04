package renetik.android.store.property.value

import renetik.android.core.kotlin.collections.reload
import renetik.android.store.CSStore
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

class CSJsonMutableListValueStoreProperty<T : CSJsonObjectStore>(
    store: CSStore,
    key: String,
    val type: KClass<T>,
    override val default: MutableList<T> = mutableListOf(),
    onApply: ((value: MutableList<T>) -> Unit)? = null
) : CSValueStoreProperty<MutableList<T>>(store, key, onApply) {
    override fun get(store: CSStore) =
        store.getJsonObjectList(key, type)?.let { mutableListOf<T>().reload(it) } ?: this.default

    override fun set(store: CSStore, value: MutableList<T>) = store.set(key, value)
}