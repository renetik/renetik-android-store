package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.store.extensions.property
import renetik.android.store.type.*
import renetik.android.test.context

class StoreTypesTestData : CSJsonObjectStore() {
    var string: String by property("key1", default = "initial")
    var int: Int by property("key2", default = 0)
    val jsonObject: TestStringData by property("key3")
}

@RunWith(RobolectricTestRunner::class)
class StoreTypesTest {
    @Test
    fun testJsonObjectStore() {
        val store = CSStringJsonStore()
        val property: StoreTypesTestData by store.property("property")
        property.string = "new value"
        property.int = 123
        property.jsonObject.lateString = "new value"

        val store2 = CSStringJsonStore(store.jsonString)
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(123, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }

    @Test
    fun testPreferencesStore() {
        val store = CSPreferencesStore(context)
        val property: StoreTypesTestData by store.property("property")
        property.string = "new value"
        property.int = 123
        property.jsonObject.lateString = "new value"
        assertTrue(store.preferences.contains("property"))

        val store2: CSStore = CSPreferencesStore(context)
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(123, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }

    @Test
    fun testPreferencesJsonStore() {
        val store = CSPreferencesJsonStore(context)
        val property: StoreTypesTestData by store.property("property")
        property.string = "new value"
        property.int = 123
        property.jsonObject.lateString = "new value"
        assertTrue(store.preferences.contains(store.key))

        val store2: CSStore = CSPreferencesJsonStore(context)
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(123, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }

    @Test
    fun testFileJsonStore() {
        val store = CSFileJsonStore(context, "file")
        val property: StoreTypesTestData by store.property("property")
        property.string = "new value"
        property.int = 123
        property.jsonObject.lateString = "new value"
        assertTrue(store.file.exists())

        val store2: CSStore = CSFileJsonStore(context, "file")
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(123, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }
}

