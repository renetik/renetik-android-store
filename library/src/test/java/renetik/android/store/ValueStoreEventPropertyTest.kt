package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Test
import renetik.android.core.lang.CSHasId
import renetik.android.event.property.CSEventProperty
import renetik.android.store.ValueStoreEventPropertyTest.Item.*
import renetik.android.store.json.CSStoreJsonObject
import renetik.android.store.property.CSStoreProperty
import renetik.android.store.property.property
import renetik.android.store.property.value.*

class ValueStoreEventPropertyTest {

    private val store: CSStore = CSStoreJsonObject()

    @Test
    fun testStringProperty() {
        val instance: CSStoreProperty<String> =
            store.property("key", default = "initial")
        assertEquals("initial", instance.value)
        instance.value = "new value"
        val instance2 = store.property("key", default = "")
        assertEquals("new value", instance2.value)
    }

    @Test
    fun testBooleanProperty() {
        val instance: CSStoreProperty<Boolean> =
            store.property("key", default = false)
        assertEquals(false, instance.value)
        instance.value = true
        val instance2 = store.property("key", default = false)
        assertEquals(true, instance2.value)
    }

    @Test
    fun testIntProperty() {
        val instance: CSStoreProperty<Int> =
            store.property("key", default = 5)
        assertEquals(5, instance.value)
        instance.value = 345
        val instance2 = store.property("key", default = 10)
        assertEquals(345, instance2.value)
    }

    @Test
    fun testFloatProperty() {
        val instance: CSStoreProperty<Float> =
            store.property("key", default = 1.5f)
        assertEquals(1.5f, instance.value)
        instance.value = 2.5f
        val instance2 = store.property("key", default = 542f)
        assertEquals(2.5f, instance2.value)
    }

    @Test
    fun testDoubleProperty() {
        val instance: CSStoreProperty<Double> =
            store.property("key", default = 1.5)
        assertEquals(1.5, instance.value, 0.0)
        instance.value = 2.3
        val instance2 = store.property("key", default = 5.5)
        assertEquals(2.3, instance2.value, 0.0)
    }

    enum class Item(override val id: String) : CSHasId {
        First("first"), Second("second"), Third("third")
    }

    @Test
    fun testListItemValueProperty() {
        val instance: CSEventProperty<Item> =
            store.property("key", values(), defaultIndex = 1)
        assertEquals(Second, instance.value)
        instance.value = Third
        assertEquals("third", store.get("key"))
        val instance2 = store.property("key", values(), default = First)
        assertEquals(Third, instance2.value)
    }

    @Test
    fun testListValueProperty() {
        val instance: CSEventProperty<List<Item>> =
            store.property("key", values(), default = listOf(First))
        assertEquals(null, store.get("key"))
        instance.value = listOf(First, Third)
        assertEquals("first,third", store.get("key"))
        val instance2 = store.property("key", values(), default = listOf(Second))
        assertEquals(listOf(First, Third), instance2.value)
    }
}