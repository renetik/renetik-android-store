package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.CSBooleanNullableStoreProperty
import renetik.android.store.property.nullable.CSIntNullableStoreProperty
import renetik.android.store.property.nullable.CSListItemNullableStoreProperty

fun CSStore.nullBoolProperty(
    key: String, default: Boolean? = null,
    onChange: ArgFunc<Boolean?>? = null): CSStoreProperty<Boolean?> =
    CSBooleanNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullIntProperty(
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null): CSStoreProperty<Int?> =
    CSIntNullableStoreProperty(this, key, default, onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, values: Iterable<T>, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    CSListItemNullableStoreProperty(this, key, values, default, onChange)