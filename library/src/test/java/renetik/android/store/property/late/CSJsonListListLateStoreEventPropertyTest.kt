package renetik.android.store.property.late

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import renetik.android.json.obj.load
import renetik.android.json.toJson
import renetik.android.store.TestStringJsonType
import renetik.android.store.type.CSJsonObjectStore

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CSJsonListListLateStoreEventPropertyTest {

    private val store = CSJsonObjectStore()

    @Test
    fun `Value set get`() {
        val property = CSJsonListListLateStoreProperty(store, "property", TestStringJsonType::class)
        property.value = listOf(
            listOf(TestStringJsonType("title11"), TestStringJsonType("title12")),
            listOf(TestStringJsonType("title21"), TestStringJsonType("title22")),
            listOf(TestStringJsonType("title31"), TestStringJsonType("title32")))

        assertEquals(property.value[1][1].lateString, "title22")
    }

    @Test
    fun `Save Load`() {
        val property = CSJsonListListLateStoreProperty(store, "property", TestStringJsonType::class)
        property.value = listOf(
            listOf(TestStringJsonType("title11"), TestStringJsonType("title12")),
            listOf(TestStringJsonType("title21"), TestStringJsonType("title22")),
            listOf(TestStringJsonType("title31"), TestStringJsonType("title32")))

        val json = store.toJson(formatted = true)

        val store2 = CSJsonObjectStore()
        val property2 = CSJsonListListLateStoreProperty(store2, "property", TestStringJsonType::class)
        store2.load(json)

        assertEquals(property2.value[1][1].lateString, "title22")
    }
}

