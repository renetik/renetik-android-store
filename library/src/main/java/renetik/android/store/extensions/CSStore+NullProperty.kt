package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.registerParent
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.CSBooleanNullableStoreProperty
import renetik.android.store.property.nullable.CSFloatNullableStoreProperty
import renetik.android.store.property.nullable.CSIntNullableStoreProperty
import renetik.android.store.property.nullable.CSStringNullableStoreProperty

fun CSStore.nullStringProperty(
    key: String, default: String? = null,
    onChange: ArgFunc<String?>? = null,
) = CSStringNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullStringProperty(
    parent: CSHasDestruct,
    key: String, default: String? = null,
    onChange: ArgFunc<String?>? = null,
) = CSStringNullableStoreProperty(this, key, default, onChange)
    .registerParent(parent)

fun CSStore.nullBoolProperty(
    key: String, default: Boolean? = null,
    onChange: ArgFunc<Boolean?>? = null,
): CSStoreProperty<Boolean?> =
    CSBooleanNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullIntProperty(
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null,
): CSStoreProperty<Int?> =
    CSIntNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullIntProperty(
    parent: CSHasDestruct,
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null,
): CSStoreProperty<Int?> =
    CSIntNullableStoreProperty(this, key, default, onChange)
        .registerParent(parent)

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
        .registerParent(parent)