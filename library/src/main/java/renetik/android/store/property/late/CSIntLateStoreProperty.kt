package renetik.android.store.property.late

import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty

class CSIntLateStoreProperty(
    store: CSStore, key: String, onChange: ((value: Int) -> Unit)? = null)
    : CSLateStoreProperty<Int>(store, key, onChange), CSStoreProperty<Int> {
    override fun get() = store.getInt(key)
    override fun set(store: CSStore, value: Int) = store.set(key, value)
}

