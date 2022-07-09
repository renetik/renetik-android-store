package renetik.android.store.property.value

import renetik.android.core.kotlin.kClass
import renetik.android.core.lang.ArgFunc
import renetik.android.event.owner.cancel
import renetik.android.event.owner.register
import renetik.android.event.registration.CSRegistration
import renetik.android.store.CSStore
import renetik.android.store.property.save
import renetik.android.store.type.CSJsonObjectStore

class CSJsonValueStoreProperty<T : CSJsonObjectStore>(
    store: CSStore, key: String,
    override val default: T,
    onChange: ArgFunc<T>? = null
) : CSValueStoreProperty<T>(store, key, onChange) {

    override fun get(store: CSStore) = store.getJsonObject(key, default.kClass)
    override fun set(store: CSStore, value: T) = store.set(key, value)
    private var onJsonObjectChanged: CSRegistration? = registerJsonObjectChanged(value)

    override fun onValueChanged(newValue: T, fire: Boolean) {
        super.onValueChanged(newValue, fire)
        onJsonObjectChanged = registerJsonObjectChanged(newValue)
    }

    private fun registerJsonObjectChanged(value: T): CSRegistration {
        cancel(onJsonObjectChanged)
        return register(value.eventChanged.listen { save() })
    }
}