package renetik.android.store.context

import renetik.android.core.lang.CSHasId
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.destruct

interface CSHasStoreContext: CSHasId {
    val store: StoreContext
    override val id get() = store.id

    companion object {
        fun <T> T.clearDestruct() where T : CSHasStoreContext, T : CSHasDestruct {
            store.clear()
            destruct()
        }
    }
}