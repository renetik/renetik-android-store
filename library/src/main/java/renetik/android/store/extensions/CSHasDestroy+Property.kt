package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestroy
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty

fun CSHasDestroy.property(
    key: String, default: String,
    onChange: ArgFunc<String>? = null): CSStoreProperty<String> =
    CSStore.store.property(this, key, default, onChange)

fun CSHasDestroy.property(
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null): CSStoreProperty<Boolean> =
    CSStore.store.property(this, key, default, onChange)

fun CSHasDestroy.property(
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null): CSStoreProperty<Int> =
    CSStore.store.property(this, key, default, onChange)

fun CSHasDestroy.property(
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null): CSStoreProperty<Float> =
    CSStore.store.property(this, key, default, onChange)

fun CSHasDestroy.property(
    key: String, default: Long,
    onChange: ArgFunc<Long>? = null): CSStoreProperty<Long> =
    CSStore.store.property(this, key, default, onChange)

fun <T> CSHasDestroy.property(
    key: String, values: List<T>, default: T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    CSStore.store.property(this, key, values, default, onChange)

fun <T> CSHasDestroy.property(
    key: String, values: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, values, values[defaultIndex], onChange)

fun <T> CSHasDestroy.property(
    key: String, values: Array<T>, default: T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    CSStore.store.property(this, key, values, default, onChange)

fun <T> CSHasDestroy.property(
    key: String, values: Array<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    CSStore.store.property(this, key, values, defaultIndex, onChange)