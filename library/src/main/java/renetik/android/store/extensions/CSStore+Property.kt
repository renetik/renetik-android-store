package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.*

fun CSStore.property(
    key: String, default: String,
    onChange: ArgFunc<String>? = null): CSStoreProperty<String> =
    CSStringValueStoreProperty(this, key, default, listenStoreChanged = false, onChange)

fun CSStore.property(
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null): CSStoreProperty<Boolean> =
    CSBooleanValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null): CSStoreProperty<Int> =
    CSIntValueStoreProperty(this, key, default, onChange = onChange)

fun CSStore.property(
    key: String, default: Double,
    onChange: ArgFunc<Double>? = null): CSStoreProperty<Double> =
    CSDoubleValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null): CSStoreProperty<Float> =
    CSFloatValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    key: String, default: Long,
    onChange: ArgFunc<Long>? = null): CSStoreProperty<Long> =
    CSLongValueStoreProperty(this, key, default, onChange)