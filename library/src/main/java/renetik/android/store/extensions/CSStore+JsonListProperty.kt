package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.registerParent
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.CSJsonListValueStoreProperty
import renetik.android.store.property.value.CSJsonMutableListValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore

inline fun <reified T : CSJsonObjectStore> CSStore.listProperty(
    key: String, default: List<T>,
    noinline onChange: ArgFunc<List<T>>? = null
): CSStoreProperty<List<T>> = CSJsonListValueStoreProperty(
    this, key, T::class, default, onChange
)

inline fun <reified T : CSJsonObjectStore> CSStore.listProperty(
    parent: CSHasDestruct, key: String, default: List<T>,
    noinline onChange: ArgFunc<List<T>>? = null
): CSStoreProperty<List<T>> = CSJsonListValueStoreProperty(
    this, key, T::class, default, onChange
).registerParent(parent)

@JvmName("propertyMutableList")
inline fun <reified T : CSJsonObjectStore> CSStore.mutableListProperty(
    key: String, default: MutableList<T>,
    noinline onChange: ArgFunc<MutableList<T>>? = null
): CSStoreProperty<MutableList<T>> = CSJsonMutableListValueStoreProperty(
    this, key, T::class, default, onChange
)

@JvmName("propertyMutableList")
inline fun <reified T : CSJsonObjectStore> CSStore.mutableListProperty(
    parent: CSHasDestruct, key: String, default: MutableList<T>,
    noinline onChange: ArgFunc<MutableList<T>>? = null
): CSStoreProperty<MutableList<T>> = CSJsonMutableListValueStoreProperty(
    this, key, T::class, default, onChange
).registerParent(parent)