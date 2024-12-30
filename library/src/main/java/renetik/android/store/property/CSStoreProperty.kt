@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.store.property

import renetik.android.event.property.CSProperty
import renetik.android.store.CSStore

interface CSStoreProperty<T> : CSProperty<T> {
    val store: CSStore
    val key: String
    fun set(store: CSStore, value: T)

    fun get(store: CSStore): T?

    val isSaved get() = store.has(key)
    fun listenStoreLoad()
    fun trackModified(track: Boolean = true): CSStoreProperty<T> = this
    fun clear() = store.clear(key)
}

inline fun <T : CSStoreProperty<*>> T.listenStore() = apply { listenStoreLoad() }