package renetik.android.store.property.nullable

import renetik.android.core.kotlin.kClass
import renetik.android.core.lang.ArgFunc
import renetik.android.event.registration.CSRegistration
import renetik.android.event.registration.cancel
import renetik.android.event.registration.register
import renetik.android.json.obj.clone
import renetik.android.store.CSStore
import renetik.android.store.property.save
import renetik.android.store.property.value.CSValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore

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

    override var value: T?
        get() {
            if (loadedValue == null)
                loadedValue = default?.clone()
            return loadedValue
        }
        set(value) = value(value)
}