package renetik.android.store.property.value

import renetik.android.event.registration.CSRegistration
import renetik.android.event.listen
import renetik.android.store.json.CSStoreJsonObject
import renetik.android.store.CSStore
import renetik.android.core.kotlin.reflect.createInstance
import kotlin.reflect.KClass

class CSJsonTypeValueStoreEventProperty<T : CSStoreJsonObject>(
    store: CSStore, key: String, val type: KClass<T>,
    listenStoreChanged: Boolean = false,
    onApply: ((value: T) -> Unit)? = null
) : CSValueStoreEventProperty<T>(store, key, listenStoreChanged, onApply) {

    override val defaultValue get() = type.createInstance()!!

    override var _value = load()
        set(value) {
            field = value
            updateOnChanged()
        }

    init {
        updateOnChanged()
    }

    var valueEventChanged: CSRegistration? = null

    private fun updateOnChanged() {
        valueEventChanged?.cancel()
        valueEventChanged = _value.eventChanged.listen { save() }
    }

    override fun get(store: CSStore) =
        store.getJsonObject(key, type)

    override fun set(store: CSStore, value: T) =
        store.set(key, value)
}