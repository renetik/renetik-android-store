package renetik.android.store

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment.getApplication
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.json.CSJson
import renetik.android.json.toJson
import renetik.android.store.CSStore.Companion.store
import renetik.android.store.extensions.CSStore.property
import renetik.android.store.extensions.reload

@RunWith(RobolectricTestRunner::class)
class DefaultStoreTest {

    @Before
    fun prepare() {
        app = getApplication()
        CSJson.forceString = true
    }

    @Test
    fun defaultStoreTest() {
        var string: String by property("string", "string")
        var bool: Boolean by property("bool", false)
        var int: Int by property("int", 5)
        assertEquals("""{}""", store.toJson())

        string = "new value"
        bool = true
        int = 10
        assertEquals("""{"string":"new value","bool":"true","int":"10"}""",
            store.toJson())

        store.reload("""{"string":"new value 2"}""")
        assertEquals("new value 2", string)
        assertEquals(false, bool)
        assertEquals(5, int)
        CSJson.forceString = false
    }

    @After
    fun backToDefault() {
        CSJson.forceString = false
    }
}