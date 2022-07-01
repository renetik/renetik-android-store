package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Test
import renetik.android.core.lang.CSHasId
import renetik.android.store.json.CSStoreJsonObject
import renetik.android.store.property.property

class ValueStoreEventPropertyTest {

    private val store: CSStore = CSStoreJsonObject()

    @Test
    fun testStringProperty() {
        val instance = store.property("key", "initial")
        assertEquals("initial", instance.value)
        instance.value = "new value"
        val instance2 = store.property("key", "initial")
        assertEquals("new value", instance2.value)
    }

    @Test
    fun testBooleanProperty() {
        val instance = store.property("key", false)
        assertEquals(false, instance.value)
        instance.value = true
        val instance2 = store.property("key", false)
        assertEquals(true, instance2.value)
    }

    @Test
    fun testIntProperty() {
        val instance = store.property("key", 5)
        assertEquals(5, instance.value)
        instance.value = 345
        val instance2 = store.property("key", 10)
        assertEquals(345, instance2.value)
    }

    @Test
    fun testFloatProperty() {
        val instance = store.property("key", 1.5f)
        assertEquals(1.5f, instance.value)
        instance.value = 2.5f
        val instance2 = store.property("key", 542f)
        assertEquals(2.5f, instance2.value)
    }

    @Test
    fun testDoubleProperty() {
        val instance = store.property("key", 1.5)
        assertEquals(1.5, instance.value, 0.0)
        instance.value = 2.3
        val instance2 = store.property("key", 5.5)
        assertEquals(2.3, instance2.value, 0.0)
    }

    enum class Item(override val id: String) : CSHasId {
        First("first"), Second("second"), Third("third")
    }

    @Test
    fun testListItemValueProperty() {
        val instance = store.property("key", Item.values(), Item.Second)
        assertEquals(Item.Second, instance.value)
        instance.value = Item.First
        val instance2 = store.property("key", Item.values(), Item.Third)
        assertEquals(Item.First, instance2.value)
    }
}