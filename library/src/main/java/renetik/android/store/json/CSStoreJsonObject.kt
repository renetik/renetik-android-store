package renetik.android.store.json

import renetik.android.core.kotlin.primitives.toArray
import renetik.android.event.CSEvent.Companion.event
import renetik.android.json.obj.CSJsonObject
import renetik.android.store.CSStore
import java.io.Closeable

@Suppress("unchecked_cast")
open class CSStoreJsonObject : CSJsonObject(), CSStore, Closeable {

	override val eventChanged = event<CSStore>()
	open fun onChanged() = eventChanged.fire(this)
	final override fun load(store: CSStore) = load(store.data)

	override fun onChange() {
		if (!isBulkSave) onChanged()
		else isBulkSaveDirty = true
	}

	override fun load(data: Map<String, Any?>) {
		super.load(data)
		onChange()
		onLoaded()
	}

	override fun onLoaded() = Unit

	override fun clear() {
		super.clear()
		onChange()
	}

	override fun clear(key: String) {
		if (data.remove(key) == null) return
		onChange()
	}

//	override fun set(key: String, string: String?) {
//		if (string != null && data[key] == string) return
//		data[key] = string
//		onChange()
//	}

//	override fun set(key: String, boolean: Boolean?) {
//		val value = boolean.toJsonType()
//		if (boolean != null && data[key] == value) return
//		data[key] =value
//		onChange()
//	}

	override fun set(key: String, value: Map<String, *>?) {
		if (value != null && data[key] == value) return
		data[key] = value?.toMap()
		onChange()
	}

	override fun set(key: String, value: Array<*>?) {
		if (value != null && data[key] == value) return
		data[key] = value?.toArray()
		onChange()
	}

	override fun set(key: String, value: List<*>?) {
		if (value != null && data[key] == value) return
		data[key] = value?.toList()
		onChange()
	}

	override fun <T : CSJsonObject> set(key: String, value: T?) {
		if (value != null && data[key] == value) return
		data[key] = value
		onChange()
	}

	override fun toJsonMap(): Map<String, *> = data
	override fun iterator() = super<CSStore>.iterator()

	protected var isBulkSave = false
	private var isBulkSaveDirty = false

	override fun bulkSave() = apply {
		isBulkSave = true
		return this
	}


	override fun close() {
		if (isBulkSaveDirty) onChanged()
		isBulkSaveDirty = false
		isBulkSave = false
	}
}