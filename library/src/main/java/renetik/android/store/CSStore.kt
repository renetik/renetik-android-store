package renetik.android.store

import android.content.Context
import renetik.android.core.lang.CSHasId
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.event.CSEvent
import renetik.android.json.CSJsonObjectInterface
import renetik.android.store.json.CSFileJsonStore
import renetik.android.store.json.CSStoreJsonObject
import renetik.android.store.property.CSPropertyStore
import renetik.android.store.property.late.*
import renetik.android.store.property.nullable.CSBooleanNullableStoreEventProperty
import renetik.android.store.property.nullable.CSIntNullableStoreEventProperty
import renetik.android.store.property.nullable.CSListItemNullableStoreEventProperty
import renetik.android.store.property.value.*
import java.io.Closeable
import kotlin.reflect.KClass

interface CSStore : CSPropertyStore,
	Iterable<Map.Entry<String, Any?>>, CSJsonObjectInterface {

	companion object {
		private var store: CSStore? = null
		val Context.store: CSStore
			get() {
				if (CSStore.store == null)
					CSStore.store = storeFactory(applicationContext)
				return CSStore.store!!
			}
		var storeFactory: (Context) -> CSStore = {
			CSFileJsonStore(it, "store", isJsonPretty = true)
		}
	}

	val eventChanged: CSEvent<CSStore>
	val data: Map<String, Any?>

	override fun toJsonMap(): Map<String, *> = data

	override fun iterator(): Iterator<Map.Entry<String, Any?>> = data.iterator()

	fun bulkSave(): Closeable = Closeable { logWarn("Bulk save not implemented") }

	fun load(store: CSStore)
	fun clear()
	fun clear(key: String)

	fun reload(store: CSStore) = bulkSave().use {
		clear()
		load(store)
	}

	override fun property(key: String, value: String, onChange: ((value: String) -> Unit)?) =
		CSStringValueStoreEventProperty(this, key, value, listenStoreChanged = false, onChange)

	override fun property(key: String, value: Boolean, onChange: ((value: Boolean) -> Unit)?) =
		CSBooleanValueStoreEventProperty(this, key, value, onChange)

	override fun property(key: String, value: Int, onChange: ((value: Int) -> Unit)?) =
		CSIntValueStoreEventProperty(this, key, value, onChange = onChange)

	override fun property(key: String, value: Double, onChange: ((value: Double) -> Unit)?) =
		CSDoubleValueStoreEventProperty(this, key, value, onChange)

	override fun property(key: String, value: Float, onChange: ((value: Float) -> Unit)?) =
		CSFloatValueStoreEventProperty(this, key, value, onChange)

	override fun property(key: String, value: Long, onChange: ((value: Long) -> Unit)?) =
		CSLongValueStoreEventProperty(this, key, value, onChange)

	override fun <T> property(
		key: String, values: List<T>, value: T, onChange: ((value: T) -> Unit)?):
			CSListItemValueStoreEventProperty<T> =
		CSListItemValueStoreEventProperty(this, key, values, value, false, onChange)

	override fun <T> property(
		key: String, values: List<T>, getDefault: () -> T, onChange: ((value: T) -> Unit)?) =
		CSListItemValueStoreEventProperty(this, key, { values }, getDefault, false, onChange)

	override fun <T> property(
		key: String, getValues: () -> List<T>,
		defaultIndex: Int, onChange: ((value: T) -> Unit)?
	) = CSListItemValueStoreEventProperty(this, key, getValues,
		{ getValues()[defaultIndex] }, listenStoreChanged = false, onChange)

	override fun <T : CSHasId> property(
		key: String, values: Iterable<T>, value: List<T>, onChange: ((value: List<T>) -> Unit)?) =
		CSListValueStoreEventProperty(this, key, values, value, onChange)

	fun <T : CSStoreJsonObject> CSStore.lateProperty(
		key: String, listType: KClass<T>, onApply: ((value: List<T>) -> Unit)? = null
	) = CSJsonListLateStoreEventProperty(this, key, listType, onApply)

	fun lateStringProperty(key: String, onChange: ((value: String) -> Unit)? = null) =
		CSStringLateStoreEventProperty(this, key, onChange)

	fun lateIntProperty(key: String, onChange: ((value: Int) -> Unit)? = null) =
		CSIntLateStoreEventProperty(this, key, onChange)

	override fun lateBoolProperty(key: String, onChange: ((value: Boolean) -> Unit)?) =
		CSBooleanLateStoreEventProperty(this, key, onChange)

	fun <T> lateItemProperty(key: String, values: List<T>,
	                         onChange: ((value: T) -> Unit)? = null) =
		CSValuesItemLateStoreEventProperty(this, key, values, onChange)

	override fun nullBoolProperty(key: String, default: Boolean?,
	                              onChange: ((value: Boolean?) -> Unit)?) =
		CSBooleanNullableStoreEventProperty(this, key, default, onChange)

	override fun propertyNullInt(key: String, default: Int?, onChange: ((value: Int?) -> Unit)?) =
		CSIntNullableStoreEventProperty(this, key, default, onChange)

	override fun <T> propertyNullListItem(
		key: String, values: List<T>, default: T?, onChange: ((value: T?) -> Unit)?) =
		CSListItemNullableStoreEventProperty(this, key, values, default, onChange)
}

