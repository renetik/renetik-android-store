package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.parent
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.CSListItemValueStoreProperty

fun <T> CSStore.property(
    key: String, getValues: () -> List<T>, getDefault: () -> T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    CSListItemValueStoreProperty(this, key, getValues, getDefault, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, getValues: () -> Collection<T>,
    getDefault: () -> T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    CSListItemValueStoreProperty(
        this, key, getValues, getDefault, onChange
    ).parent(parent)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, getValues: () -> List<T>,
    default: T, onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(parent, key, getValues, getDefault = { default }, onChange)

fun <T> CSStore.property(
    key: String, values: List<T>, getDefault: () -> T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(key, getValues = { values }, getDefault, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: List<T>, getDefault: () -> T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(parent, key, getValues = { values }, getDefault, onChange)

fun <T> CSStore.property(
    key: String, values: List<T>, default: T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(key, getValues = { values }, getDefault = { default }, onChange)


fun <T> CSStore.dataProperty(
    key: String, values: List<T>, default: T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> = property(key, values, default, onChange)
    .apply { listenStoreLoad() }

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: List<T>, default: T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(parent, key, getValues = { values }, getDefault = { default }, onChange)

fun <T> CSStore.property(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(key, list, list[defaultIndex], onChange)


fun <T> CSStore.dataProperty(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(key, list, list[defaultIndex], onChange).apply { listenStoreLoad() }

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(parent, key, list, list[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, values: Array<T>, default: T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(key, values.asList(), default, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: Array<T>, default: T,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(parent, key, values.asList(), default, onChange)

fun <T> CSStore.property(
    key: String, values: Array<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(key, values.asList(), values[defaultIndex], onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: Array<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(parent, key, values.asList(), values[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, getValues: () -> List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(key, getValues, { getValues()[defaultIndex] }, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, getValues: () -> List<T>, defaultIndex: Int = 0,
    onChange: ArgFunc<T>? = null
): CSStoreProperty<T> =
    property(parent, key, getValues, getDefault = { getValues()[defaultIndex] }, onChange)
