package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSLateStoreProperty
import renetik.android.store.property.late.CSJsonListLateStoreProperty
import renetik.android.store.property.late.CSJsonListListLateStoreProperty
import renetik.android.store.property.late.CSJsonLateStoreProperty
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.lateJsonProperty(
    key: String, listType: KClass<T>,
    onChange: ArgFunc<T>? = null): CSLateStoreProperty<T> =
    CSJsonLateStoreProperty(this, key, listType, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.lateJsonProperty(
    key: String, noinline onChange: ArgFunc<T>? = null): CSLateStoreProperty<T> =
    lateJsonProperty(key, T::class, onChange)

fun <T : CSJsonObjectStore> CSStore.lateJsonListProperty(
    key: String, listType: KClass<T>,
    onChange: ArgFunc<List<T>>? = null): CSLateStoreProperty<List<T>> =
    CSJsonListLateStoreProperty(this, key, listType, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.lateJsonListProperty(
    key: String, noinline onChange: ArgFunc<List<T>>? = null): CSLateStoreProperty<List<T>> =
    lateJsonListProperty(key, T::class, onChange)

fun <T : CSJsonObjectStore> CSStore.lateJsonListListProperty(
    key: String, listType: KClass<T>,
    onChange: ArgFunc<List<List<T>>>? = null): CSLateStoreProperty<List<List<T>>> =
    CSJsonListListLateStoreProperty(this, key, listType, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.lateJsonListListProperty(
    key: String, noinline onChange: ArgFunc<List<List<T>>>? = null)
        : CSLateStoreProperty<List<List<T>>> = lateJsonListListProperty(key, T::class, onChange)
