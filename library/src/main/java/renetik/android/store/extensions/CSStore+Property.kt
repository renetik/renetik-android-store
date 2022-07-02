package renetik.android.store

import renetik.android.core.lang.CSHasId
import renetik.android.store.type.CSJsonObjectStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.late.*
import renetik.android.store.property.nullable.CSBooleanNullableStoreProperty
import renetik.android.store.property.nullable.CSIntNullableStoreProperty
import renetik.android.store.property.nullable.CSJsonTypeNullableStoreProperty
import renetik.android.store.property.nullable.CSListItemNullableStoreProperty
import renetik.android.store.property.value.*
import kotlin.reflect.KClass

fun CSStore.property(
    key: String, default: String, onChange: ((value: String) -> Unit)? = null)
        : CSStoreProperty<String> =
    CSStringValueStoreProperty(this, key, default, listenStoreChanged = false, onChange)

fun CSStore.property(
    key: String, default: Boolean, onChange: ((value: Boolean) -> Unit)? = null)
        : CSStoreProperty<Boolean> = CSBooleanValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    key: String, default: Int, onChange: ((value: Int) -> Unit)? = null)
        : CSStoreProperty<Int> = CSIntValueStoreProperty(this, key, default, onChange = onChange)

fun CSStore.property(
    key: String, default: Double, onChange: ((value: Double) -> Unit)? = null)
        : CSStoreProperty<Double> = CSDoubleValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    key: String, default: Float, onChange: ((value: Float) -> Unit)? = null)
        : CSStoreProperty<Float> = CSFloatValueStoreProperty(this, key, default, onChange)

fun CSStore.property(
    key: String, default: Long, onChange: ((value: Long) -> Unit)? = null)
        : CSStoreProperty<Long> = CSLongValueStoreProperty(this, key, default, onChange)

fun <T> CSStore.property(
    key: String, getValues: () -> List<T>,
    getDefault: () -> T, onChange: ((value: T) -> Unit)? = null): CSStoreProperty<T> =
    CSListItemValueStoreProperty(this, key, getValues, getDefault, onChange = onChange)

fun <T : CSHasId> CSStore.property(
    key: String, values: List<T>, default: List<T>,
    onChange: ((value: List<T>) -> Unit)? = null): CSStoreProperty<List<T>> =
    CSListValueStoreProperty(this, key, values, default, onChange)

fun <T> CSStore.property(
    key: String, values: List<T>, getDefault: () -> T,
    onChange: ((value: T) -> Unit)? = null) =
    property(key, { values }, getDefault, onChange)

fun <T> CSStore.property(
    key: String, list: List<T>, default: T,
    onChange: ((value: T) -> Unit)? = null): CSStoreProperty<T> =
    property(key, { list }, { default }, onChange)

fun <T> CSStore.property(
    key: String, list: List<T>, defaultIndex: Int,
    onChange: ((value: T) -> Unit)? = null): CSStoreProperty<T> =
    property(key, list, list[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, array: Array<T>, default: T,
    onChange: ((value: T) -> Unit)? = null): CSStoreProperty<T> =
    property(key, array.asList(), default, onChange)

fun <T> CSStore.property(
    key: String, array: Array<T>, defaultIndex: Int,
    onChange: ((value: T) -> Unit)? = null): CSStoreProperty<T> =
    property(key, array.asList(), array[defaultIndex], onChange)

fun <T> CSStore.property(
    key: String, getList: () -> List<T>, defaultIndex: Int,
    onChange: ((value: T) -> Unit)? = null): CSStoreProperty<T> =
    property(key, getList, { getList()[defaultIndex] }, onChange)

fun <T : CSHasId> CSStore.property(
    key: String, array: Array<T>, default: List<T>,
    onChange: ((value: List<T>) -> Unit)? = null): CSStoreProperty<List<T>> =
    property(key, array.asList(), default, onChange)

//json
fun <T : CSJsonObjectStore> CSStore.property(
    key: String, listType: KClass<T>, default: List<T>,
    onApply: ((value: List<T>) -> Unit)? = null
) = CSJsonListValueStoreProperty(this,
    key, listType, default, listenStoreChanged = false, onApply)

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>, default: T? = null,
    onApply: ((value: T?) -> Unit)? = null
) = CSJsonTypeNullableStoreProperty(this, key, type, default, onApply)

fun <T : CSJsonObjectStore> CSStore.property(
    key: String, type: KClass<T>, onApply: ((value: T) -> Unit)? = null
) = CSJsonTypeValueStoreProperty(this, key, type, listenStoreChanged = false, onApply)


//Null
fun CSStore.nullBoolProperty(
    key: String, default: Boolean? = null, onChange: ((value: Boolean?) -> Unit)? = null)
        : CSStoreProperty<Boolean?> = CSBooleanNullableStoreProperty(this, key, default, onChange)

fun CSStore.nullIntProperty(
    key: String, default: Int? = null, onChange: ((value: Int?) -> Unit)? = null)
        : CSStoreProperty<Int?> = CSIntNullableStoreProperty(this, key, default, onChange)

fun <T> CSStore.nullListItemProperty(
    key: String, values: Iterable<T>, default: T? = null,
    onChange: ((value: T?) -> Unit)? = null): CSStoreProperty<T?> =
    CSListItemNullableStoreProperty(this, key, values, default, onChange)

//Late
fun <T : CSJsonObjectStore> CSStore.lateProperty(
    key: String, listType: KClass<T>, onApply: ((value: List<T>) -> Unit)? = null)
        : CSStoreProperty<List<T>> = CSJsonListLateStoreProperty(this, key, listType, onApply)

fun CSStore.lateStringProperty(
    key: String, onChange: ((value: String) -> Unit)? = null)
        : CSStoreProperty<String> = CSStringLateStoreProperty(this, key, onChange)

fun CSStore.lateIntProperty(
    key: String, onChange: ((value: Int) -> Unit)? = null)
        : CSStoreProperty<Int> = CSIntLateStoreProperty(this, key, onChange)

fun CSStore.lateBoolProperty(
    key: String, onChange: ((value: Boolean) -> Unit)? = null)
        : CSStoreProperty<Boolean> = CSBooleanLateStoreProperty(this, key, onChange)

fun <T> CSStore.lateItemProperty(
    key: String, values: Iterable<T>, onChange: ((value: T) -> Unit)? = null)
        : CSStoreProperty<T> = CSValuesItemLateStoreProperty(this, key, values, onChange)
