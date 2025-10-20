package renetik.android.store.type

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import renetik.android.core.kotlin.collections.reload

class CSPreferencesJsonStore(
    context: Context,
    val key: String = "store",
    id: String = "default", isPretty: Boolean = false) : CSJsonStoreBase(isPretty) {

    val preferences: SharedPreferences = context.getSharedPreferences(id, MODE_PRIVATE)

    override fun loadJson() = preferences.getString(key, "{}")

    override fun saveJson(json: String) = preferences.edit().putString(key, json).apply()

    init {
        load()
    }
}