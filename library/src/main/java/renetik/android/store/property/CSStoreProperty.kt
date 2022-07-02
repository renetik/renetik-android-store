package renetik.android.store.property

import renetik.android.event.property.CSEventProperty
import renetik.android.store.CSStore

interface CSStoreProperty<T> : CSEventProperty<T> {
    val store: CSStore
    val key: String
    fun set(store: CSStore, value: T)
    fun saveTo(store: CSStore) = set(store, value)
    fun save() = set(store, value)
}