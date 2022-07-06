package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.store.CSStore
import renetik.android.store.property.CSLateStoreProperty
import renetik.android.store.property.late.CSBooleanLateStoreProperty
import renetik.android.store.property.late.CSIntLateStoreProperty
import renetik.android.store.property.late.CSListItemLateStoreProperty
import renetik.android.store.property.late.CSStringLateStoreProperty

fun CSStore.lateStringProperty(
    key: String,
    onChange: ArgFunc<String>? = null): CSLateStoreProperty<String> =
    CSStringLateStoreProperty(this, key, onChange)

fun CSStore.lateIntProperty(
    key: String,
    onChange: ArgFunc<Int>? = null): CSLateStoreProperty<Int> =
    CSIntLateStoreProperty(this, key, onChange)

fun CSStore.lateBoolProperty(
    key: String,
    onChange: ArgFunc<Boolean>? = null): CSLateStoreProperty<Boolean> =
    CSBooleanLateStoreProperty(this, key, onChange)

fun <T> CSStore.lateListItemProperty(
    key: String, values: Iterable<T>,
    onChange: ArgFunc<T>? = null): CSLateStoreProperty<T> =
    CSListItemLateStoreProperty(this, key, values, onChange)

fun <T> CSStore.lateListItemProperty(
    key: String, values: Array<T>,
    onChange: ArgFunc<T>? = null): CSLateStoreProperty<T> =
    lateListItemProperty(key, values.asIterable(), onChange)
