package renetik.android.store

import renetik.android.store.extensions.lateStringProperty
import renetik.android.store.extensions.nullStringProperty
import renetik.android.store.extensions.property
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.type.CSJsonObjectStore

class TestJsonType() : CSJsonObjectStore() {
    val string: CSStoreProperty<String> = property("stringId", "string")
    val nullString: CSStoreProperty<String?> = nullStringProperty("nullStringId")
    val lateString: CSStoreProperty<String> = lateStringProperty("lateStringId")

    constructor(value1: String, value2: String, value3: String) : this() {
        string.value = value1
        nullString.value = value2
        lateString.value = value3
    }

    constructor(value: String) : this(value, value, value)
}