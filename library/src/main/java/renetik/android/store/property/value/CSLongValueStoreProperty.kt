package renetik.android.store.property.value

import renetik.android.store.CSStore

class CSLongValueStoreProperty(
    store: CSStore, key: String, default: Long,
    onChange: ((value: Long) -> Unit)?)
    : CSValueStoreProperty<Long>(store, key, onChange) {
    override val default = default
    override fun get(store: CSStore) = store.getLong(key)
    override fun set(store: CSStore, value: Long) = store.set(key, value)
}