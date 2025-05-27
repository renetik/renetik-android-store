@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.store.property

import renetik.android.event.property.CSProperty
import renetik.android.event.registration.CSHasRegistrations
import renetik.android.event.registration.onChange
import renetik.android.event.registration.onChangeOnce
import renetik.android.event.registration.plus
import renetik.android.store.CSStore

interface CSStoreProperty<T> : CSProperty<T> {
    val store: CSStore
    val key: String
    fun set(store: CSStore, value: T)

    fun get(store: CSStore): T?

    val isSaved get() = store.has(key)
    fun update()
    fun trackModified(track: Boolean = true): CSStoreProperty<T> = this
    fun clear() = store.clear(key)
}

inline fun <T, V> T.listenLoad() where T : CSStoreProperty<V>, T : CSHasRegistrations = apply {
    this + store.eventLoaded.onChange(::update)
}

inline fun <T : CSStoreProperty<*>> T.listenLoad(parent: CSHasRegistrations) = apply {
    parent + store.eventLoaded.onChange(::update)
}

inline fun <T : CSStoreProperty<*>> T.listenLoadOnce() = apply {
    store.eventLoaded.onChangeOnce(::update)
}