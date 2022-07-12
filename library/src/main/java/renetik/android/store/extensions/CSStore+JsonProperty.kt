package renetik.android.store.extensions

import renetik.android.core.kotlin.reflect.createInstance
import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.CSJsonListValueStoreProperty
import renetik.android.store.property.value.CSJsonMutableListValueStoreProperty
import renetik.android.store.property.value.CSJsonValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>, default: List<T>,
    onChange: ArgFunc<List<T>>? = null): CSStoreProperty<List<T>> =
    CSJsonListValueStoreProperty(this, key, type, default, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.property(
    key: String, default: List<T>,
    noinline onChange: ArgFunc<List<T>>? = null)
        : CSStoreProperty<List<T>> =
    property(key, T::class, default, onChange)

@JvmName("propertyMutableList")
fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>, default: MutableList<T>,
    onChange: ArgFunc<MutableList<T>>? = null)
        : CSStoreProperty<MutableList<T>> =
    CSJsonMutableListValueStoreProperty(this, key, type, default, onChange)

@JvmName("propertyMutableList")
inline fun <reified T : CSJsonObjectStore> CSStore.property(
    key: String, default: MutableList<T>,
    noinline onChange: ArgFunc<MutableList<T>>? = null)
        : CSStoreProperty<MutableList<T>> =
    property(key, T::class, default, onChange)

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, default: T,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    CSJsonValueStoreProperty(this, key, default, onChange = onChange)

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    property(key, type.createInstance()!!, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.property(
    key: String, noinline onChange: ArgFunc<T>? = null)
        : CSStoreProperty<T> =
    property(key, T::class.createInstance()!!, onChange)

