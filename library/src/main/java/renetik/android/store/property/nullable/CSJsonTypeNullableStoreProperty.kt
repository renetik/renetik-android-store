package renetik.android.store.property.nullable

import renetik.android.store.type.CSJsonObjectStore
import renetik.android.store.CSStore
import kotlin.reflect.KClass

class CSJsonTypeNullableStoreProperty<T : CSJsonObjectStore>(
    store: CSStore, key: String, val type: KClass<T>,
    val default: T? = null, onApply: ((value: T?) -> Unit)? = null
) : CSNullableStoreProperty<T>(store, key, default, listenStoreChanged = false, onApply) {
    override fun get(store: CSStore): T? = store.getJsonObject(key, type)
    override fun set(store: CSStore, value: T?) = store.set(key, value)
}