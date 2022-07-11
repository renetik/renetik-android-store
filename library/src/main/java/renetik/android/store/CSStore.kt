package renetik.android.store

import android.content.Context
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.event.CSEvent
import renetik.android.json.obj.CSJsonObjectInterface
import renetik.android.store.type.CSFileJsonStore
import java.io.Closeable

interface CSStore : Iterable<Map.Entry<String, Any?>>, CSJsonObjectInterface {

    companion object {
        var store: CSStore? = null
        val Context.store: CSStore
            get() {
                if (CSStore.store == null)
                    CSStore.store = contextStoreFactory(this.applicationContext)
                return CSStore.store!!
            }
        var contextStoreFactory: (Context) -> CSStore = {
            CSFileJsonStore(it, "store", isJsonPretty = true)
        }
    }

    val eventLoaded: CSEvent<CSStore>
    val eventChanged: CSEvent<CSStore>
    val data: Map<String, Any?>
    fun bulkSave(): Closeable = Closeable { logWarn("Bulk save not implemented") }
    fun load(data: Map<String, Any?>)
    fun reload(data: Map<String, Any?>) = bulkSave().use {
        clear()
        load(data)
    }
}