package renetik.android.store.property.value

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.Config.NONE
import renetik.android.store.extensions.property
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.type.CSJsonObjectStore
import renetik.android.store.type.CSStringJsonStore

@RunWith(RobolectricTestRunner::class)
@Config(manifest= NONE)
class CSStringValueStorePropertyTest {
    private val store = CSStringJsonStore("{}")
    private var _value: String? = null
    private val property = CSStringValueStoreProperty(store, "key", "") {
        _value = it
    }

    @Test
    fun testInit() {
        assertEquals("", property.value)
        assertEquals(null, _value)
    }

    @Test
    fun testValue() {
        property.value = "test"
        assertEquals("test", property.value)
        assertEquals("test", _value)
        assertEquals("""{"key":"test"}""", store.jsonString)

        property.value = ""
        assertEquals("", property.value)
        assertEquals("", _value)
        assertEquals("""{"key":""}""", store.jsonString)
    }

    @Test
    fun testReload() {
        val property: CSStoreProperty<CSJsonObjectData> =
            store.property("key", CSJsonObjectData::class)
        property.value.string.value = "tralala"

        assertEquals("""{"key":{"key":"tralala"}}""", store.jsonString)

        val property2 = store.property("key", CSJsonObjectData::class)
        assertEquals("tralala", property2.value.string.value)
    }
}

class CSJsonObjectData : CSJsonObjectStore() {
    val string = CSStringValueStoreProperty(this, "key", "", listenStoreChanged = true)
}