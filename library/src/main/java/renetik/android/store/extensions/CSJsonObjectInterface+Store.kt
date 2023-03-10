package renetik.android.store.extensions

import renetik.android.event.CSEvent
import renetik.android.event.CSEvent.Companion.event
import renetik.android.json.obj.CSJsonObject
import renetik.android.json.obj.CSJsonObjectInterface
import renetik.android.json.obj.getJsonObject
import renetik.android.store.CSStore
import kotlin.reflect.KClass

fun CSJsonObjectInterface.createStore(key: String): CSStore {
    val jsonObject = getJsonObject(key) ?: CSJsonObject()
    fun save() = setJsonObject(key, jsonObject)
    return object : CSStore {
        override val eventLoaded: CSEvent<CSStore> = event<CSStore>()
        override val eventChanged: CSEvent<CSStore> = event<CSStore>()
        override val data: Map<String, Any?> get() = jsonObject.data
        override val jsonMap: Map<String, *> get() = jsonObject.jsonMap
        override fun load(data: Map<String, Any?>) {
            jsonObject.load(data)
            eventLoaded.fire(this)
        }

        override fun clear(key: String) {
            jsonObject.clear(key)
            save()
        }

        override fun clear() {
            jsonObject.clear()
            save()
        }

        override fun set(key: String, string: String?) {
            jsonObject.set(key, string)
            save()
        }

        override fun set(key: String, value: List<*>?) {
            jsonObject.set(key, value)
            save()
        }

        override fun set(key: String, value: Array<*>?) {
            jsonObject.set(key, value)
            save()
        }

        override fun set(key: String, value: Map<String, *>?) {
            jsonObject.set(key, value)
            save()
        }

        override fun <T : CSJsonObject> getJsonObject(
            key: String, type: KClass<T>
        ): T? = jsonObject.getJsonObject(key, type)

        override fun <T : CSJsonObject> setJsonObject(
            key: String, value: T?
        ) {
            jsonObject.setJsonObject(key, value)
            save()
        }

        override fun <T : CSJsonObject> getJsonObjectList(
            key: String, type: KClass<T>
        ): List<T>? = jsonObject.getJsonObjectList(key, type)

        override fun <T : CSJsonObject> setJsonObjectList(
            key: String, list: List<T>?
        ) {
            jsonObject.setJsonObjectList(key, list)
            save()
        }
    }
}