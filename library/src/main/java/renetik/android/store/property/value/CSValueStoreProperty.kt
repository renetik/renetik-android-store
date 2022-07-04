package renetik.android.store.property.value

import renetik.android.event.property.CSEventPropertyBase
import renetik.android.event.register
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty

abstract class CSValueStoreProperty<T>(
    final override val store: CSStore,
    final override val key: String,
    onChange: ((value: T) -> Unit)? = null)
    : CSEventPropertyBase<T>(onChange), CSStoreProperty<T> {

    abstract val default: T
    abstract fun get(store: CSStore): T?
    private var isLoaded = false
    protected var loadedValue: T? = null

    init {
        register(store.eventLoaded.listen {
            val newValue = get(store)
            if (newValue == null) {
                if (loadedValue != default) {
                    loadedValue = null
                    onValueChanged(default, true)
                }
                loadedValue = null
            } else {
                if (loadedValue != newValue) {
                    loadedValue = newValue
                    onValueChanged(newValue, true)
                }
            }
        })
    }

    override var value: T
        get() {
            if (!isLoaded) {
                loadedValue = get(store)
                isLoaded = true
            }
            return loadedValue ?: default
        }
        set(value) = value(value)

    override fun value(newValue: T, fire: Boolean) {
        if (loadedValue != newValue)
            saveValue(newValue, fire)
    }

    fun saveValue(newValue: T, fire: Boolean) {
        loadedValue = newValue
        set(store, newValue)
        onValueChanged(newValue, fire)
    }

    override fun toString() = "$key $value"
}