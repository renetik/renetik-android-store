package renetik.android.store.type

import android.os.Bundle

class CSBundleJsonStore(
    private val bundle: Bundle = Bundle(),
    val key: String = "store",
    isPretty: Boolean = false
) : CSJsonStoreBase(isPretty) {

    override fun loadJsonString(): String = bundle.getString(key, "{}")

    override fun saveJsonString(json: String) = bundle.putString(key, json)
}