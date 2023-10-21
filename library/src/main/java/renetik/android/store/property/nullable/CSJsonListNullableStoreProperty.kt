package renetik.android.store.property.nullable

import renetik.android.store.CSStore
import renetik.android.store.property.value.CSValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

class CSJsonListNullableStoreProperty<T : CSJsonObjectStore>(
    store: CSStore, key: String, val type: KClass<T>,
    override val default: List<T>? = null,
    onChange: ((value: List<T>?) -> Unit)? = null
) : CSValueStoreProperty<List<T>?>(store, key, onChange) {
    override fun get(store: CSStore): List<T>? = store.getJsonObjectList(key, type)
    override fun set(store: CSStore, value: List<T>?) = store.setJsonObjectList(key, value)
}