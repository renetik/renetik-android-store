package renetik.android.store.type

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class CSPreferencesJsonStore(
    context: Context,
    val key: String = "store",
    id: String = "default", isPretty: Boolean = false) : CSJsonStoreBase(isPretty) {

    val preferences: SharedPreferences = context.getSharedPreferences(id, MODE_PRIVATE)

    override fun loadJsonString() = preferences.getString(key, "{}")

    override fun saveJsonString(json: String) = preferences.edit().putString(key, json).apply()
}