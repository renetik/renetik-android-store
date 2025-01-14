package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.parent
import renetik.android.event.registration.plus
import renetik.android.store.CSStore
import renetik.android.store.property.listenStore
import renetik.android.store.property.listenStoreOnce
import renetik.android.store.property.value.CSBooleanValueStoreProperty
import renetik.android.store.property.value.CSDoubleValueStoreProperty
import renetik.android.store.property.value.CSFloatValueStoreProperty
import renetik.android.store.property.value.CSIntValueStoreProperty
import renetik.android.store.property.value.CSLongValueStoreProperty
import renetik.android.store.property.value.CSStringValueStoreProperty

fun CSStore.property(
    key: String, default: String,
    onChange: ArgFunc<String>? = null,
) = CSStringValueStoreProperty(this, key, default, onChange)

fun CSStore.dataProperty(
    key: String, default: String,
    onChange: ArgFunc<String>? = null,
) = property(key, default, onChange).listenStoreOnce()

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: String,
    onChange: ArgFunc<String>? = null,
) = CSStringValueStoreProperty(this, key, default, onChange)
    .parent(parent).listenStore()

fun CSStore.property(
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null,
) = CSBooleanValueStoreProperty(this, key, default, onChange)

fun CSStore.dataProperty(
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null,
) = property(key, default, onChange).listenStoreOnce()

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Boolean,
    onChange: ArgFunc<Boolean>? = null,
) = CSBooleanValueStoreProperty(this, key, default, onChange)
    .parent(parent).listenStore()

fun CSStore.property(
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null
) = CSIntValueStoreProperty(this, key, default, onChange)

fun CSStore.dataProperty(
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null
) = property(key, default, onChange).listenStoreOnce()

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Int,
    onChange: ArgFunc<Int>? = null,
) = CSIntValueStoreProperty(this, key, default, onChange)
    .parent(parent).listenStore()

fun CSStore.property(
    key: String, default: Double,
    onChange: ArgFunc<Double>? = null,
) = CSDoubleValueStoreProperty(this, key, default, onChange)

fun CSStore.dataProperty(
    key: String, default: Double,
    onChange: ArgFunc<Double>? = null,
) = property(key, default, onChange).listenStoreOnce()

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Double,
    onChange: ArgFunc<Double>? = null,
) = CSDoubleValueStoreProperty(this, key, default, onChange)
    .parent(parent).listenStore()

fun CSStore.property(
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null,
) = CSFloatValueStoreProperty(this, key, default, onChange)

fun CSStore.dataProperty(
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null,
) = property(key, default, onChange).listenStoreOnce()

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Float,
    onChange: ArgFunc<Float>? = null,
) = CSFloatValueStoreProperty(this, key, default, onChange)
    .parent(parent).listenStore()

fun CSStore.property(
    key: String, default: Long,
    onChange: ArgFunc<Long>? = null,
) = CSLongValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    parent: CSHasDestruct,
    key: String, default: Long,
    onChange: ArgFunc<Long>? = null,
) = CSLongValueStoreProperty(this, key, default, onChange)
    .parent(parent).listenStore()