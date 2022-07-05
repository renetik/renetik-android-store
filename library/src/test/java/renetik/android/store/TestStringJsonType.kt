package renetik.android.store

import renetik.android.store.extensions.lateStringProperty
import renetik.android.store.extensions.nullStringProperty
import renetik.android.store.extensions.property
import renetik.android.store.type.CSJsonObjectStore

class TestStringJsonType() : CSJsonObjectStore() {
    var string by property("stringId", "string")
    var nullString by nullStringProperty("nullStringId")
    var lateString by lateStringProperty("lateStringId")

    constructor(value1: String, value2: String, value3: String) : this() {
        string = value1
        nullString = value2
        lateString = value3
    }

    constructor(value: String) : this(value, value, value)
}