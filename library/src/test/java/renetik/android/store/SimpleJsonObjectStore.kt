package renetik.android.store

import renetik.android.store.extensions.lateStringProperty
import renetik.android.store.extensions.nullStringProperty
import renetik.android.store.extensions.property
import renetik.android.store.type.CSJsonObjectStore

class SimpleJsonObjectStore(
    string: String? = null,
    nullString: String? = null,
    lateString: String? = null) : CSJsonObjectStore() {

    var string: String by property("stringId", "string")
    var nullString: String? by nullStringProperty("nullStringId")
    var lateString: String by lateStringProperty("lateStringId")

    init {
        string?.let { this.string = it }
        nullString?.let { this.nullString = it }
        lateString?.let { this.lateString = it }
    }
}

