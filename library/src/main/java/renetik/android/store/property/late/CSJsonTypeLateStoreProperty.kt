package renetik.android.store.property.late

import renetik.android.store.CSStore
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

class CSJsonTypeLateStoreProperty<T : CSJsonObjectStore>(
    store: CSStore, override val key: String, val type: KClass<T>,
    onChange: ((value: T) -> Unit)? = null)
    : CSLateStorePropertyBase<T>(store, key, onChange) {
    override fun get(): T? = store.getJsonObject(key, type)
    override fun set(store: CSStore, value: T) = store.setJsonObject(key, value)
}