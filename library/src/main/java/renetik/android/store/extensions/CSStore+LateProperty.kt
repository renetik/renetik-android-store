package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.late.*
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

fun <T : CSJsonObjectStore> CSStore.lateProperty(
    key: String, listType: KClass<T>,
    onChange: ArgFunc<List<T>>? = null): CSStoreProperty<List<T>> =
    CSJsonListLateStoreProperty(this, key, listType, onChange)

fun CSStore.lateStringProperty(
    key: String,
    onChange: ArgFunc<String>? = null): CSStoreProperty<String> =
    CSStringLateStoreProperty(this, key, onChange)

fun CSStore.lateIntProperty(
    key: String,
    onChange: ArgFunc<Int>? = null): CSStoreProperty<Int> =
    CSIntLateStoreProperty(this, key, onChange)

fun CSStore.lateBoolProperty(
    key: String,
    onChange: ArgFunc<Boolean>? = null): CSStoreProperty<Boolean> =
    CSBooleanLateStoreProperty(this, key, onChange)

fun <T> CSStore.lateItemProperty(
    key: String, values: Iterable<T>,
    onChange: ArgFunc<T>? = null): CSStoreProperty<T> =
    CSValuesItemLateStoreProperty(this, key, values, onChange)
