package renetik.android.store

import renetik.android.store.extensions.nullStringProperty
import renetik.android.store.extensions.listProperty
import renetik.android.store.type.CSJsonObjectStore

class ComplexJsonObjectStore() : CSJsonObjectStore() {
    var title: String? by nullStringProperty("title", "")
    var list: List<ComplexJsonObjectStore> by listProperty("list")

    constructor(title: String, list: List<ComplexJsonObjectStore>) : this() {
        this.title = title
        this.list = list
    }
}