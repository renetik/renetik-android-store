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
        val instance = store.property("key", default = "initial")
        assertEquals("initial", instance.value)
        instance.value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        val instance2 = store.reload(store.toJson()).property("key", default = "")
        assertEquals("new value", instance2.value)
    }

    @Test
    fun testBooleanProperty() {
        val instance = store.property("key", default = false)
        assertEquals(false, instance.value)
        instance.value = true
        assertEquals("""{"key":true}""", store.toJson())
        val instance2 = store.reload(store.toJson()).property("key", default = false)
        assertEquals(true, instance2.value)
    }

    @Test
    fun testIntProperty() {
        val instance = store.property("key", default = 5)
        assertEquals(5, instance.value)
        instance.value = 345
        assertEquals("""{"key":345}""", store.toJson())
        val instance2 = store.reload(store.toJson()).property("key", default = 10)
        assertEquals(345, instance2.value)
    }

    @Test
    fun testFloatProperty() {
        val instance = store.property("key", default = 1.5f)
        assertEquals(1.5f, instance.value)
        instance.value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        val instance2 = store.reload(store.toJson()).property("key", default = 542f)
        assertEquals(2.5f, instance2.value)
    }

    @Test
    fun testDoubleProperty() {
        val instance = store.property("key", default = 1.5)
        assertEquals(1.5, instance.value, 0.0)
        instance.value = 2.3
        assertEquals("""{"key":"2.3"}""", store.toJson(forceString = true))
        val instance2 = store.reload(store.toJson()).property("key", default = 5.5)
        assertEquals(2.3, instance2.value, 0.0)
    }

    @Test
    fun testListItemValueProperty() {
        val instance = store.property("key", TestIdItem.values(), defaultIndex = 1)
        assertEquals(Second, instance.value)
        instance.value = Third
        assertEquals("""{"key":"3"}""", store.toJson())
        val instance2 = store.reload(store.toJson()).property("key", values(), default = First)
        assertEquals(Third, instance2.value)
    }

    @Test
    fun testListValueProperty() {
        val instance = store.property("key", TestIdItem.values(), default = listOf(First))
        assertEquals(null, store.get("key"))
        instance.value = listOf(First, Third)
        assertEquals("""{"key":"1,3"}""", store.toJson())
        val instance2 = store.reload(store.toJson())
            .property("key", values(), default = listOf(Second))
        assertEquals(listOf(First, Third), instance2.value)
    }

    @Test
    fun testJsonTypeValueProperty() {
        val instance = store.property<TestStringJsonType>("key")
        assertEquals("""{}""", store.toJson())
        assertEquals("string", instance.value.string.value)
        assertNull(instance.value.nullString.value)
        assertThrows(Exception::class.java) { instance.value.lateString.value }

        val newString = "new string"
        instance.value.string.value = newString
        assertEquals("""{"key":{"stringId":"new string"}}""", store.toJson())
        instance.value.nullString.value = newString
        instance.value.lateString.value = newString

        val instance2 = store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, instance2.value.string.value)
        assertEquals(newString, instance2.value.nullString.value)
        assertEquals(newString, instance2.value.lateString.value)
    }

    @Test
    fun testJsonTypeValuePropertyDefault() {
        val property = store.property("key", TestStringJsonType("string"))
        assertEquals("""{}""", store.toJson())
        assertEquals("string", property.value.string.value)
        assertEquals("string", property.value.nullString.value)
        assertEquals("string", property.value.lateString.value)

        val newString = "new string"
        property.value.string.value = newString
        assertEquals(
            """{"key":{"stringId":"new string","lateStringId":"string","nullStringId":"string"}}""",
            store.toJson())
        property.value.nullString.value = newString
        property.value.lateString.value = newString

        val property2 = store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, property2.value.string.value)
        assertEquals(newString, property2.value.nullString.value)
        assertEquals(newString, property2.value.lateString.value)
    }

    @Test
    fun testJsonListValueProperty() {
        val property = store.property<TestStringJsonType>("key", mutableListOf())
        assertEquals("""{}""", store.toJson())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType().apply { lateString.value = "string" })
        property.save()
        assertEquals("""{"key":[{},{},{"lateStringId":"string"}]}""", store.toJson())
        property.value.last().lateString.value = "new string"
        property.save()

        val property2 = store.reload(store.toJson()).property<TestStringJsonType>("key", listOf())
        assertEquals("new string", property2.value.last().lateString.value)
    }
}

