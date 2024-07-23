package renetik.android.store.extensions

import renetik.android.core.lang.ArgFunc
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.parent
import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.listenStore
import renetik.android.store.property.value.CSJsonListValueStoreProperty
import renetik.android.store.property.value.CSJsonMutableListValueStoreProperty
import renetik.android.store.type.CSJsonObjectStore

inline fun <reified T : CSJsonObjectStore> CSStore.listProperty(
    key: String, noinline onChange: ArgFunc<List<T>>? = null
): CSStoreProperty<List<T>> = CSJsonListValueStoreProperty(
    this, key, T::class, onChange
)

inline fun <reified T : CSJsonObjectStore> CSStore.dataListProperty(
    key: String, noinline onChange: ArgFunc<List<T>>? = null
): CSStoreProperty<List<T>> = listProperty(key, onChange).listenStore()

inline fun <reified T : CSJsonObjectStore> CSStore.listProperty(
    parent: CSHasDestruct, key: String,
    noinline onChange: ArgFunc<List<T>>? = null
) = CSJsonListValueStoreProperty(this, key, T::class, onChange)
    .parent(parent).listenStore()

@JvmName("propertyMutableList")
inline fun <reified T : CSJsonObjectStore> CSStore.mutableListProperty(
    key: String, noinline onChange: ArgFunc<MutableList<T>>? = null
): CSStoreProperty<MutableList<T>> = CSJsonMutableListValueStoreProperty(
    this, key, T::class, onChange
)

inline fun <reified T : CSJsonObjectStore> CSStore.dataMutableListProperty(
    key: String, noinline onChange: ArgFunc<MutableList<T>>? = null
) = mutableListProperty(key, onChange).listenStore()

@JvmName("propertyMutableList")
inline fun <reified T : CSJsonObjectStore> CSStore.mutableListProperty(
    parent: CSHasDestruct, key: String,
    noinline onChange: ArgFunc<MutableList<T>>? = null
): CSStoreProperty<MutableList<T>> = CSJsonMutableListValueStoreProperty(
    this, key, T::class, onChange
).parent(parent).listenStore()