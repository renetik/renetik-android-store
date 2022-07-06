package renetik.android.store.property.late

import renetik.android.store.CSStore
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

class CSJsonListLateStoreProperty<T : CSJsonObjectStore>(
    store: CSStore, override val key: String, val type: KClass<T>,
    onChange: ((value: List<T>) -> Unit)? = null)
    : CSLateStorePropertyBase<List<T>>(store, key, onChange) {
    override fun get(): List<T>? = store.getJsonObjectList(key, type)
    override fun set(store: CSStore, value: List<T>) = store.set(key, value)
}


