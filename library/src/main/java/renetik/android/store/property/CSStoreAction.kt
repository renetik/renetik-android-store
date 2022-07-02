package renetik.android.store.property

import renetik.android.core.CSApplication.Companion.app
import renetik.android.event.property.CSActionInterface
import renetik.android.store.CSStore.Companion.store
import renetik.android.store.property.value.CSBooleanValueStoreProperty

object CSStoreAction {
    var actionsDisabled = false
    fun action(id: String): CSActionInterface =
        object : CSBooleanValueStoreProperty(app.store, id, false) {
            override var _value = if (!actionsDisabled) load() else false
        }
}