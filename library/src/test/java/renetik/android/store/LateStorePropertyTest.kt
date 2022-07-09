package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.json.CSJson.forceString
import renetik.android.json.toJson
import renetik.android.store.TestIdItem.Companion.TestIdItems
import renetik.android.store.TestIdItem.Fourth
import renetik.android.store.TestIdItem.Second
import renetik.android.store.extensions.*
import renetik.android.store.type.CSJsonObjectStore
import renetik.android.test.assertThrows

@RunWith(RobolectricTestRunner::class)
class LateStorePropertyTest {
    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var eventCount = 0
        var newValue: String? = null
        var value: String by store.lateStringProperty("key") {
            newValue = it
            eventCount += 1
        }
        value = "value 1"

        assertEquals("""{"key":"value 1"}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":"value 2"}""")
        assertEquals(newValue, "value 2")
        assertEquals(2, eventCount)
    }


    @Test
    fun testBooleanProperty() {
        var newValue: Boolean? = null
        var value: Boolean by store.lateBoolProperty("key") { newValue = it }
        value = false

        assertEquals("""{"key":false}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":true}""")
        assertTrue(newValue!!)
    }

    @Test
    fun testIntProperty() {
        forceString = true
        var newValue: Int? = null
        var value: Int by store.lateIntProperty("key") { newValue = it }
        value = 34

        assertEquals("""{"key":"34"}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":"56"}""")
        assertEquals(56, newValue)
        forceString = false
    }

    @Test
    fun testListItemValueProperty() {
        var eventCount = 0
        var newValue: TestIdItem? = null
        var value: TestIdItem by store.lateListItemProperty("key", TestIdItems) {
            newValue = it
            eventCount += 1
        }
        value = Fourth

        assertEquals("""{"key":"id4"}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":"id2"}""")
        assertEquals(newValue, Second)
        assertEquals(2, eventCount)
    }

    @Test
    fun testJsonProperty() {
        var newValue: TestStringData? = null
        var value: TestStringData by store.lateJsonProperty("key") { newValue = it }
        value = TestStringData()

        assertEquals("""{"key":{}}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":{"stringId":"string 1"}}""")
        assertEquals(newValue, TestStringData().apply { string = "string 1" })
    }

    @Test
    fun testLateJsonListProperty() {
        var newValue: List<TestStringData>? = null
        var value: List<TestStringData> by store.lateJsonListProperty("key") { newValue = it }
        value = listOf(TestStringData(), TestStringData(lateString = "string 1"))

        assertEquals("""{"key":[{},{"lateStringId":"string 1"}]}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":[{"nullStringId":"string 2"},{}]}""")
        assertEquals(newValue,
            listOf(TestStringData(nullString = "string 2"), TestStringData()))
    }

    @Test
    fun testLateJsonListListProperty() {
        var newValue: List<List<TestStringData>>? = null
        var value: List<List<TestStringData>> by store.lateJsonListListProperty("key") {
            newValue = it
        }
        assertThrows { value.last() }
        value = listOf(listOf(TestStringData()), listOf(), listOf(TestStringData("test")))

        assertEquals("""{"key":[[{}],[],[{"stringId":"test"}]]}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":[[{"stringId":"test 2"}],[]]}""")
        assertEquals("test 2", value.first().first().string)
    }
}

