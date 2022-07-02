package renetik.android.store.extensions

import renetik.android.store.CSStore
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.value.*

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