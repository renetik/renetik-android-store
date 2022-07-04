package renetik.android.store.property.nullable

import renetik.android.store.CSStore
import renetik.android.store.property.value.CSValueStoreProperty

//abstract class CSNullableStoreProperty<T>(
//    final override val store: CSStore,
//    final override val key: String,
//    val defaultValue: T?,
//    val listenStoreChanged: Boolean = false,
//    onApply: ((value: T?) -> Unit)? = null)
//    : CSEventPropertyBase<T?>(onApply), CSStoreProperty<T?> {
//
//    protected var _value: T? = null
//    abstract fun get(store: CSStore): T?
//
//    fun load(): T? = load(store)
//    fun load(store: CSStore): T? = get(store) ?: defaultValue
//
//    private val storeEventChangedRegistration =
//        if (listenStoreChanged) register(store.eventChanged.listen {
//            val newValue = load()
//            if (_value == newValue) return@listen
//            _value = newValue
//            onValueChanged(newValue)
//        }) else null
//
//    var isLoaded = false
//    override var value: T?
//        get() {
//            if (!isLoaded) {
//                _value = load()
//                isLoaded = true
//            }
//            return _value
//        }
//        set(value) = value(value)
//
//    override fun value(newValue: T?, fire: Boolean) {
//        if (value == newValue) return
//        _value = newValue
//        storeEventChangedRegistration?.pause().use { set(store, newValue) }
//        onValueChanged(newValue, fire)
//    }
//
//    override fun toString() = "$key $value"
//}

abstract class CSNullableStoreProperty<T>(
    store: CSStore, key: String,
    override val default: T? = null,
    onChange: ((value: T?) -> Unit)? = null)
    : CSValueStoreProperty<T?>(store, key, onChange) {
//    override var value: T?
//        get() = if (store.has(key)) get(store) else default
//        set(value) = value(value)
}