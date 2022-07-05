package renetik.android.store.extensions

import renetik.android.core.kotlin.reflect.createInstance
import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.*
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun CSStore.nullStringProperty(
    key: String, default: String? = null,
    onChange: ArgFunc<String?>? = null): CSStoreProperty<String?> =
    CSStringNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullBoolProperty(
    key: String, default: Boolean? = null,
    onChange: ArgFunc<Boolean?>? = null): CSStoreProperty<Boolean?> =
    CSBooleanNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullIntProperty(
    key: String, default: Int? = null,
    onChange: ArgFunc<Int?>? = null): CSStoreProperty<Int?> =
    CSIntNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullFloatProperty(
    key: String, default: Float? = null,
    onChange: ArgFunc<Float?>? = null): CSStoreProperty<Float?> =
    CSFloatNullableStoreProperty(this, key, default, onChange)