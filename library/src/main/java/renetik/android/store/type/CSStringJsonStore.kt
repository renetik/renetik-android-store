package renetik.android.store.type

import renetik.android.core.kotlin.collections.reload

class CSStringJsonStore(jsonString: String = "{}") : CSJsonStoreBase() {
    var jsonString: String = jsonString
        set(value) {
            field = value
            load()
        }

    override fun loadJsonString() = jsonString
    override fun saveJsonString(json: String) {
        jsonString = json
    }

    init {
        load()
    }
}
