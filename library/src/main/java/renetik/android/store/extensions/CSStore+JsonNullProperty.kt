package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.CSJsonNullableStoreProperty
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.nullJsonProperty(
    key: String, type: KClass<T>, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    CSJsonNullableStoreProperty(this, key, type, default, onChange)

inline fun <reified T : CSJsonObjectStore> CSStore.nullJsonProperty(
    key: String, default: T? = null, noinline onChange: ArgFunc<T?>? = null)
        : CSStoreProperty<T?> = nullJsonProperty(key, T::class, default, onChange)