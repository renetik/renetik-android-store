package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.json.toJson
import renetik.android.store.TestIdItem.*
import renetik.android.store.TestIdItem.Companion.TestIdItems
import renetik.android.store.extensions.property
import renetik.android.store.extensions.reload
import renetik.android.store.property.save
import renetik.android.store.type.CSJsonObjectStore
import renetik.android.test.assertThrows

@Suppress("UNUSED_VALUE", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
@RunWith(RobolectricTestRunner::class)
class ValueStorePropertyTest {
    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var eventCount = 0
        var value: String by store.property("key", default = "initial") { eventCount += 1 }
        assertEquals("initial", value)
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())

        store.reload("""{"key":"new value 2"}""")
        val value2: String by store.property("key", default = "")

        assertEquals("new value 2", value)
        assertEquals("new value 2", value2)
        assertEquals(2, eventCount)
    }

    @Test
    fun testBooleanProperty() {
        var value: Boolean by store.property("key", default = false)
        assertEquals(false, value)
        value = true
        assertEquals("""{"key":true}""", store.toJson())

        store.reload(store.toJson())
        val value2: Boolean by store.property("key", default = false)
        assertEquals(true, value2)
    }

    @Test
    fun testIntProperty() {
        var value: Int by store.property("key", default = 5)
        assertEquals(5, value)
        value = 345
        assertEquals("""{"key":345}""", store.toJson())

        store.reload(store.toJson())
        val value2: Int by store.property("key", default = 10)
        assertEquals(345, value2)
    }

    @Test
    fun testFloatProperty() {
        var eventCount = 0
        var value: Float by store.property("key", default = 1.5f) { eventCount += 1 }
        assertEquals(1.5f, value)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())

        store.reload("""{"key":2.3}""")
        assertEquals(2, eventCount)

        val value2: Float by store.property("key", default = 542f)
        assertEquals(2.3f, value2)
    }

    @Test
    fun testDoubleProperty() {
        var eventCount = 0
        var value: Double by store.property("key", default = 1.5) { eventCount += 1 }
        assertEquals(1.5, value, 0.0)
        value = 2.3
        assertEquals("""{"key":"2.3"}""", store.toJson(forceString = true))

        store.reload(store.toJson())
        val value2: Double by store.property("key", default = 5.5)
        assertEquals(2.3, value2, 0.0)
        assertEquals(1, eventCount)
    }

    @Test
    fun testListItemValueProperty() {
        var value: TestIdItem by store.property("key",
            TestIdItems, defaultIndex = 1)
        assertEquals(Second, value)
        value = Third
        assertEquals("""{"key":"id3"}""", store.toJson())

        store.reload(store.toJson())
        val value2: TestIdItem by store.property("key",
            TestIdItems, default = First)
        assertEquals(Third, value2)
    }

    @Test
    fun testListValueProperty() {
        var value: List<TestIdItem> by store.property("key",
            TestIdItems, default = listOf(First))
        assertEquals(null, store.get("key"))
        value = listOf(First, Third)
        assertEquals("""{"key":"id1,id3"}""", store.toJson())

        store.reload(store.toJson())
        val value2: List<TestIdItem> by store.property("key",
            TestIdItems, default = listOf(Second))
        assertEquals(listOf(First, Third), value2)
    }

    @Test
    fun testJsonValueProperty() {
        val value: TestStringData by store.property("key")
        assertEquals("""{}""", store.toJson())
        assertEquals("string", value.string)
        assertNull(value.nullString)
        assertThrows { value.lateString }

        val newString = "new string"
        value.string = newString
        assertEquals("""{"key":{"stringId":"new string"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        store.reload(store.toJson())
        val value2: TestStringData by store.property("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonValuePropertyDefault() {
        val value: TestStringData by store.property("key",
            TestStringData(string = "string 2"))
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value.string)
        assertEquals(null, value.nullString)
        assertThrows { value.lateString }

        val newString = "string 3"
        value.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        store.reload(store.toJson())
        val value2: TestStringData by store.property("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonListValueProperty() {
        val property = store.property<TestStringData>("key", mutableListOf())
        assertEquals("""{}""", store.toJson())
        property.value.add(TestStringData())
        property.value.add(TestStringData())
        property.value.add(TestStringData(lateString = "string"))
        property.save()
        assertEquals("""{"key":[{},{},{"lateStringId":"string"}]}""", store.toJson())
        property.value.last().lateString = "new string"
        property.save()

        store.reload(store.toJson())
        val value: List<TestStringData> by store.property("key", listOf())
        assertEquals("new string", value.last().lateString)
    }
}

