package renetik.android.store.property.nullable

import renetik.android.store.CSStore

class CSFloatNullableStoreProperty(
    store: CSStore, key: String, default: Float?,
    onChange: ((value: Float?) -> Unit)?)
    : CSNullableStoreProperty<Float>(store,
    key, default, listenStoreChanged = false, onChange) {
    override fun get(store: CSStore): Float? = store.getFloat(key)
    override fun set(store: CSStore, value: Float?) {
        if (value == null) store.clear(key) else store.set(key, value)
    }
}