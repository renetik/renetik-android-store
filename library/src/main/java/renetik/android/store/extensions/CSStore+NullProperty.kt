package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.parent
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.listenLoad
import renetik.android.store.property.listenLoadOnce
import renetik.android.store.property.nullable.CSBooleanNullableStoreProperty
import renetik.android.store.property.nullable.CSDoubleNullableStoreProperty
import renetik.android.store.property.nullable.CSFloatNullableStoreProperty
import renetik.android.store.property.nullable.CSIntNullableStoreProperty
import renetik.android.store.property.nullable.CSStringNullableStoreProperty

fun CSStore.nullStringProperty(
    key: String, default: String? = null,
    onChange: ArgFunc<String?>? = null,
) = CSStringNullableStoreProperty(this, key, default, onChange)

fun CSStore.dataNullStringProperty(
    key: String, default: String? = null,
    onChange: ArgFunc<String?>? = null,
) = nullStringProperty(key, default, onChange).listenLoadOnce()

fun CSStore.nullStringProperty(
    parent: CSHasDestruct,
    key: String, default: String? = null,
    onChange: ArgFunc<String?>? = null,
) = CSStringNullableStoreProperty(this, key, default, onChange)
    .parent(parent).listenLoad()

fun CSStore.nullBoolProperty(
    key: String, default: Boolean? = null,
    onChange: ArgFunc<Boolean?>? = null,
) = CSBooleanNullableStoreProperty(this, key, default, onChange)

fun CSStore.dataNullBoolProperty(
    key: String, default: Boolean? = null,
    onChange: ArgFunc<Boolean?>? = null,
): CSStoreProperty<Boolean?> =
    nullBoolProperty(key, default, onChange).listenLoadOnce()

fun CSStore.nullIntProperty(
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null,
) = CSIntNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullDoubleProperty(
    key: String, default: Double? = null,
    onChange: ArgFunc<Double?>? = null,
) = CSDoubleNullableStoreProperty(this, key, default, onChange)

fun CSStore.dataNullIntProperty(
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null,
): CSStoreProperty<Int?> =
    nullIntProperty(key, default, onChange).listenLoadOnce()

fun CSStore.nullIntProperty(
    parent: CSHasDestruct,
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null,
): CSStoreProperty<Int?> =
    CSIntNullableStoreProperty(this, key, default, onChange)
        .parent(parent).listenLoad()

fun CSStore.nullFloatProperty(
    key: String, default: Float? = null,
    onChange: ArgFunc<Float?>? = null,
): CSStoreProperty<Float?> =
    CSFloatNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullFloatProperty(
    parent: CSHasDestruct,
    key: String, default: Float? = null,
    onChange: ArgFunc<Float?>? = null,
): CSStoreProperty<Float?> =
    CSFloatNullableStoreProperty(this, key, default, onChange)
        .parent(parent).listenLoad()