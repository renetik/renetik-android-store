package renetik.android.store

import android.annotation.SuppressLint
import android.content.Context
import renetik.android.core.extensions.content.isDebug
import renetik.android.core.lang.catchAllWarnReturnNull
import renetik.android.event.CSEvent.Companion.event
import renetik.android.event.owner.CSContext
import renetik.android.json.*
import renetik.android.json.obj.CSJsonObject
import kotlin.reflect.KClass

class CSPreferencesStore(id: String) : CSContext(), CSStore {

	private val preferences = getSharedPreferences(id, Context.MODE_PRIVATE)
	override val eventChanged = event<CSStore>()
	override val data: Map<String, Any?> get() = preferences.all

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
		set(key, value?.toJson(formatted = isDebug))

	override fun set(key: String, value: List<*>?) =
		set(key, value?.toJson(formatted = isDebug))

	override fun set(key: String, value: Map<String, *>?) =
		set(key, value?.toJson(formatted = isDebug))

	@Suppress("unchecked_cast")
	override fun <T : CSJsonObject> getJsonObjectList(key: String, type: KClass<T>) =
		(get(key)?.parseJsonList() as? List<MutableMap<String, Any?>>)
			?.let(type::createJsonObjectList)

	override fun <T : CSJsonObject> set(key: String, value: T?) =
		set(key, value?.toJson(formatted = isDebug))

	override fun <T : CSJsonObject> getJsonObject(key: String,
	                                              type: KClass<T>) =
		get(key)?.parseJsonMap()?.let(type::createJsonObject)

	override fun load(data: Map<String, Any?>) = with(preferences.edit()) {
		loadAll(data)
		eventChanged.fire(this@CSPreferencesStore)
		apply()
	}

	override fun reload(data: Map<String, Any?>) = with(preferences.edit()) {
		clear()
		loadAll(data)
		eventChanged.fire(this@CSPreferencesStore)
		apply()
	}
}

