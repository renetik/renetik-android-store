package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.nullable.CSJsonTypeNullableStoreProperty
import renetik.android.store.property.value.CSJsonListValueStoreProperty
import renetik.android.store.property.value.CSJsonTypeValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>, default: List<T>,
    onChange: ArgFunc<List<T>>? = null): CSStoreProperty<List<T>> =
    CSJsonListValueStoreProperty(this, key, type, default, listenStoreChanged = false, onChange)

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>, default: T? = null,
    onChange: ArgFunc<T?>? = null): CSStoreProperty<T?> =
    CSJsonTypeNullableStoreProperty(this, key, type, default, onChange)

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    CSJsonTypeValueStoreProperty(this, key, type, listenStoreChanged = false, onChange)