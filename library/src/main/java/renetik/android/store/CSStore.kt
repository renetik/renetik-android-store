package renetik.android.store

import android.content.Context
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.event.CSEvent
import renetik.android.json.obj.CSJsonObjectInterface
import renetik.android.store.type.CSFileJsonStore
import java.io.Closeable

interface CSStore : Iterable<Map.Entry<String, Any?>>, CSJsonObjectInterface {

    companion object {
        private var store: CSStore? = null
        val Context.store: CSStore
            get() {
                if (CSStore.store == null)
                    CSStore.store = storeFactory(applicationContext)
                return CSStore.store!!
            }
        var storeFactory: (Context) -> CSStore = {
            CSFileJsonStore(it, "store", isJsonPretty = true)
        }
    }

    val eventChanged: CSEvent<CSStore>
    val data: Map<String, Any?>

    override fun toJsonMap(): Map<String, *> = data
    override fun iterator(): Iterator<Map.Entry<String, Any?>> = data.iterator()

    fun bulkSave(): Closeable = Closeable { logWarn("Bulk save not implemented") }

    fun load(data: Map<String, Any?>)
    fun reload(data: Map<String, Any?>) = bulkSave().use {
        clear()
        load(data)
    }

    fun clear(key: String)
    fun clear()
}

fun CSStore.load(store: CSStore) = load(store.data)

fun CSStore.reload(store: CSStore) = reload(store.data)

