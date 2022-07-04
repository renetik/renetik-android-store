package renetik.android.store.property.value

import renetik.android.store.CSStore

class CSFloatValueStoreProperty(
    store: CSStore, key: String, default: Float,
    onChange: ((value: Float) -> Unit)?)
    : CSValueStoreProperty<Float>(store, key, onChange) {
    override val default = default
    override fun get(store: CSStore) = store.getFloat(key)
    override fun set(store: CSStore, value: Float) = store.set(key, value)
}

