package renetik.android.store.type

import renetik.android.core.kotlin.changeIf
import renetik.android.core.lang.CSHandler.mainHandler
import renetik.android.event.registration.CSRegistration
import renetik.android.event.registration.later
import renetik.android.event.registration.task.CSBackground.background
import renetik.android.json.CSJson.isJsonPretty
import renetik.android.json.parseJsonMap
import renetik.android.json.toJson

abstract class CSJsonStoreBase(
    private val isPretty: Boolean = isJsonPretty
) : CSJsonObjectStore() {

    override val data: MutableMap<String, Any?> by lazy { load() }
    abstract fun loadJsonString(): String?
    abstract fun saveJsonString(json: String)
    protected fun load() = loadJsonString()?.parseJsonMap() ?: mutableMapOf()

    override fun onChanged() {
        super.onChanged()
        save()
    }

    private var backgroundSaveRegistration: CSRegistration? = null
    private var mainLaterRegistration: CSRegistration? = null
    fun save() {
        if (isBulkSave) return
        mainLaterRegistration?.cancel()
        backgroundSaveRegistration?.cancel()
        backgroundSaveRegistration = background { registration ->
            val json = data
                .changeIf(isPretty) { it.toSortedMap() }
                .toJson(formatted = isPretty)
            if (registration.isActive) {
                mainLaterRegistration?.cancel()
                mainLaterRegistration = mainHandler.later { saveJsonString(json) }
            }
        }
    }
}