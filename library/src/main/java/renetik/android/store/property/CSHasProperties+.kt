package renetik.android.store.property

import renetik.android.core.lang.CSHasId
import renetik.android.store.property.value.CSListItemValueStoreEventProperty
import renetik.android.store.property.value.CSListValueStoreEventProperty

fun <T> CSHasProperties.property(
    key: String, values: List<T>, getDefault: () -> T,
    onChange: ((value: T) -> Unit)?) =
    property(key, { values }, getDefault, onChange)

fun <T> CSHasProperties.property(
    key: String, list: List<T>, value: T,
    onChange: ((value: T) -> Unit)? = null)
        : CSListItemValueStoreEventProperty<T> =
    property(key, { list }, { value }, onChange)

fun <T> CSHasProperties.property(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ((value: T) -> Unit)? = null)
        : CSListItemValueStoreEventProperty<T> =
    property(key, list, list[defaultIndex], onChange)

fun <T> CSHasProperties.property(
    key: String, array: Array<T>, value: T,
    onChange: ((value: T) -> Unit)? = null)
        : CSListItemValueStoreEventProperty<T> =
    property(key, array.asList(), value, onChange)

fun <T> CSHasProperties.property(
    key: String, array: Array<T>, valueIndex: Int,
    onChange: ((value: T) -> Unit)? = null)
        : CSListItemValueStoreEventProperty<T> =
    property(key, array.asList(), array[valueIndex], onChange)

fun <T> CSHasProperties.property(
    key: String, getList: () -> List<T>,
    defaultIndex: Int, onChange: ((value: T) -> Unit)?)
        : CSListItemValueStoreEventProperty<T> =
    property(key, getList, { getList()[defaultIndex] }, onChange)

fun <T : CSHasId> CSHasProperties.property(
    key: String, array: Array<T>, value: List<T>,
    onChange: ((value: List<T>) -> Unit)? = null)
        : CSListValueStoreEventProperty<T> =
    property(key, array.asList(), value, onChange)
