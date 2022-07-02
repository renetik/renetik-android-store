package renetik.android.store.extensions

import android.content.Context
import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore.Companion.store
import renetik.android.store.property.CSStoreProperty

fun Context.property(
    key: String, default: String,
    onChange: ArgFunc<String>? = null): CSStoreProperty<String> =
    store.property(key, default, onChange)

fun Context.property(
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null): CSStoreProperty<Float> =
    store.property(key, default, onChange)

fun Context.property(
    key: String, default: Long,
    onChange: ArgFunc<Long>? = null): CSStoreProperty<Long> =
    store.property(key, default, onChange)

fun Context.property(
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null): CSStoreProperty<Int> =
    store.property(key, default, onChange)

fun Context.property(
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null): CSStoreProperty<Boolean> =
    store.property(key, default, onChange)

fun <T> Context.property(
    key: String, values: List<T>, default: T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    store.property(key, values, default, onChange)

fun <T> Context.property(
    key: String, values: List<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, values, values[defaultIndex], onChange)

fun <T> Context.property(
    key: String, values: Array<T>, default: T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    store.property(key, values, default, onChange)

fun <T> Context.property(
    key: String, values: Array<T>, defaultIndex: Int,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    store.property(key, values, defaultIndex, onChange)