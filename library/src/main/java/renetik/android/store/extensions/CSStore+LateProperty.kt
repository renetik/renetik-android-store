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
    onChange: ArgFunc<String>? = null
) = CSStringLateStoreProperty(this, key, onChange)

fun CSStore.dataLateStringProperty(
    key: String,
    onChange: ArgFunc<String>? = null
) = lateStringProperty(key, onChange).apply { listenStoreLoad() }

fun CSStore.lateIntProperty(
    key: String,
    onChange: ArgFunc<Int>? = null
): CSLateStoreProperty<Int> =
    CSIntLateStoreProperty(this, key, onChange)

fun CSStore.dataLateIntProperty(
    key: String,
    onChange: ArgFunc<Int>? = null
) = lateIntProperty(key, onChange).apply { listenStoreLoad() }

fun CSStore.lateBoolProperty(
    key: String,
    onChange: ArgFunc<Boolean>? = null
): CSLateStoreProperty<Boolean> =
    CSBooleanLateStoreProperty(this, key, onChange)

fun <T> CSStore.lateListItemProperty(
    key: String, values: Iterable<T>,
    onChange: ArgFunc<T>? = null
): CSLateStoreProperty<T> =
    CSListItemLateStoreProperty(this, key, values, onChange)

fun <T> CSStore.dataLateListItemProperty(
    key: String, values: Iterable<T>,
    onChange: ArgFunc<T>? = null
): CSLateStoreProperty<T> =
    CSListItemLateStoreProperty(this, key, values, onChange).apply { listenStoreLoad() }

fun <T> CSStore.lateListItemProperty(
    key: String, values: Array<T>,
    onChange: ArgFunc<T>? = null
): CSLateStoreProperty<T> =
    lateListItemProperty(key, values.asIterable(), onChange)
