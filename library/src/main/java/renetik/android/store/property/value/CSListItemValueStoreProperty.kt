package renetik.android.store.property.value

import renetik.android.core.kotlin.toId
import renetik.android.core.lang.property.CSListValuesProperty
import renetik.android.json.obj.getValue
import renetik.android.store.CSStore

class CSListItemValueStoreProperty<T>(
    store: CSStore, key: String,
    val getValues: () -> List<T>,
    val getDefault: () -> T,
    onChange: ((value: T) -> Unit)? = null
) : CSValueStoreProperty<T>(store, key,onChange), CSListValuesProperty<T> {

    override val values: List<T> get() = getValues()
    override val default: T get() = getDefault()
    override fun get(store: CSStore): T? = store.getValue(key, values)
    override fun set(store: CSStore, value: T) = store.set(key, value.toId())
}