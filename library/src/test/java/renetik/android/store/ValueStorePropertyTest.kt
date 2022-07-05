package renetik.android.store

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.json.toJson
import renetik.android.store.TestIdItem.*
import renetik.android.store.extensions.property
import renetik.android.store.extensions.reload
import renetik.android.store.type.CSJsonObjectStore

@RunWith(RobolectricTestRunner::class)
class ValueStorePropertyTest {

    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var value by store.property("key", default = "initial")
        assertEquals("initial", value)
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = "")
        assertEquals("new value", value2)
    }

    @Test
    fun testBooleanProperty() {
        var value by store.property("key", default = false)
        assertEquals(false, value)
        value = true
        assertEquals("""{"key":true}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = false)
        assertEquals(true, value2)
    }

    @Test
    fun testIntProperty() {
        var value by store.property("key", default = 5)
        assertEquals(5, value)
        value = 345
        assertEquals("""{"key":345}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = 10)
        assertEquals(345, value2)
    }

    @Test
    fun testFloatProperty() {
        var value by store.property("key", default = 1.5f)
        assertEquals(1.5f, value)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = 542f)
        assertEquals(2.5f, value2)
    }

    @Test
    fun testDoubleProperty() {
        var value by store.property("key", default = 1.5)
        assertEquals(1.5, value, 0.0)
        value = 2.3
        assertEquals("""{"key":"2.3"}""", store.toJson(forceString = true))
        val value2 by store.reload(store.toJson()).property("key", default = 5.5)
        assertEquals(2.3, value2, 0.0)
    }

    @Test
    fun testListItemValueProperty() {
        var value by store.property("key", TestIdItem.values(), defaultIndex = 1)
        assertEquals(Second, value)
        value = Third
        assertEquals("""{"key":"3"}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", values(), default = First)
        assertEquals(Third, value2)
    }

    @Test
    fun testListValueProperty() {
        var value by store.property("key", TestIdItem.values(), default = listOf(First))
        assertEquals(null, store.get("key"))
        value = listOf(First, Third)
        assertEquals("""{"key":"1,3"}""", store.toJson())
        val value2 by store.reload(store.toJson())
            .property("key", values(), default = listOf(Second))
        assertEquals(listOf(First, Third), value2)
    }

    @Test
    fun testJsonTypeValueProperty() {
        val value by store.property<TestStringJsonType>("key")
        assertEquals("""{}""", store.toJson())
        assertEquals("string", value.string)
        assertNull(value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "new string"
        value.string = newString
        assertEquals("""{"key":{"stringId":"new string"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        val value2 by store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonTypeValuePropertyDefault() {
        val value by store.property("key", TestStringJsonType()
            .apply { string = "string 2" })
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value.string)
        assertEquals(null, value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "string 3"
        value.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        val value2 by store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonListValueProperty() {
        val property = store.property<TestStringJsonType>("key", mutableListOf())
        assertEquals("""{}""", store.toJson())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType().apply { lateString = "string" })
        property.save()
        assertEquals("""{"key":[{},{},{"lateStringId":"string"}]}""", store.toJson())
        property.value.last().lateString = "new string"
        property.save()

        val value by store.reload(store.toJson()).property<TestStringJsonType>("key", listOf())
        assertEquals("new string", value.last().lateString)
    }
}

