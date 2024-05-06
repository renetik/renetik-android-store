package renetik.android.store.type

import renetik.android.event.CSEvent.Companion.event
import renetik.android.json.obj.CSJsonObject
import renetik.android.store.CSStore

open class CSJsonObjectStore : CSJsonObject(), CSStore {
    override val eventLoaded = event<CSStore>()
    override val eventChanged = event<CSStore>()
    override fun load(data: Map<String, Any?>) = super.load(data)
    override fun onLoaded() = eventLoaded.fire(this)
    open fun onChanged() = eventChanged.fire(this)

    override fun onChange() {
        if (!isBulkSave) onChanged()
        else isBulkSaveDirty = true
    }

    private var isBulkSaveDirty = false
    var isBulkSave = false
        private set

    override fun pauseOnChange(): Boolean {
        if (!isBulkSave) {
            isBulkSave = true
            isBulkSaveDirty = false
            return true
        }
        return false
    }

    override fun resumeOnChange() {
        isBulkSave = false
        if (isBulkSaveDirty) onChanged()
        isBulkSaveDirty = false
    }
}

