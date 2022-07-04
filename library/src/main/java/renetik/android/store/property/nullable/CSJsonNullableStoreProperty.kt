package renetik.android.store.property.nullable

import renetik.android.core.kotlin.kClass
import renetik.android.core.lang.ArgFunc
import renetik.android.event.cancel
import renetik.android.event.register
import renetik.android.event.registration.CSRegistration
import renetik.android.store.CSStore
import renetik.android.store.property.value.CSValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore

//class CSJsonNullableStoreProperty<T : CSJsonObjectStore>(
//    store: CSStore, key: String, val type: KClass<T>,
//    default: T? = null, onApply: ((value: T?) -> Unit)? = null
//) : CSNullableStoreProperty<T>(store, key, default, onApply) {
//    override fun get(store: CSStore): T? = store.getJsonObject(key, type)
//    override fun set(store: CSStore, value: T?) = store.set(key, value)
//}

class CSJsonNullableStoreProperty<T : CSJsonObjectStore>(
    store: CSStore, key: String,
    override val default: T? = null,
    onChange: ArgFunc<T?>? = null
) : CSValueStoreProperty<T?>(store, key, onChange) {

    override fun get(store: CSStore) = default?.let { store.getJsonObject(key, it.kClass) }
    override fun set(store: CSStore, value: T?) =
        value?.let { store.set(key, it) } ?: store.clear(key)

    private var onJsonObjectChanged: CSRegistration? = registerJsonObjectChanged(value)

    override fun onValueChanged(newValue: T?, fire: Boolean) {
        super.onValueChanged(newValue, fire)
        onJsonObjectChanged = registerJsonObjectChanged(newValue)
    }

    private fun registerJsonObjectChanged(value: T?): CSRegistration? {
        cancel(onJsonObjectChanged)
        return register(value?.eventChanged?.listen {
            loadedValue = value
            save()
        })
    }

}


//class CSJsonNullableStorePropertyOld<T : CSJsonObjectStore>(
//    store: CSStore, key: String, val type: KClass<T>,
//    override val default: T? = null,
//    onChange: ((value: T?) -> Unit)? = null)
//    : CSValueStoreProperty<T?>(store, key, onChange) {
//    override fun get(store: CSStore) = store.getJsonObject(key, type)
//    override fun set(store: CSStore, value: T?) =
//        value?.let { store.set(key, value) } ?: store.clear(key)
//}