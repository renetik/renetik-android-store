package renetik.android.store.type

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import renetik.android.core.lang.catchAllWarnReturnNull
import renetik.android.event.CSEvent.Companion.event
import renetik.android.json.*
import renetik.android.json.CSJson.isJsonPretty
import renetik.android.json.obj.CSJsonObject
import renetik.android.store.CSStore
import renetik.android.store.extensions.loadAll
import kotlin.reflect.KClass

class CSPreferencesStore(val context: Context, id: String = "default") : CSStore {
    val preferences: SharedPreferences = context.getSharedPreferences(id, MODE_PRIVATE)
    override val eventLoaded = event<CSStore>()
    override val eventChanged = event<CSStore>()
    override val data: Map<String, Any?> get() = preferences.all
    override val jsonMap: Map<String, *> by lazy { data }

    @SuppressLint("CommitPrefEdits")
    override fun clear() = preferences.edit().clear().apply()

    override fun clear(key: String) {
        val editor = preferences.edit()
        editor.remove(key)
        editor.apply()
    }

    override fun has(key: String) = preferences.contains(key)

    override fun get(key: String): String? =
        catchAllWarnReturnNull { preferences.getString(key, null) }

    override fun getList(key: String): List<*>? = get(key)?.parseJson<List<*>>()

    override fun getMap(key: String): Map<String, *>? =
        get(key)?.parseJson<Map<String, *>>()

    override fun set(key: String, string: String?) = string?.let {
        val editor = preferences.edit()
        editor.putString(key, it)
        eventChanged.fire(this@CSPreferencesStore)
        editor.apply()
    } ?: clear(key)

    override fun set(key: String, value: Array<*>?) =
        set(key, value?.toJson(formatted = isJsonPretty))

    override fun set(key: String, value: List<*>?) =
        set(key, value?.toJson(formatted = isJsonPretty))

    override fun set(key: String, value: Map<String, *>?) =
        set(key, value?.toJson(formatted = isJsonPretty))

    @Suppress("unchecked_cast")
    override fun <T : CSJsonObject> getJsonObjectList(key: String, type: KClass<T>) =
        (get(key)?.parseJsonList() as? List<MutableMap<String, Any?>>)
            ?.let(type::createJsonObjectList)

    override fun <T : CSJsonObject> setJsonObjectList(key: String, list: List<T>?) =
        set(key, list?.toJson(formatted = isJsonPretty))

    override fun <T : CSJsonObject> getJsonObject(key: String, type: KClass<T>) =
        get(key)?.parseJsonMap()?.let(type::createJsonObject)

    override fun <T : CSJsonObject> set(key: String, value: T?) =
        set(key, value?.toJson(formatted = isJsonPretty))

    override fun load(data: Map<String, Any?>) = with(preferences.edit()) { load(data) }

    override fun reload(data: Map<String, Any?>) = with(preferences.edit()) {
        clear()
        load(data)
    }

    private fun SharedPreferences.Editor.load(data: Map<String, Any?>) {
        loadAll(data)
        eventLoaded.fire(this@CSPreferencesStore)
        eventChanged.fire(this@CSPreferencesStore)
        apply()
    }
}

