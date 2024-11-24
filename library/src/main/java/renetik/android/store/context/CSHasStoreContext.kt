package renetik.android.store.context

import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.destruct

interface CSHasStoreContext {
    val store: StoreContext

    companion object {
        fun <T> T.clearDestruct() where T : CSHasStoreContext, T : CSHasDestruct {
            store.clear()
            destruct()
        }
    }
}