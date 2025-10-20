package renetik.android.store.type

import renetik.android.core.kotlin.collections.reload

class CSStringJsonStore(jsonString: String = "{}") : CSJsonStoreBase() {
    var jsonString: String = jsonString
        set(value) {
            field = value
            load()
        }

    override fun loadJson() = jsonString
    override fun saveJson(json: String) {
        jsonString = json
    }

    init {
        load()
    }
}
