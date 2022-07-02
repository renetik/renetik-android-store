package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.CSListItemValueStoreProperty

fun <T> CSStore.property(
    key: String, getValues: () -> List<T>, getDefault: () -> T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    CSListItemValueStoreProperty(this, key, getValues, getDefault, onChange = onChange)

fun <T> CSStore.property(
    key: String, values: List<T>, getDefault: () -> T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, { values }, getDefault, onChange)

fun <T> CSStore.property(
    key: String, list: List<T>, default: T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, { list }, { default }, onChange)

fun <T> CSStore.property(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, list, list[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, array: Array<T>, default: T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, array.asList(), default, onChange)

fun <T> CSStore.property(
    key: String, array: Array<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, array.asList(), array[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, getList: () -> List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, getList, { getList()[defaultIndex] }, onChange)
