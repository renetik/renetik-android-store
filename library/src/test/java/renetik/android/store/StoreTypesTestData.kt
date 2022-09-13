package renetik.android.store

import renetik.android.store.extensions.property
import renetik.android.store.type.CSJsonObjectStore

class StoreTypesTestData : CSJsonObjectStore() {
    var string: String by property("key1", default = "initial")
    var int: Int by property("key2", default = 5)
    val jsonObject: SimpleJsonObjectStore by property("key3")
}