package renetik.android.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import renetik.android.json.obj.clone
import renetik.android.json.obj.load
import renetik.android.json.toJson
import renetik.android.store.extensions.lateStringProperty
import renetik.android.store.extensions.nullStringProperty
import renetik.android.store.extensions.property
import renetik.android.store.extensions.reload
import renetik.android.store.type.CSJsonObjectStore

class TestStringJsonType() : CSJsonObjectStore() {
    constructor(string: String? = null, nullString: String? = null,
                lateString: String? = null) : this() {
        string?.let { this.string = it }
        nullString?.let { this.nullString = it }
        lateString?.let { this.lateString = it }
    }

    var string: String by property("stringId", "string")
    var nullString: String? by nullStringProperty("nullStringId")
    var lateString: String by lateStringProperty("lateStringId")
}

@RunWith(RobolectricTestRunner::class)
class TestStringJsonTypeTest {
    @Test
    fun jsonTypeTest() {
        val instance = TestStringJsonType()
        assertEquals("""{}""", instance.toJson())
        instance.string = "string"
        assertEquals("""{"stringId":"string"}""", instance.toJson())
        instance.lateString = "lateString"
        assertEquals("""{"stringId":"string","lateStringId":"lateString"}""", instance.toJson())
        instance.nullString = "nullString"
        assertEquals(
            """{"stringId":"string","lateStringId":"lateString","nullStringId":"nullString"}""",
            instance.toJson())
        instance.nullString = null
        assertEquals("""{"stringId":"string","lateStringId":"lateString"}""", instance.toJson())
    }

    @Test
    fun reloadJsonTypeTest() {
        val instance = TestStringJsonType()
        assertEquals("""{}""", instance.toJson())
        instance.string = "string 2"
        assertEquals("""{"stringId":"string 2"}""", instance.toJson())
        instance.reload(
            """{"stringId":"string 3","lateStringId":"lateString","nullStringId":"nullString"}""")
        assertEquals("string 3", instance.string)
        assertEquals("lateString", instance.lateString)
    }

    @Test
    fun loadJsonTypeTest() {
        val instance = TestStringJsonType()
        assertEquals("""{}""", instance.toJson())
        instance.string = "string 2"
        assertEquals("""{"stringId":"string 2"}""", instance.toJson())
        instance.load(
            """{"lateStringId":"lateString"}""")
        assertEquals("string 2", instance.string)
        assertEquals(null, instance.nullString)
        assertEquals("lateString", instance.lateString)
    }

    @Test
    fun cloneJsonTypeTest() {
        val instance = TestStringJsonType("string 2", "nullString", "lateString")
        assertEquals("lateString", instance.lateString)
        val instance2 = instance.clone()
        assertNotSame(instance, instance2)
        assertEquals("lateString", instance2.lateString)
    }
}