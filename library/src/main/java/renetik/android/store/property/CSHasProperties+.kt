package renetik.android.store.property

import renetik.android.core.lang.CSHasId
import renetik.android.event.property.CSEventProperty
import renetik.android.store.property.value.CSListItemValueStoreProperty
import renetik.android.store.property.value.CSListValueStoreProperty

fun <T> CSStoredProperties.property(
    key: String, values: List<T>, getDefault: () -> T,
    onChange: ((value: T) -> Unit)?) =
    property(key, { values }, getDefault, onChange)

fun <T> CSStoredProperties.property(
    key: String, list: List<T>, default: T,
    onChange: ((value: T) -> Unit)? = null)
        : CSEventProperty<T> =
    property(key, { list }, { default }, onChange)

fun <T> CSStoredProperties.property(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ((value: T) -> Unit)? = null)
        : CSEventProperty<T> =
    property(key, list, list[defaultIndex], onChange)

fun <T> CSStoredProperties.property(
    key: String, array: Array<T>, default: T,
    onChange: ((value: T) -> Unit)? = null)
        : CSEventProperty<T> =
    property(key, array.asList(), default, onChange)

fun <T> CSStoredProperties.property(
    key: String, array: Array<T>, defaultIndex: Int,
    onChange: ((value: T) -> Unit)? = null)
        : CSEventProperty<T> =
    property(key, array.asList(), array[defaultIndex], onChange)

fun <T> CSStoredProperties.property(
    key: String, getList: () -> List<T>, defaultIndex: Int,
    onChange: ((value: T) -> Unit)?)
        : CSEventProperty<T> =
    property(key, getList, { getList()[defaultIndex] }, onChange)

fun <T : CSHasId> CSStoredProperties.property(
    key: String, array: Array<T>, default: List<T>,
    onChange: ((value: List<T>) -> Unit)? = null)
        : CSEventProperty<List<T>> =
    property(key, array.asList(), default, onChange)
