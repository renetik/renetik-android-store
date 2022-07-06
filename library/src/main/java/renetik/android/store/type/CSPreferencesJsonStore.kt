package renetik.android.store.type

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE

class CSPreferencesJsonStore(context: Context,
                             val key: String = "store",
                             id: String = "default", isPretty: Boolean = false)
    : CSJsonStore(isPretty) {
    val preferences = context.getSharedPreferences(id, MODE_PRIVATE)
    override fun loadJsonString() = preferences.getString(key, "{}")

    @SuppressLint("CommitPrefEdits")
    override fun saveJsonString(json: String) {
        preferences.edit().putString(key, json).apply()
    }
}