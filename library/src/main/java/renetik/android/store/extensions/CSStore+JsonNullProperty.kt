package renetik.android.store.extensions

import renetik.android.core.kotlin.reflect.createInstance
import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.*
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.nullJsonProperty(
    key: String, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    CSJsonNullableStoreProperty(this, key, default, onChange)

//fun <T : CSJsonObjectStore> CSStore.nullJsonProperty(
//    key: String, type: KClass<T>,
//    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
//    nullJsonProperty(key, type.createInstance()!!, onChange)
//
//inline fun <reified T : CSJsonObjectStore> CSStore.nullJsonProperty(
//    key: String, noinline onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
//    nullJsonProperty(key, T::class, onChange)