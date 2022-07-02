package renetik.android.store.property

import renetik.android.core.lang.CSHasId
import renetik.android.event.property.CSEventProperty
import renetik.android.store.json.CSStoreJsonObject
import kotlin.reflect.KClass

interface CSStoredProperties {
    fun property(
        key: String, default: String,
        onChange: ((value: String) -> Unit)? = null)
            : CSEventProperty<String>

    fun property(
        key: String, default: Boolean,
        onChange: ((value: Boolean) -> Unit)? = null)
            : CSEventProperty<Boolean>

    fun nullBoolProperty(
        key: String, default: Boolean? = null,
        onChange: ((value: Boolean?) -> Unit)? = null)
            : CSEventProperty<Boolean?>

    fun lateBoolProperty(
        key: String, onChange: ((value: Boolean) -> Unit)? = null)
            : CSEventProperty<Boolean>

    fun property(
        key: String, default: Int,
        onChange: ((value: Int) -> Unit)? = null)
            : CSEventProperty<Int>

    fun propertyNullInt(
        key: String, default: Int? = null,
        onChange: ((value: Int?) -> Unit)? = null)
            : CSEventProperty<Int?>

    fun property(
        key: String, default: Double,
        onChange: ((value: Double) -> Unit)? = null)
            : CSEventProperty<Double>

    fun property(
        key: String, default: Float,
        onChange: ((value: Float) -> Unit)? = null)
            : CSEventProperty<Float>

    fun property(
        key: String, default: Long,
        onChange: ((value: Long) -> Unit)? = null)
            : CSEventProperty<Long>

    fun <T> property(
        key: String, getValues: () -> List<T>,
        getDefault: () -> T, onChange: ((value: T) -> Unit)? = null)
            : CSEventProperty<T>

    fun <T> propertyNullListItem(
        key: String, values: Iterable<T>, default: T?,
        onChange: ((value: T?) -> Unit)? = null)
            : CSEventProperty<T?>

    fun <T> lateItemProperty(
        key: String, values: Iterable<T>,
        onChange: ((value: T) -> Unit)? = null)
            : CSEventProperty<T>

    fun <T : CSHasId> property(
        key: String, values: Iterable<T>, default: List<T>,
        onChange: ((value: List<T>) -> Unit)? = null)
            : CSEventProperty<List<T>>

    fun <T : CSStoreJsonObject> lateProperty(
        key: String, listType: KClass<T>,
        onApply: ((value: List<T>) -> Unit)? = null)
            : CSEventProperty<List<T>>

    fun lateStringProperty(
        key: String, onChange: ((value: String) -> Unit)? = null)
            : CSEventProperty<String>

    fun lateIntProperty(
        key: String, onChange: ((value: Int) -> Unit)? = null)
            : CSEventProperty<Int>
}