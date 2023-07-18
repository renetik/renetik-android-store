package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.core.lang.CSHasId
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.registerParent
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.CSListValueStoreProperty

fun <T : CSHasId> CSStore.property(
    key: String, values: List<T>, default: List<T>,
    onChange: ArgFunc<List<T>>? = null): CSStoreProperty<List<T>> =
    CSListValueStoreProperty(this, key, values, default, onChange)

fun <T : CSHasId> CSStore.property(
    parent: CSHasDestruct, key: String, values: List<T>, default: List<T>,
    onChange: ArgFunc<List<T>>? = null): CSStoreProperty<List<T>> =
    CSListValueStoreProperty(this, key, values, default, onChange).registerParent(parent)

fun <T : CSHasId> CSStore.property(
    key: String, array: Array<T>, default: List<T>,
    onChange: ArgFunc<List<T>>? = null): CSStoreProperty<List<T>> =
    property(key, array.asList(), default, onChange)

fun <T : CSHasId> CSStore.property(
    parent: CSHasDestruct, key: String, array: Array<T>, default: List<T>,
    onChange: ArgFunc<List<T>>? = null): CSStoreProperty<List<T>> =
    property(parent, key, array.asList(), default, onChange)