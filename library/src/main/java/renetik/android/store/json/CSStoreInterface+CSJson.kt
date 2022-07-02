package renetik.android.store.json

import renetik.android.store.CSStore
import renetik.android.store.property.nullable.CSJsonTypeNullableStoreProperty
import renetik.android.store.property.value.CSJsonListValueStoreProperty
import renetik.android.store.property.value.CSJsonTypeValueStoreProperty
import kotlin.reflect.KClass

fun <T : CSStoreJsonObject> CSStore.property(
    key: String, listType: KClass<T>, default: List<T>,
    onApply: ((value: List<T>) -> Unit)? = null
) = CSJsonListValueStoreProperty(this,
    key, listType, default, listenStoreChanged = false, onApply)

fun <T : CSStoreJsonObject> CSStore.property(
    key: String, type: KClass<T>, default: T? = null,
    onApply: ((value: T?) -> Unit)? = null
) = CSJsonTypeNullableStoreProperty(this, key, type, default, onApply)

fun <T : CSStoreJsonObject> CSStore.property(
    key: String, type: KClass<T>, onApply: ((value: T) -> Unit)? = null
) = CSJsonTypeValueStoreProperty(this, key, type, listenStoreChanged = false, onApply)