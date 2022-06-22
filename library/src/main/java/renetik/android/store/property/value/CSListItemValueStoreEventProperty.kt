package renetik.android.store.property.value

import renetik.android.core.lang.property.CSListValuesProperty
import renetik.android.store.CSStore
import renetik.android.store.getValue
import renetik.android.core.kotlin.toId

class CSListItemValueStoreEventProperty<T>(
    store: CSStore, key: String,
    val getValues: () -> List<T>,
    val getDefault: () -> T,
    listenStoreChanged: Boolean = false,
    onChange: ((value: T) -> Unit)? = null
) : CSValueStoreEventProperty<T>(store, key,
    listenStoreChanged, onChange), CSListValuesProperty<T> {

    constructor(
        store: CSStore, key: String,
        values: List<T>, default: T,
        listenStoreChanged: Boolean = false,
        onChange: ((value: T) -> Unit)? = null
    ) : this(store, key, { values },
        getDefault = { default },
        listenStoreChanged, onChange)

    override val values: List<T> get() = getValues()
    override val defaultValue: T get() = getDefault()
    override var _value: T = load()
    override fun get(store: CSStore): T? = store.getValue(key, values)
    override fun set(store: CSStore, value: T) = store.set(key, value.toId())
}