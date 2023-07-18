package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.registerParent
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.CSBooleanValueStoreProperty
import renetik.android.store.property.value.CSDoubleValueStoreProperty
import renetik.android.store.property.value.CSFloatValueStoreProperty
import renetik.android.store.property.value.CSIntValueStoreProperty
import renetik.android.store.property.value.CSLongValueStoreProperty
import renetik.android.store.property.value.CSStringValueStoreProperty

fun CSStore.property(
    key: String, default: String,
    onChange: ArgFunc<String>? = null,
): CSStoreProperty<String> =
    CSStringValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: String,
    onChange: ArgFunc<String>? = null,
): CSStoreProperty<String> =
    CSStringValueStoreProperty(this, key, default, onChange).registerParent(parent)

fun CSStore.property(
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null,
): CSStoreProperty<Boolean> =
    CSBooleanValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null,
): CSStoreProperty<Boolean> =
    CSBooleanValueStoreProperty(this, key, default, onChange).registerParent(parent)

fun CSStore.property(
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null
): CSStoreProperty<Int> =
    CSIntValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null,
): CSStoreProperty<Int> =
    CSIntValueStoreProperty(this, key, default, onChange).registerParent(parent)

fun CSStore.property(
    key: String, default: Double,
    onChange: ArgFunc<Double>? = null,
): CSStoreProperty<Double> =
    CSDoubleValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Double,
    onChange: ArgFunc<Double>? = null,
): CSStoreProperty<Double> =
    CSDoubleValueStoreProperty(this, key, default, onChange).registerParent(parent)

fun CSStore.property(
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null,
): CSStoreProperty<Float> =
    CSFloatValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null,
): CSStoreProperty<Float> =
    CSFloatValueStoreProperty(this, key, default, onChange).registerParent(parent)

fun CSStore.property(
    key: String, default: Long,
    onChange: ArgFunc<Long>? = null,
): CSStoreProperty<Long> =
    CSLongValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Long,
    onChange: ArgFunc<Long>? = null,
): CSStoreProperty<Long> =
    CSLongValueStoreProperty(this, key, default, onChange).registerParent(parent)