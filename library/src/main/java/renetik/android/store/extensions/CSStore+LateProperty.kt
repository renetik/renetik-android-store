package renetik.android.store.extensions

import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.late.*
import renetik.android.store.type.CSJsonObjectStore
import kotlin.reflect.KClass

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
