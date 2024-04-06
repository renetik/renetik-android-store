package renetik.android.store

import renetik.android.core.lang.CSEnvironment.app
import renetik.android.core.lang.Func
import renetik.android.core.lang.lazy.CSLazyVar.Companion.lazyVar
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.event.CSEvent
import renetik.android.json.obj.CSJsonObjectInterface
import renetik.android.store.type.CSFileJsonStore
import java.io.Closeable

interface CSStore : Iterable<Map.Entry<String, Any?>>, CSJsonObjectInterface {

    companion object {
        //TODO: Remove completely and make it just local to app
        var store: CSStore by lazyVar { CSFileJsonStore(app, "store") }
    }

    val eventLoaded: CSEvent<CSStore>
    val eventChanged: CSEvent<CSStore>
    val data: Map<String, Any?>

    @Deprecated("Use pause")
    fun bulkSave(): Closeable =
        Closeable { logWarn { "Bulk save not implemented" } }

    fun operation(func: Func) = logWarn { "Pause not implemented" }

    fun load(data: Map<String, Any?>)
    fun reload(data: Map<String, Any?>) = bulkSave().use {
        clear()
        load(data)
    }
}

//fun CSStore.pause(func: Func) {
//    bulkSave().use { func() }
//}