package renetik.android.store
//
//import org.junit.Assert.assertEquals
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner
//import renetik.android.json.obj.load
//import renetik.android.json.toJson
//
//@RunWith(RobolectricTestRunner::class)
//class ComplexJsonObjectStoreTest {
//
//    @Test
//    fun equalsTest() {
//        val instance1 = ComplexJsonObjectStore()
//        instance1.title = "title 1"
//        instance1.list = listOf(
//            ComplexJsonObjectStore().addValues("child title 1"),
//            ComplexJsonObjectStore().addValues("child title 2"))
//        val instance1Loaded = ComplexJsonObjectStore().load(instance1.toJson())
//
////        val instance2 = ComplexJsonObjectStore()
////        instance2.title = "string 2"
////        instance2.list = listOf(SimpleJsonObjectStore("title1"), SimpleJsonObjectStore("title2"))
//
//        assertEquals(instance1Loaded, instance1)
//    }
//}
//
//private fun ComplexJsonObjectStore.addValues(title: String) = apply {
//    this.title = title
//    list = listOf(
//        ComplexJsonObjectStore().apply { this.title = "$title child 1" },
//        ComplexJsonObjectStore().apply { this.title = "$title child 1" })
//}
