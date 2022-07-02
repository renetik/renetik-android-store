package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Test
import renetik.android.store.TestIdItem.*
import renetik.android.store.extensions.property
import renetik.android.store.type.CSJsonObjectStore

class ValueStoreEventPropertyTest {

    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        val instance = store.property("key", default = "initial")
        assertEquals("initial", instance.value)
        instance.value = "new value"
        val instance2 = store.property("key", default = "")
        assertEquals("new value", instance2.value)
    }

    @Test
    fun testBooleanProperty() {
        val instance = store.property("key", default = false)
        assertEquals(false, instance.value)
        instance.value = true
        val instance2 = store.property("key", default = false)
        assertEquals(true, instance2.value)
    }

    @Test
    fun testIntProperty() {
        val instance = store.property("key", default = 5)
        assertEquals(5, instance.value)
        instance.value = 345
        val instance2 = store.property("key", default = 10)
        assertEquals(345, instance2.value)
    }

    @Test
    fun testFloatProperty() {
        val instance = store.property("key", default = 1.5f)
        assertEquals(1.5f, instance.value)
        instance.value = 2.5f
        val instance2 = store.property("key", default = 542f)
        assertEquals(2.5f, instance2.value)
    }

    @Test
    fun testDoubleProperty() {
        val instance = store.property("key", default = 1.5)
        assertEquals(1.5, instance.value, 0.0)
        instance.value = 2.3
        val instance2 = store.property("key", default = 5.5)
        assertEquals(2.3, instance2.value, 0.0)
    }

    @Test
    fun testListItemValueProperty() {
        val instance = store.property("key", TestIdItem.values(), defaultIndex = 1)
        assertEquals(Second, instance.value)
        instance.value = Third
        assertEquals("third", store.get("key"))
        val instance2 = store.property("key", values(), default = First)
        assertEquals(Third, instance2.value)
    }

    @Test
    fun testListValueProperty() {
        val instance = store.property("key", TestIdItem.values(), default = listOf(First))
        assertEquals(null, store.get("key"))
        instance.value = listOf(First, Third)
        assertEquals("1,3", store.get("key"))
        val instance2 = store.property("key", values(), default = listOf(Second))
        assertEquals(listOf(First, Third), instance2.value)
    }
}

