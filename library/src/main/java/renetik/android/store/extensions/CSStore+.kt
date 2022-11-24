package renetik.android.store.extensions

import renetik.android.json.parseJsonMap
import renetik.android.store.CSStore

fun <T : CSStore> T.load(store: CSStore) = apply { load(store.data) }
fun <T : CSStore> T.reload(store: CSStore) = apply { reload(store.data) }
fun <T : CSStore> T.reload(json: String) = apply { reload(json.parseJsonMap()!!) }