package renetik.android.store.property

import renetik.android.core.lang.CSHasId
import renetik.android.event.property.CSEventProperty
import renetik.android.store.json.CSStoreJsonObject
import renetik.android.store.property.late.CSIntLateStoreEventProperty
import renetik.android.store.property.late.CSJsonListLateStoreEventProperty
import renetik.android.store.property.late.CSStringLateStoreEventProperty
import renetik.android.store.property.late.CSValuesItemLateStoreEventProperty
import renetik.android.store.property.nullable.CSListItemNullableStoreEventProperty
import renetik.android.store.property.value.CSListItemValueStoreEventProperty
import renetik.android.store.property.value.CSListValueStoreEventProperty
import kotlin.reflect.KClass

interface CSHasProperties {
    fun property(
        key: String, value: String,
        onChange: ((value: String) -> Unit)? = null)
            : CSEventProperty<String>

    fun property(
        key: String, value: Boolean,
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
        key: String, value: Int,
        onChange: ((value: Int) -> Unit)? = null)
            : CSEventProperty<Int>

    fun propertyNullInt(
        key: String, default: Int? = null,
        onChange: ((value: Int?) -> Unit)? = null)
            : CSEventProperty<Int?>

    fun property(
        key: String, value: Double,
        onChange: ((value: Double) -> Unit)? = null)
            : CSEventProperty<Double>

    fun property(
        key: String, value: Float,
        onChange: ((value: Float) -> Unit)? = null)
            : CSEventProperty<Float>

    fun property(
        key: String, value: Long,
        onChange: ((value: Long) -> Unit)? = null)
            : CSEventProperty<Long>

    fun <T> property(
        key: String, getValues: () -> List<T>,
        getDefault: () -> T, onChange: ((value: T) -> Unit)? = null)
            : CSListItemValueStoreEventProperty<T>

    fun <T> propertyNullListItem(
        key: String, values: Iterable<T>, default: T?,
        onChange: ((value: T?) -> Unit)? = null)
            : CSListItemNullableStoreEventProperty<T>

    fun <T> lateItemProperty(
        key: String, values: Iterable<T>,
        onChange: ((value: T) -> Unit)? = null)
            : CSValuesItemLateStoreEventProperty<T>

    fun <T : CSHasId> property(
        key: String, values: Iterable<T>, value: List<T>,
        onChange: ((value: List<T>) -> Unit)? = null)
            : CSListValueStoreEventProperty<T>

    fun <T : CSStoreJsonObject> lateProperty(
        key: String, listType: KClass<T>,
        onApply: ((value: List<T>) -> Unit)? = null)
            : CSJsonListLateStoreEventProperty<T>

    fun lateStringProperty(
        key: String, onChange: ((value: String) -> Unit)? = null)
            : CSStringLateStoreEventProperty

    fun lateIntProperty(
        key: String, onChange: ((value: Int) -> Unit)? = null)
            : CSIntLateStoreEventProperty
}