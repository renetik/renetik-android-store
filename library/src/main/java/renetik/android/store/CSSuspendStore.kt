package renetik.android.store

import renetik.android.event.CSSuspendEvent
import renetik.android.event.registration.CSSuspendHasChange
import renetik.android.json.obj.CSJsonObjectInterface

interface CSSuspendStore : Iterable<Map.Entry<String, Any?>>,
    CSSuspendHasChange<CSSuspendStore> {

    val jsonData: CSJsonObjectInterface
    val eventLoaded: CSSuspendEvent<CSSuspendStore>
    val eventChanged: CSSuspendEvent<CSSuspendStore>

    suspend fun load(data: Map<String, Any?>)
    suspend fun reload(data: Map<String, Any?>) {
        jsonData.clear()
        load(data)
    }

    override fun onChange(function: suspend (CSSuspendStore) -> Unit) =
        eventChanged.onChange(function)

    override fun iterator() = jsonData.iterator()
}