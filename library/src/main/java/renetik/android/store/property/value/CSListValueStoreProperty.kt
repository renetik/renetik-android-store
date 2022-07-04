package renetik.android.store.property.value

import renetik.android.core.kotlin.toId
import renetik.android.core.lang.CSHasId
import renetik.android.store.CSStore

class CSListValueStoreProperty<T : CSHasId>(
    store: CSStore, key: String,
    val values: Iterable<T>,
    override val default: List<T>,
    onChange: ((value: List<T>) -> Unit)? = null
) : CSValueStoreProperty<List<T>>(store, key, onChange) {

    override fun get(store: CSStore) = store.get(key)?.split(",")
        ?.mapNotNull { categoryId -> values.find { it.id == categoryId } } ?: this.default

    override fun set(store: CSStore, value: List<T>) =
        store.set(key, value.joinToString(",") { it.toId() })
}