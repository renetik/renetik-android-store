package renetik.android.store.context

import renetik.android.core.lang.ArgFun
import renetik.android.core.lang.CSHasId
import renetik.android.event.common.CSHasDestruct
import renetik.android.event.common.CSHasRegistrationsHasDestruct
import renetik.android.event.registration.CSHasChange
import renetik.android.store.property.CSStoreProperty

interface CSStoreContext
    : CSHasRegistrationsHasDestruct, CSHasId, CSHasChange<Unit> {
    companion object

    val key: String?
    override val id: String

    fun appContext(parent: CSHasDestruct = this, key: String? = this.key): CSStoreContext

    fun memoryContext(parent: CSHasDestruct = this, key: String? = this.key): CSStoreContext

    fun context(parent: CSHasDestruct, key: String? = null): CSStoreContext

    fun clear()

    fun property(
        key: String, default: String, onChange: ArgFun<String>? = null,
    ): CSStoreProperty<String>

    fun property(
        key: String, default: Boolean, onChange: ArgFun<Boolean>? = null,
    ): CSStoreProperty<Boolean>

    fun property(
        key: String, default: Float, onChange: ArgFun<Float>? = null,
    ): CSStoreProperty<Float>

    fun property(
        key: String, default: Int, onChange: ArgFun<Int>? = null
    ): CSStoreProperty<Int>

    fun property(
        key: String, default: () -> Int, onChange: ArgFun<Int>? = null
    ): CSStoreProperty<Int>

    fun nullIntProperty(
        key: String, default: Int? = null, onChange: ((value: Int?) -> Unit)? = null
    ): CSStoreProperty<Int?>

    fun nullFloatProperty(
        key: String, default: Float? = null, onChange: ((value: Float?) -> Unit)? = null
    ): CSStoreProperty<Float?>

    fun nullStringProperty(
        key: String, default: String? = null, onChange: ((value: String?) -> Unit)? = null
    ): CSStoreProperty<String?>

    fun <T> property(
        key: String, values: () -> List<T>, default: () -> T, onChange: ArgFun<T>? = null
    ): CSStoreProperty<T>

    fun <T> nullListItemProperty(
        key: String, values: List<T>, default: T? = null,
        onChange: ((value: T?) -> Unit)? = null
    ): CSStoreProperty<T?>

    fun property(
        key: String, default: List<Int>, onChange: ArgFun<List<Int>>? = null
    ): CSStoreProperty<List<Int>>
}