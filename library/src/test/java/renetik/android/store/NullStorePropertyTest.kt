package renetik.android.store

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.json.CSJson
import renetik.android.json.toJson
import renetik.android.store.TestIdItem.Companion.TestIdItems
import renetik.android.store.TestIdItem.First
import renetik.android.store.TestIdItem.Second
import renetik.android.store.extensions.*
import renetik.android.store.type.CSJsonObjectStore
import renetik.android.test.assertThrows

@RunWith(RobolectricTestRunner::class)
class NullStorePropertyTest {
    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var value: String? by store.nullStringProperty("key", "initial")
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals("initial", value)

        store.reload(store.toJson())
        val value2: String? by store.nullStringProperty("key")
        assertNull(value2)
    }

    @Test
    fun testBooleanProperty() {
        CSJson.forceString = true
        var value: Boolean? by store.nullBoolProperty("key")
        assertNull(value)
        value = true
        assertEquals("""{"key":"true"}""", store.toJson())

        store.reload(store.toJson())
        val value2: Boolean? by store.nullBoolProperty("key")
        assertEquals(true, value2)
        CSJson.forceString = false
    }

    @Test
    fun testIntProperty() {
        var value: Int? by store.nullIntProperty("key", 5)
        value = 10
        assertEquals("""{"key":10}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(5, value)

        store.reload(store.toJson())
        val value2: Int? by store.nullIntProperty("key")
        assertNull(value2)
    }

    @Test
    fun testFloatProperty() {
        var value: Float? by store.nullFloatProperty("key", 1.5f)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(1.5f, value)

        store.reload(store.toJson())
        val value2: Float? by store.nullFloatProperty("key")
        assertNull(value2)
    }

    @Test
    fun testListItemValueProperty() {
        var value: TestIdItem? by store.nullListItemProperty("key",
            TestIdItems, defaultIndex = 1)
        value = First
        assertEquals("""{"key":"id1"}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(Second, value)

        store.reload(store.toJson())
        val value2: TestIdItem? by store.nullListItemProperty("key", TestIdItems)
        assertNull(value2)
    }

    @Test
    fun testJsonProperty() {
        val default = TestStringData(string = "string 2")
        var value: TestStringData? by store.nullJsonProperty("key", default)
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value!!.string)
        assertEquals(null, value!!.nullString)
        assertThrows { value!!.lateString }

        val newString = "string 3"
        value!!.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value!!.nullString = newString
        value!!.lateString = newString

        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(default, value)
        assertNotSame(default, value)

        store.reload(store.toJson())
        val value2: TestStringData? by store.nullJsonProperty("key")
        assertNull(value2)
    }
}

