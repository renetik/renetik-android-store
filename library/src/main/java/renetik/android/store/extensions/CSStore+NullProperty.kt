package renetik.android.store.extensions

import renetik.android.core.kotlin.reflect.createInstance
import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.*
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun CSStore.nullStringProperty(
    key: String, default: String? = null,
    onChange: ArgFunc<String?>? = null): CSStoreProperty<String?> =
    CSStringNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullBoolProperty(
    key: String, default: Boolean? = null,
    onChange: ArgFunc<Boolean?>? = null): CSStoreProperty<Boolean?> =
    CSBooleanNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullIntProperty(
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null): CSStoreProperty<Int?> =
    CSIntNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullFloatProperty(
    key: String, default: Float? = null,
    onChange: ArgFunc<Float?>? = null): CSStoreProperty<Float?> =
    CSFloatNullableStoreProperty(this, key, default, onChange)

fun <T> CSStore.nullProperty(
    key: String, getValues: () -> List<T>, getDefault: () -> T?,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    CSListItemNullableStoreProperty(this, key, getValues, getDefault, onChange)

fun <T> CSStore.nullProperty(
    key: String, values: List<T>, getDefault: () -> T?,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, { values }, getDefault, onChange)

fun <T> CSStore.nullProperty(
    key: String, list: List<T>, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, { list }, { default }, onChange)

fun <T> CSStore.nullProperty(
    key: String, getList: () -> List<T>, defaultIndex: Int,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, getList, { getList()[defaultIndex] }, onChange)

fun <T> CSStore.nullProperty(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, list, list[defaultIndex], onChange)

fun <T> CSStore.nullProperty(
    key: String, array: Array<T>, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, array.asList(), default, onChange)

fun <T> CSStore.nullProperty(
    key: String, array: Array<T>, defaultIndex: Int,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, array.asList(), array[defaultIndex], onChange)

fun <T : CSJsonObjectStore> CSStore.nullProperty(
    key: String, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    CSJsonNullableStoreProperty(this, key, default, onChange)

fun <T : CSJsonObjectStore> CSStore.nullProperty(
    key: String, type: KClass<T>,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, type.createInstance()!!, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.nullProperty(
    key: String, noinline onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    nullProperty(key, T::class, onChange)