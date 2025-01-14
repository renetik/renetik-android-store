package renetik.android.store.context

import renetik.android.core.lang.ArgFunc

fun <T> CSStoreContext.property(
    key: String, getValues: () -> List<T>,
    defaultIndex: Int = 0, onChange: ArgFunc<T>? = null
) = property(key, getValues, { getValues()[defaultIndex] }, onChange)

fun <T> CSStoreContext.property(
    key: String, getValues: () -> List<T>, default: T, onChange: ArgFunc<T>? = null
) = property(key, getValues, { default }, onChange)

fun <T> CSStoreContext.property(
    key: String, values: List<T>, defaultIndex: Int, onChange: ArgFunc<T>? = null
) = property(key, { values }, values[defaultIndex], onChange)

fun <T> CSStoreContext.property(
    key: String, values: List<T>, default: T, onChange: ArgFunc<T>? = null
) = property(key, { values }, { default }, onChange)

fun <T> CSStoreContext.property(
    key: String, values: List<T>, default: () -> T, onChange: ArgFunc<T>? = null
) = property(key, { values }, default, onChange)