package renetik.android.store.extensions

import renetik.android.json.parseJsonMap
import renetik.android.store.CSStore

fun <T : CSStore> T.load(store: CSStore) = apply { load(store.data) }
fun <T : CSStore> T.reload(store: CSStore) = apply { reload(store.data) }
fun <T : CSStore> T.reload(json: String) = apply { reload(json.parseJsonMap()!!) }
val <T : CSStore> T.isEmpty get() = data.isEmpty()

inline fun <T : CSStore, R> T.operation(func: (T) -> R) {
    if (!startOperation()) {
        func(this); return
    }
    func(this)
    stopOperation()
}