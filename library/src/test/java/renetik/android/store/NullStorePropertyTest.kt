package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
        var property = store.nullStringProperty("key", "initial")
        property.value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        property.value = null
        assertEquals("""{}""", store.toJson())
        assertEquals("initial", property.value)
        property = store.reload(store.toJson()).nullStringProperty("key")
        assertNull(property.value)
    }

    @Test
    fun testBooleanProperty() {
        CSJson.forceStringInJson = true
        val instance = store.nullBoolProperty("key")
        assertNull(instance.value)
        instance.value = true
        assertEquals("""{"key":"true"}""", store.toJson())
        val instance2 = store.reload(store.toJson())
            .nullBoolProperty("key")
        assertEquals(true, instance2.value)
        CSJson.forceStringInJson = false
    }

    @Test
    fun testIntProperty() {
        var property = store.nullIntProperty("key", 5)
        property.value = 10
        assertEquals("""{"key":10}""", store.toJson())
        property.value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(5, property.value)
        property = store.reload(store.toJson()).nullIntProperty("key")
        assertNull(property.value)
    }

    @Test
    fun testFloatProperty() {
        var property = store.nullFloatProperty("key", 1.5f)
        property.value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        property.value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(1.5f, property.value)
        property = store.reload(store.toJson()).nullFloatProperty("key")
        assertNull(property.value)
    }

    @Test
    fun testListItemValueProperty() {
        var property = store.nullProperty("key", TestIdItem.values(), defaultIndex = 1)
        property.value = First
        assertEquals("""{"key":"1"}""", store.toJson())
        property.value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(Second, property.value)
        property = store.reload(store.toJson()).nullProperty("key", TestIdItem.values())
        assertNull(property.value)
    }

    @Test
    fun testJsonProperty() {
        val property = store.nullProperty("key", TestJsonType("string"))
        assertEquals("""{}""", store.toJson())
        assertEquals("string", property.value!!.string.value)
        assertEquals("string", property.value!!.nullString.value)
        assertEquals("string", property.value!!.lateString.value)

//        val newString = "new string"
//        property.value!!.string.value = newString
//        assertEquals("""{"key":{"stringId":"new string","lateStringId":"string","nullStringId":"string"}}""", store.toJson())
//        property.value!!.nullString.value = newString
//        property.value!!.lateString.value = newString

//        property.value = null
//        assertEquals("""{}""", store.toJson())
//        assertEquals(TestJsonType("string"), property.value)
//        property = store.reload(store.toJson()).nullProperty("key")
//        assertNull(property.value)
    }
}

