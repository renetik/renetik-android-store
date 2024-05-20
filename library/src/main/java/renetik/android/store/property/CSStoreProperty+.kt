package renetik.android.store.property

import renetik.android.store.CSStore

fun <T> CSStoreProperty<T>.saveTo(store: CSStore) = set(store, value)
fun <T> CSStoreProperty<T>.save() = set(store, value)
//val CSStoreProperty<*>.isSaved get() = store.has(key)