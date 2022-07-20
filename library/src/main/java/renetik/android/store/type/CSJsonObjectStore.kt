package renetik.android.store.type

import renetik.android.event.CSEvent.Companion.event
import renetik.android.json.obj.CSJsonObject
import renetik.android.store.CSStore
import java.io.Closeable

open class CSJsonObjectStore : CSJsonObject(), CSStore, Closeable {
    override val eventLoaded = event<CSStore>()
    override val eventChanged = event<CSStore>()
    override fun onLoaded() = eventLoaded.fire(this)
    open fun onChanged() = eventChanged.fire(this)
    override fun onChange() {
        if (!isBulkSave) onChanged()
        else isBulkSaveDirty = true
    }

    protected var isBulkSave = false
    private var isBulkSaveDirty = false

    override fun bulkSave() = apply {
        isBulkSave = true
        return this
    }

    override fun close() {
        if (isBulkSaveDirty) onChanged()
        isBulkSaveDirty = false
        isBulkSave = false
    }
}