package renetik.android.store

import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.lang.lazyVar
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogMessage.Companion.message
import renetik.android.event.CSEvent
import renetik.android.json.obj.CSJsonObjectInterface
import renetik.android.store.type.CSFileJsonStore
import java.io.Closeable

interface CSStore : Iterable<Map.Entry<String, Any?>>, CSJsonObjectInterface {

    companion object {
        //TODO: Remove completely and make it just local to app
        var store: CSStore by lazyVar {
            CSFileJsonStore(app, "store", isJsonPretty = isDebug)
        }
    }

    val eventLoaded: CSEvent<CSStore>
    val eventChanged: CSEvent<CSStore>
    val data: Map<String, Any?>
    fun bulkSave(): Closeable =
        Closeable { logWarn { message("Bulk save not implemented") } }

    fun load(data: Map<String, Any?>)
    fun reload(data: Map<String, Any?>) = bulkSave().use {
        clear()
        load(data)
    }
}