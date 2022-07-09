package renetik.android.store.property.value

import renetik.android.core.lang.lazyVar
import renetik.android.event.property.CSEventPropertyBase
import renetik.android.event.owner.register
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty

abstract class CSValueStoreProperty<T>(
    final override val store: CSStore,
    final override val key: String,
    onChange: ((value: T) -> Unit)? = null)
    : CSEventPropertyBase<T>(onChange), CSStoreProperty<T> {

    abstract val default: T
    abstract fun get(store: CSStore): T?
    protected var loadedValue: T? by lazyVar { get(store) }

    init {
        register(store.eventLoaded.listen {
            val newValue = get(store)
            if (newValue == null) {
                if (loadedValue != default) {
                    loadedValue = null
                    onValueChanged(default)
                }
                loadedValue = null
            } else {
                if (loadedValue != newValue) {
                    loadedValue = newValue
                    onValueChanged(newValue)
                }
            }
        })
    }

    override var value: T
        get() = loadedValue ?: default
        set(value) = value(value)

    override fun value(newValue: T, fire: Boolean) {
        if (loadedValue != newValue) saveValue(newValue, fire)
    }

    fun saveValue(newValue: T, fire: Boolean) {
        loadedValue = newValue
        set(store, newValue)
        onValueChanged(newValue, fire)
    }

    override fun toString() = super.toString() + ":$key:$value"
}