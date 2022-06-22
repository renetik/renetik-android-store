package renetik.android.store.json

import android.annotation.SuppressLint
import android.content.Context
import renetik.android.core.CSApplication.Companion.app

class CSPreferencesJsonStore(id: String, isJsonPretty: Boolean = false)
    : CSJsonStore(isJsonPretty) {
    private val preferences = app.getSharedPreferences(id, Context.MODE_PRIVATE)
    override fun loadJsonString() = preferences.getString("json", "{}")

    @SuppressLint("CommitPrefEdits")
    override fun saveJsonString(json: String) {
        preferences.edit().putString("json", json).apply()
    }
}