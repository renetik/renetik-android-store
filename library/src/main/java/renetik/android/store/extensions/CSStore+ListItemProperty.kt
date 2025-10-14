package renetik.android.store.extensions

import renetik.android.core.lang.ArgFun
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.parent
import renetik.android.store.CSStore
import renetik.android.store.property.listenLoad
import renetik.android.store.property.listenLoadOnce
import renetik.android.store.property.value.CSListItemValueStoreProperty

fun <T> CSStore.property(
    key: String, getValues: () -> List<T>, getDefault: () -> T,
    onChange: ArgFun<T>? = null
) = CSListItemValueStoreProperty(this, key, getValues, getDefault, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct? = null, key: String, getValues: () -> Collection<T>,
    getDefault: () -> T, onChange: ArgFun<T>? = null
) = CSListItemValueStoreProperty(
    this, key, getValues, getDefault, onChange
).apply { parent?.also(::parent) }.listenLoad()

fun <T> CSStore.property(
    parent: CSHasDestruct? = null, key: String, getValues: () -> List<T>,
    default: T, onChange: ArgFun<T>? = null
) = property(parent, key, getValues, getDefault = { default }, onChange)

fun <T> CSStore.property(
    key: String, values: List<T>, getDefault: () -> T,
    onChange: ArgFun<T>? = null
) = property(key, getValues = { values }, getDefault, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: List<T>, getDefault: () -> T,
    onChange: ArgFun<T>? = null
) = property(parent, key, getValues = { values }, getDefault, onChange)

fun <T> CSStore.property(
    key: String, values: List<T>, default: T,
    onChange: ArgFun<T>? = null
) = property(key, getValues = { values }, getDefault = { default }, onChange)

fun <T> CSStore.dataProperty(
    key: String, values: List<T>, default: T,
    onChange: ArgFun<T>? = null
) = property(key, values, default, onChange).listenLoadOnce()

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: List<T>, default: T,
    onChange: ArgFun<T>? = null
) = property(parent, key, getValues = { values }, getDefault = { default }, onChange)

fun <T> CSStore.property(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFun<T>? = null
) = property(key, list, list[defaultIndex], onChange)

fun <T> CSStore.dataProperty(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFun<T>? = null
) = property(key, list, list[defaultIndex], onChange).listenLoadOnce()

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, list: List<T>, defaultIndex: Int,
    onChange: ArgFun<T>? = null
) = property(parent, key, list, list[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, values: Array<T>, default: T,
    onChange: ArgFun<T>? = null
) = property(key, values.asList(), default, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: Array<T>, default: T,
    onChange: ArgFun<T>? = null
) = property(parent, key, values.asList(), default, onChange)

fun <T> CSStore.property(
    key: String, values: Array<T>, defaultIndex: Int,
    onChange: ArgFun<T>? = null
) = property(key, values.asList(), values[defaultIndex], onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, values: Array<T>, defaultIndex: Int,
    onChange: ArgFun<T>? = null
) = property(parent, key, values.asList(), values[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, getValues: () -> List<T>, defaultIndex: Int,
    onChange: ArgFun<T>? = null
) = property(key, getValues, { getValues()[defaultIndex] }, onChange)

fun <T> CSStore.property(
    parent: CSHasDestruct, key: String, getValues: () -> List<T>, defaultIndex: Int = 0,
    onChange: ArgFun<T>? = null
) = property(parent, key, getValues, getDefault = { getValues()[defaultIndex] }, onChange)
