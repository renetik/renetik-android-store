package renetik.android.store.type

import android.os.Bundle
import renetik.android.core.kotlin.collections.reload

class CSBundleJsonStore(
    private val bundle: Bundle = Bundle(),
    val key: String = "store",
    isPretty: Boolean = false
) : CSJsonStoreBase(isPretty) {

    override fun loadJson(): String = bundle.getString(key, "{}")

    override fun saveJson(json: String) = bundle.putString(key, json)

    init {
        load()
    }
}