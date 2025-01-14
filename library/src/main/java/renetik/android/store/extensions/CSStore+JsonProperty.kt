package renetik.android.store.extensions

import renetik.android.core.kotlin.reflect.createInstance
import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.parent
import renetik.android.store.CSStore
import renetik.android.store.property.listenStore
import renetik.android.store.property.listenStoreOnce
import renetik.android.store.property.value.CSJsonValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, default: T,
    onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> = CSJsonValueStoreProperty(this, key, default, onChange)

fun <T : CSJsonObjectStore> CSStore.dataProperty(
    key: String, default: T,
    onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> = property(key, default, onChange).listenStoreOnce()

fun <T : CSJsonObjectStore> CSStore.property(
    parent: CSHasDestruct, key: String, default: T,
    onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> = CSJsonValueStoreProperty(this, key, default, onChange)
    .parent(parent).listenStore()

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>,
    onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> = property(key, type.createInstance()!!, onChange)

fun <T : CSJsonObjectStore> CSStore.property(
    parent: CSHasDestruct, key: String, type: KClass<T>,
    onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> = property(parent, key, type.createInstance()!!, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.property(
    key: String,
    noinline onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> = property(key, T::class.createInstance()!!, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.dataProperty(
    key: String,
    noinline onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> = property(key, onChange).listenStoreOnce()

inline fun <reified T : CSJsonObjectStore> CSStore.property(
    parent: CSHasDestruct, key: String,
    noinline onChange: ArgFunc<T>? = null
): CSJsonValueStoreProperty<T> =
    property(parent, key, T::class.createInstance()!!, onChange)

