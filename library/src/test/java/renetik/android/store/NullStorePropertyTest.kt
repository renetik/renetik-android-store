package renetik.android.store

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.json.CSJson
import renetik.android.json.toJson
import renetik.android.store.TestIdItem.First
import renetik.android.store.TestIdItem.Second
import renetik.android.store.extensions.*
import renetik.android.store.type.CSJsonObjectStore

@RunWith(RobolectricTestRunner::class)
class NullStorePropertyTest {

    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var value by store.nullStringProperty("key", "initial")
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals("initial", value)
        val value2 by store.reload(store.toJson()).nullStringProperty("key")
        assertNull(value2)
    }

    @Test
    fun testBooleanProperty() {
        CSJson.forceStringInJson = true
        var value by store.nullBoolProperty("key")
        assertNull(value)
        value = true
        assertEquals("""{"key":"true"}""", store.toJson())
        val instance2 = store.reload(store.toJson())
            .nullBoolProperty("key")
        assertEquals(true, value)
        CSJson.forceStringInJson = false
    }

    @Test
    fun testIntProperty() {
        var value by store.nullIntProperty("key", 5)
        value = 10
        assertEquals("""{"key":10}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(5, value)
        val value2 by store.reload(store.toJson()).nullIntProperty("key")
        assertNull(value2)
    }

    @Test
    fun testFloatProperty() {
        var value by store.nullFloatProperty("key", 1.5f)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(1.5f, value)
        val value2 by store.reload(store.toJson()).nullFloatProperty("key")
        assertNull(value2)
    }

    @Test
    fun testListItemValueProperty() {
        var value by store.nullListItemProperty("key", TestIdItem.values(), defaultIndex = 1)
        value = First
        assertEquals("""{"key":"1"}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(Second, value)
        val value2 by store.reload(store.toJson()).nullListItemProperty("key", TestIdItem.values())
        assertNull(value2)
    }

    @Test
    fun testJsonProperty() {
        val default = TestStringJsonType().apply { string = "string 2" }
        var value by store.nullJsonProperty("key", default)
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value!!.string)
        assertEquals(null, value!!.nullString)
        assertThrows(Exception::class.java) { value!!.lateString }

        val newString = "string 3"
        value!!.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value!!.nullString = newString
        value!!.lateString = newString

        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(default, value)
        assertNotSame(default, value)

        val value2 by store.reload(store.toJson()).nullJsonProperty("key", default = null)
        assertNull(value2)
    }
}

