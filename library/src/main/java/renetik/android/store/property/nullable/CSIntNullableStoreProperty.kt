package renetik.android.store.property.nullable

import renetik.android.store.CSStore
import renetik.android.store.property.value.CSValueStoreProperty

//class CSIntNullableStoreProperty(
//    store: CSStore, key: String, default: Int? = null,
//    onChange: ((value: Int?) -> Unit)? = null)
//    : CSNullableStoreProperty<Int>(store,
//    key, default, onChange) {
//    override fun get(store: CSStore): Int? = store.getInt(key)
//    override fun set(store: CSStore, value: Int?) {
//        if (value == null) store.clear(key) else store.set(key, value)
//    }
//}
class CSIntNullableStoreProperty(
    store: CSStore, key: String,
    override val default: Int? = null,
    onChange: ((value: Int?) -> Unit)? = null)
    : CSValueStoreProperty<Int?>(store, key, onChange) {
    override fun get(store: CSStore) = store.getInt(key)
    override fun set(store: CSStore, value: Int?) =
        value?.let { store.set(key, value) } ?: store.clear(key)
}