package renetik.android.store

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment.getApplication
import org.robolectric.annotation.Config
import renetik.android.core.lang.CSEnvironment.app
import renetik.android.json.CSJson
import renetik.android.json.toJson
import renetik.android.store.CSStore.Companion.fileStore
import renetik.android.store.extensions.dataProperty
import renetik.android.store.extensions.reload

@RunWith(RobolectricTestRunner::class)
@Config(application = TestCSApplication::class)
class DefaultStoreTest {

    @Before
    fun prepare() {
        app = getApplication()
        CSJson.forceString = true
    }

    @Test
    fun defaultStoreTest() {
        var string: String by fileStore.dataProperty("string", "string")
        var bool: Boolean by fileStore.dataProperty("bool", false)
        var int: Int by fileStore.dataProperty("int", 5)
        assertEquals("""{}""", fileStore.toJson())

        string = "new value"
        bool = true
        int = 10
        assertEquals(
            """{"string":"new value","bool":"true","int":"10"}""",
            fileStore.toJson()
        )

        fileStore.reload("""{"string":"new value 2"}""")
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