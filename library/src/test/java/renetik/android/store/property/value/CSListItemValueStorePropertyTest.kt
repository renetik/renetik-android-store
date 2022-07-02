package renetik.android.store.property.value

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import renetik.android.store.json.CSStringJsonStore
import renetik.android.store.property

private enum class TestEnum {
    First, Second, Third
}

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class CSListItemValueStorePropertyTest {

    private val store = CSStringJsonStore("{}")
    private var _value: TestEnum? = null
    private val property = store.property("key", TestEnum.values(), TestEnum.First) {
        _value = it
    }

    @Test
    fun firstLoad() {
        Assert.assertEquals(TestEnum.First, property.value)
        Assert.assertEquals(null, _value)
    }

    @Test
    fun value() {
        property.value = TestEnum.Second
        Assert.assertEquals(TestEnum.Second, property.value)
        Assert.assertEquals(TestEnum.Second, _value)
        Assert.assertTrue(store.has("key"))
        Assert.assertEquals("""{"key":"Second"}""", store.jsonString)
    }

//    @Test
//    fun reload() {
//        property.save(store, TestEnum.Third)
//        Assert.assertEquals("""{"key":"Third"}""", store.jsonString)
//        Assert.assertEquals(TestEnum.First, property.value)
//
//        property.reload()
//        Assert.assertEquals(TestEnum.Third, property.value)
//        Assert.assertEquals(TestEnum.Third, _value)
//    }
}