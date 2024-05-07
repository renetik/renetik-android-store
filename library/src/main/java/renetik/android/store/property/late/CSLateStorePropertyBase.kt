package renetik.android.store.property.late

import renetik.android.core.kotlin.notNull
import renetik.android.event.property.CSPropertyBase
import renetik.android.event.registration.register
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

    override var filter: ((T?) -> T?)? = null
    override fun getFiltered(store: CSStore): T? =
        get().let { filter?.invoke(it) ?: it }

    init {
        register(store.eventLoaded.listen {
            if (loadedValue != null) {
                val newValue = getFiltered(store)!!
                if (loadedValue != newValue) {
                    loadedValue = newValue
                    onValueChanged(newValue)
                }
            }
        })
    }

    override var value: T
        get() {
            if (loadedValue == null) loadedValue = getFiltered(store)!!
            return loadedValue!!
        }
        set(value) = value(value)

    abstract fun get(): T?

    override fun value(newValue: T, fire: Boolean) {
        if (loadedValue == newValue) return
        loadedValue = newValue
        set(store, newValue)
        onValueChanged(newValue, fire)
    }

    override val isLoaded get() = loadedValue.notNull
    override fun toString() = super.toString() + ":$key:$value"
}