package renetik.android.store.property.nullable

import renetik.android.store.CSStore
import renetik.android.core.kotlin.toId
import renetik.android.json.obj.getValue

class CSListItemNullableStoreProperty<T>(
    store: CSStore, key: String,
    val values: Iterable<T>, default: T? = null, onChange: ((value: T?) -> Unit)? = null
) : CSNullableStoreProperty<T>(store,
    key, default, listenStoreChanged = false, onChange) {
    override fun get(store: CSStore) = store.getValue(key, values)
    override fun set(store: CSStore, value: T?) {
        if (value == null) store.clear(key) else store.set(key, value.toId())
    }
}