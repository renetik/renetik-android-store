package renetik.android.store.type

import renetik.android.event.CSEvent.Companion.event
import renetik.android.json.obj.CSJsonObject
import renetik.android.store.CSStore

open class CSJsonObjectStore : CSJsonObject(), CSStore {

    companion object {
        fun CSJsonObjectStore(data: Map<String, Any?>) =
            CSJsonObjectStore().apply { load(data) }
    }

    override val eventLoaded = event<CSStore>()
    override val eventChanged = event<CSStore>()
    override fun load(data: Map<String, Any?>) = super.load(data)
    override fun onLoaded() = eventLoaded.fire(this)
    open fun onChanged() = eventChanged.fire(this)

    override fun onChange() {
        if (!isPausedOnChange) onChanged()
        else isChangeWhilePaused = true
    }

    private var isChangeWhilePaused = false
    var isPausedOnChange = false

    override fun pauseOnChange(): Boolean {
        if (!isPausedOnChange) {
            isPausedOnChange = true
            isChangeWhilePaused = false
            return true
        }
        return false
    }

    override fun resumeOnChange() {
        isPausedOnChange = false
        if (isChangeWhilePaused) onChanged()
        isChangeWhilePaused = false
    }
}

