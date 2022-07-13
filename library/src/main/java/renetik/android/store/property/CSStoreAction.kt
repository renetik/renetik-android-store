package renetik.android.store.property

import renetik.android.event.property.CSActionInterface
import renetik.android.store.CSStore
import renetik.android.store.property.value.CSBooleanValueStoreProperty

object CSStoreAction {
    var actionsDisabled = false
    fun action(id: String): CSActionInterface =
        object : CSBooleanValueStoreProperty(CSStore.store, id, false) {
            override fun get(store: CSStore) =
                if (!actionsDisabled) store.getBoolean(key) else false
        }
}