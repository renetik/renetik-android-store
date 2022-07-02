package renetik.android.store.property.late

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import renetik.android.json.obj.load
import renetik.android.json.toJson
import renetik.android.store.type.CSJsonObjectStore
import renetik.android.store.lateStringProperty

class TestData() : CSJsonObjectStore() {
    val title = lateStringProperty("title")

    constructor(title: String) : this() {
        this.title.value(title)
    }
}

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CSJsonListListLateStoreEventPropertyTest {

    private val store = CSJsonObjectStore()

    @Test
    fun `Value set get`() {
        val property = CSJsonListListLateStoreProperty(store, "property", TestData::class)
        property.value = listOf(
            listOf(TestData("title11"), TestData("title12")),
            listOf(TestData("title21"), TestData("title22")),
            listOf(TestData("title31"), TestData("title32")))

        assertEquals(property.value[1][1].title.value, "title22")
    }

    @Test
    fun `Save Load`() {
        val property = CSJsonListListLateStoreProperty(store, "property", TestData::class)
        property.value = listOf(
            listOf(TestData("title11"), TestData("title12")),
            listOf(TestData("title21"), TestData("title22")),
            listOf(TestData("title31"), TestData("title32")))

        val json = store.toJson(formatted = true)

        val store2 = CSJsonObjectStore()
        val property2 = CSJsonListListLateStoreProperty(store2, "property", TestData::class)
        store2.load(json)

        assertEquals(property2.value[1][1].title.value, "title22")
    }
}

