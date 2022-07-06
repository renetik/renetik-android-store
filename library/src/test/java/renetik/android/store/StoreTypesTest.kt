package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.core.extensions.content.getString
import renetik.android.core.java.io.readString
import renetik.android.json.CSJson.disableJsonFormat
import renetik.android.json.toJson
import renetik.android.store.extensions.load
import renetik.android.store.extensions.property
import renetik.android.store.type.*
import renetik.android.test.context

class StoreTypesTestData : CSJsonObjectStore() {
    var string: String by property("key1", default = "initial")
    var int: Int by property("key2", default = 5)
    val jsonObject: TestStringData by property("key3")
}

@RunWith(RobolectricTestRunner::class)
class StoreTypesTest {
    @Before
    fun setupAll() = disableJsonFormat()

    @Test
    fun testJsonObjectStore() {
        val store = CSJsonObjectStore()
        val property: StoreTypesTestData by store.property("property")
        assertEquals(5, property.int)

        property.jsonObject.lateString = "new value"
        assertEquals("""{"property":{"key3":{"lateStringId":"new value"}}}""", store.toJson())
        property.string = "new value"
        property.int = 123

        val store2 = CSJsonObjectStore().load(store)
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(123, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }

    @Test
    fun testStringJsonStore() {
        val store = CSStringJsonStore()
        val property: StoreTypesTestData by store.property("property")
        assertEquals(5, property.int)

        property.string = "new value"
        assertEquals("""{"property":{"key1":"new value"}}""", store.jsonString)
        property.jsonObject.lateString = "new value"
        property.int = 100

        val store2 = CSStringJsonStore(store.jsonString)
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(100, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }

    @Test
    fun testPreferencesStore() {
        val store = CSPreferencesStore(context)
        val property: StoreTypesTestData by store.property("property")
        assertEquals(5, property.int)

        property.int = 123
        property.jsonObject.lateString = "new value"
        assertEquals("""{"key2":123,"key3":{"lateStringId":"new value"}}""",
            store.preferences.getString("property"))

        val store2: CSStore = CSPreferencesStore(context)
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("initial", property2.string)
        assertEquals(123, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }

    @Test
    fun testPreferencesJsonStore() {
        val store = CSPreferencesJsonStore(context)
        val property: StoreTypesTestData by store.property("property")
        assertEquals(5, property.int)

        property.string = "new value"
        property.int = 123
        assertEquals("""{"property":{"key1":"new value","key2":123}}""",
            store.preferences.getString(store.key))

        val store2: CSStore = CSPreferencesJsonStore(context)
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(123, property2.int)
        assertNull(property2.jsonObject.nullString)
    }

    @Test
    fun testFileJsonStore() {
        val store = CSFileJsonStore(context, "file")
        val property: StoreTypesTestData by store.property("property")
        assertEquals(5, property.int)

        property.string = "new value"
        property.int = 123
        property.jsonObject.lateString = "new value"
        assertEquals(
            """{"property":{"key1":"new value","key2":123,"key3":{"lateStringId":"new value"}}}""",
            store.file.readString())

        val store2: CSStore = CSFileJsonStore(context, "file")
        val property2: StoreTypesTestData by store2.property("property")
        assertEquals("new value", property2.string)
        assertEquals(123, property2.int)
        assertEquals("new value", property2.jsonObject.lateString)
    }
}