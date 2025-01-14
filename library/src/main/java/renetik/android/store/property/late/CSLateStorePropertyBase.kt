package renetik.android.store.property.late

import renetik.android.event.property.CSPropertyBase
import renetik.android.event.registration.plus
import renetik.android.event.util.CSLater.later
import renetik.android.store.CSStore
import renetik.android.store.property.CSLateStoreProperty

abstract class CSLateStorePropertyBase<T>(
    final override val store: CSStore,
    override val key: String, onChange: ((value: T) -> Unit)?
) : CSPropertyBase<T>(onChange), CSLateStoreProperty<T> {

    private var loadedValue: T? = null
        set(value) {
            field = value
            onLoadedValueChanged(value)
        }

    open fun onLoadedValueChanged(value: T?) = Unit

    init {
        later { if (parent != null) listenStoreLoad() }
    }

    override fun listenStoreLoad() = this + ("store.eventLoaded.listen"
            to store.eventLoaded.listen { update() })

    private fun update() {
        if (loadedValue == null) return
        val newValue = get(store)!!
        if (loadedValue != newValue) {
            loadedValue = newValue
            onValueChanged(newValue)
        }
    }

    override var value: T
        get() {
            if (loadedValue == null) loadedValue = get(store)!!
            return loadedValue!!
        }
        set(value) = value(value)

    abstract override fun get(store: CSStore): T?

    override fun value(newValue: T, fire: Boolean) {
        if (loadedValue == newValue) return
        loadedValue = newValue
        set(store, newValue)
        onValueChanged(newValue, fire)
    }

    override val isLoaded get() = loadedValue != null
    override fun toString() = super.toString() + ":$key:$value"
}