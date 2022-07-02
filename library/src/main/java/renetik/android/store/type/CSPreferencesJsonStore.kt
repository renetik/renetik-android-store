package renetik.android.store.type

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import renetik.android.core.CSApplication.Companion.app

class CSPreferencesJsonStore(id: String, isJsonPretty: Boolean = false)
    : CSJsonStore(isJsonPretty) {
    private val preferences = app.getSharedPreferences(id, MODE_PRIVATE)
    override fun loadJsonString() = preferences.getString("json", "{}")

    @SuppressLint("CommitPrefEdits")
    override fun saveJsonString(json: String) {
        preferences.edit().putString("json", json).apply()
    }
}