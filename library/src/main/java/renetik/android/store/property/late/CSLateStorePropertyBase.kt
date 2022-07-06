package renetik.android.store.property.late

import renetik.android.core.kotlin.CSUnexpectedException.Companion.unexpected
import renetik.android.core.kotlin.notNull
import renetik.android.event.property.CSEventPropertyBase
import renetik.android.store.CSStore
import renetik.android.store.property.CSLateStoreProperty

abstract class CSLateStorePropertyBase<T>(
    override val store: CSStore,
    override val key: String, onChange: ((value: T) -> Unit)?)
    : CSEventPropertyBase<T>(onChange), CSLateStoreProperty<T> {

    var loadedValue: T? = null

    override var value: T
        get() {
            if (loadedValue == null) loadedValue = get()
            if (loadedValue == null) throw unexpected
            return loadedValue!!
        }
        set(value) = value(value)

    abstract fun get(): T?

    override fun value(newValue: T, fire: Boolean) {
        if (loadedValue == newValue) return
        val before = if (store.has(key)) loadedValue else null
        loadedValue = newValue
        set(store, newValue)
        onValueChanged(newValue, fire && before != null)
    }

    override val isLoaded get() = loadedValue.notNull
    override fun toString() = super.toString() + ":$key:$value"
}