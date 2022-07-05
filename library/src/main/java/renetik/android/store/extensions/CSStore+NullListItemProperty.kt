package renetik.android.store.extensions

import renetik.android.core.kotlin.reflect.createInstance
import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.*
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T> CSStore.nullListItemProperty(
    key: String, getValues: () -> List<T>, getDefault: () -> T?,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    CSListItemNullableStoreProperty(this, key, getValues, getDefault, onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, values: List<T>, getDefault: () -> T?,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullListItemProperty(key, { values }, getDefault, onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, list: List<T>, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullListItemProperty(key, { list }, { default }, onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, getList: () -> List<T>, defaultIndex: Int,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullListItemProperty(key, getList, { getList()[defaultIndex] }, onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullListItemProperty(key, list, list[defaultIndex], onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, array: Array<T>, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullListItemProperty(key, array.asList(), default, onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, array: Array<T>, defaultIndex: Int,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullListItemProperty(key, array.asList(), array[defaultIndex], onChange)