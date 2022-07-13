package renetik.android.store.property.value

import renetik.android.store.CSStore

class CSStringValueStoreProperty(
    store: CSStore, key: String,
    override val default: String,
    onChange: ((value: String) -> Unit)? = null)
    : CSValueStoreProperty<String>(store, key, onChange) {
    override fun get(store: CSStore) = store.getString(key)
    override fun set(store: CSStore, value: String) = store.set(key, value)
}

