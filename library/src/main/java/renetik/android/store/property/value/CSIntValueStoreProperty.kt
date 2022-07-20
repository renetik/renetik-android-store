package renetik.android.store.property.value

import renetik.android.store.CSStore

class CSIntValueStoreProperty(
    store: CSStore, key: String,
    override val default: Int,
    onChange: ((value: Int) -> Unit)? = null)
    : CSValueStoreProperty<Int>(store, key,  onChange) {
    override fun get(store: CSStore) = store.getInt(key)
    override fun set(store: CSStore, value: Int) = store.set(key, value)
}

