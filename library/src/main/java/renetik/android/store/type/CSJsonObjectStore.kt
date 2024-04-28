package renetik.android.store.type

import renetik.android.core.kotlin.unexpected
import renetik.android.core.lang.Func
import renetik.android.event.CSEvent.Companion.event
import renetik.android.json.obj.CSJsonObject
import renetik.android.store.CSStore
import java.io.Closeable

open class CSJsonObjectStore : CSJsonObject(), CSStore {
    override val eventLoaded = event<CSStore>()
    override val eventChanged = event<CSStore>()
    override fun onLoaded() = eventLoaded.fire(this)
    open fun onChanged() = eventChanged.fire(this)
    override fun onChange() {
        if (!isBulkSave) onChanged()
        else isBulkSaveDirty = true
    }

    private var isBulkSaveDirty = false
    var isBulkSave = false
        private set

    @Deprecated("Use pause")
    override fun bulkSave(): Closeable {
        if (isBulkSave) unexpected()
        isBulkSave = true
        return Closeable {
            isBulkSave = false
            if (isBulkSaveDirty) onChanged()
            isBulkSaveDirty = false
        }
    }

    override fun operation(func: Func) {
        if (isBulkSave) {
            func()
            return
        }
        isBulkSave = true
        func()
        isBulkSave = false
        if (isBulkSaveDirty) onChanged()
        isBulkSaveDirty = false
    }
}