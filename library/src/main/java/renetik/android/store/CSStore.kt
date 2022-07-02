package renetik.android.store

import android.content.Context
import renetik.android.core.lang.CSHasId
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.event.CSEvent
import renetik.android.json.obj.CSJsonObjectInterface
import renetik.android.store.json.CSFileJsonStore
import renetik.android.store.json.CSStoreJsonObject
import renetik.android.store.property.CSStoredProperties
import renetik.android.store.property.late.*
import renetik.android.store.property.nullable.CSBooleanNullableStoreProperty
import renetik.android.store.property.nullable.CSIntNullableStoreProperty
import renetik.android.store.property.nullable.CSListItemNullableStoreProperty
import renetik.android.store.property.value.*
import java.io.Closeable
import kotlin.reflect.KClass

interface CSStore : CSStoredProperties,
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

    override fun property(key: String, default: String, onChange: ((value: String) -> Unit)?) =
        CSStringValueStoreProperty(this, key, default, listenStoreChanged = false, onChange)

    override fun property(key: String, default: Boolean, onChange: ((value: Boolean) -> Unit)?) =
        CSBooleanValueStoreProperty(this, key, default, onChange)

    override fun property(key: String, default: Int, onChange: ((value: Int) -> Unit)?) =
        CSIntValueStoreProperty(this, key, default, onChange = onChange)

    override fun property(key: String, default: Double, onChange: ((value: Double) -> Unit)?) =
        CSDoubleValueStoreProperty(this, key, default, onChange)

    override fun property(key: String, default: Float, onChange: ((value: Float) -> Unit)?) =
        CSFloatValueStoreProperty(this, key, default, onChange)

    override fun property(key: String, default: Long, onChange: ((value: Long) -> Unit)?) =
        CSLongValueStoreProperty(this, key, default, onChange)

    override fun <T> property(
        key: String, getValues: () -> List<T>,
        getDefault: () -> T, onChange: ((value: T) -> Unit)?) =
        CSListItemValueStoreProperty(this, key, getValues, getDefault, onChange = onChange)

    override fun <T : CSHasId> property(
        key: String, values: Iterable<T>, default: List<T>, onChange: ((value: List<T>) -> Unit)?) =
        CSListValueStoreProperty(this, key, values, default, onChange)

    override fun <T : CSStoreJsonObject> lateProperty(
        key: String, listType: KClass<T>, onApply: ((value: List<T>) -> Unit)?
    ) = CSJsonListLateStoreProperty(this, key, listType, onApply)

    override fun lateStringProperty(key: String, onChange: ((value: String) -> Unit)?) =
        CSStringLateStoreProperty(this, key, onChange)

    override fun lateIntProperty(key: String, onChange: ((value: Int) -> Unit)?) =
        CSIntLateStoreProperty(this, key, onChange)

    override fun lateBoolProperty(key: String, onChange: ((value: Boolean) -> Unit)?) =
        CSBooleanLateStoreProperty(this, key, onChange)

    override fun <T> lateItemProperty(key: String, values: Iterable<T>,
                                      onChange: ((value: T) -> Unit)?) =
        CSValuesItemLateStoreProperty(this, key, values, onChange)

    override fun nullBoolProperty(key: String, default: Boolean?,
                                  onChange: ((value: Boolean?) -> Unit)?) =
        CSBooleanNullableStoreProperty(this, key, default, onChange)

    override fun propertyNullInt(key: String, default: Int?, onChange: ((value: Int?) -> Unit)?) =
        CSIntNullableStoreProperty(this, key, default, onChange)

    override fun <T> propertyNullListItem(
        key: String, values: Iterable<T>, default: T?, onChange: ((value: T?) -> Unit)?) =
        CSListItemNullableStoreProperty(this, key, values, default, onChange)
}

