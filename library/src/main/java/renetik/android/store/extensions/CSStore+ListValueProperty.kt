package renetik.android.store.extensions

import renetik.android.core.lang.CSHasId
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.CSListValueStoreProperty

fun <T : CSHasId> CSStore.property(
    key: String, values: List<T>, default: List<T>,
    onChange: ((value: List<T>) -> Unit)? = null): CSStoreProperty<List<T>> =
    CSListValueStoreProperty(this, key, values, default, onChange)

fun <T : CSHasId> CSStore.property(
    key: String, array: Array<T>, default: List<T>,
    onChange: ((value: List<T>) -> Unit)? = null): CSStoreProperty<List<T>> =
    property(key, array.asList(), default, onChange)