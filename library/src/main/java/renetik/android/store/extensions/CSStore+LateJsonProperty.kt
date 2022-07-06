package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSLateStoreProperty
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.late.*
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.lateJsonProperty(
    key: String, listType: KClass<T>,
    onChange: ArgFunc<T>? = null): CSLateStoreProperty<T> =
    CSJsonTypeLateStoreProperty(this, key, listType, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.lateJsonProperty(
    key: String, noinline onChange: ArgFunc<T>? = null): CSLateStoreProperty<T> =
    CSJsonTypeLateStoreProperty(this, key, T::class, onChange)

fun <T : CSJsonObjectStore> CSStore.lateJsonListProperty(
    key: String, listType: KClass<T>,
    onChange: ArgFunc<List<T>>? = null): CSLateStoreProperty<List<T>> =
    CSJsonListLateStoreProperty(this, key, listType, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.lateJsonListProperty(
    key: String, noinline onChange: ArgFunc<List<T>>? = null): CSLateStoreProperty<List<T>> =
    CSJsonListLateStoreProperty(this, key, T::class, onChange)
