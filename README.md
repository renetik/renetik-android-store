<!---Header--->
[![Android Build](https://github.com/renetik/renetik-android-store/workflows/Android%20Build/badge.svg)
](https://github.com/renetik/renetik-android-store/actions/workflows/android.yml)

# Renetik Android - Store
#### [https://github.com/renetik/renetik-android-store](https://github.com/renetik/renetik-android-store/) ➜ [Documentation](https://renetik.github.io/renetik-android-store/)

Framework to enjoy, improve and speed up your application development while writing readable code.
Used as library in many projects and improving it while developing new projects.
I am open for [Hire](https://renetik.github.io) or investment in my mobile app music production & perfromance project Renetik Instruments www.renetik.com.

```gradle
allprojects {
    repositories {
        // For master-SNAPSHOT
        maven { url 'https://github.com/renetik/maven-snapshot/raw/master/repository' }
        // For release builds
        maven { url 'https://github.com/renetik/maven/raw/master/repository' }
    }
}
```

Step 2. Add the dependency

```gradle
dependencies {
    implementation 'com.renetik.library:renetik-android-store:$renetik-android-version'
}
```
```kotlin
class TestStringJsonType() : CSJsonObjectStore() {
    constructor(value: String) : this(value, value, value)
    constructor(value1: String, value2: String, value3: String) : this() {
        string = value1
        nullString = value2
        lateString = value3
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
```
```kotlin
class ValueStorePropertyTest {

    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var value by store.property("key", default = "initial")
        assertEquals("initial", value)
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = "")
        assertEquals("new value", value2)
    }

    @Test
    fun testBooleanProperty() {
        var value by store.property("key", default = false)
        assertEquals(false, value)
        value = true
        assertEquals("""{"key":true}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = false)
        assertEquals(true, value2)
    }

    @Test
    fun testIntProperty() {
        var value by store.property("key", default = 5)
        assertEquals(5, value)
        value = 345
        assertEquals("""{"key":345}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = 10)
        assertEquals(345, value2)
    }

    @Test
    fun testFloatProperty() {
        var value by store.property("key", default = 1.5f)
        assertEquals(1.5f, value)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = 542f)
        assertEquals(2.5f, value2)
    }

    @Test
    fun testDoubleProperty() {
        var value by store.property("key", default = 1.5)
        assertEquals(1.5, value, 0.0)
        value = 2.3
        assertEquals("""{"key":"2.3"}""", store.toJson(forceString = true))
        val value2 by store.reload(store.toJson()).property("key", default = 5.5)
        assertEquals(2.3, value2, 0.0)
    }

    @Test
    fun testListItemValueProperty() {
        var value by store.property("key", TestIdItem.values(), defaultIndex = 1)
        assertEquals(Second, value)
        value = Third
        assertEquals("""{"key":"3"}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", values(), default = First)
        assertEquals(Third, value2)
    }

    @Test
    fun testListValueProperty() {
        var value by store.property("key", TestIdItem.values(), default = listOf(First))
        assertEquals(null, store.get("key"))
        value = listOf(First, Third)
        assertEquals("""{"key":"1,3"}""", store.toJson())
        val value2 by store.reload(store.toJson())
            .property("key", values(), default = listOf(Second))
        assertEquals(listOf(First, Third), value2)
    }

    @Test
    fun testJsonTypeValueProperty() {
        val value by store.property<TestStringJsonType>("key")
        assertEquals("""{}""", store.toJson())
        assertEquals("string", value.string)
        assertNull(value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "new string"
        value.string = newString
        assertEquals("""{"key":{"stringId":"new string"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        val value2 by store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonTypeValuePropertyDefault() {
        val value by store.property("key", TestStringJsonType()
            .apply { string = "string 2" })
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value.string)
        assertEquals(null, value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "string 3"
        value.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        val value2 by store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonListValueProperty() {
        val property = store.property<TestStringJsonType>("key", mutableListOf())
        assertEquals("""{}""", store.toJson())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType().apply { lateString = "string" })
        property.save()
        assertEquals("""{"key":[{},{},{"lateStringId":"string"}]}""", store.toJson())
        property.value.last().lateString = "new string"
        property.save()

        val value by store.reload(store.toJson()).property<TestStringJsonType>("key", listOf())
        assertEquals("new string", value.last().lateString)
    }
}
```
```kotlin
class ValueStorePropertyTest {

    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var value by store.property("key", default = "initial")
        assertEquals("initial", value)
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = "")
        assertEquals("new value", value2)
    }

    @Test
    fun testBooleanProperty() {
        var value by store.property("key", default = false)
        assertEquals(false, value)
        value = true
        assertEquals("""{"key":true}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = false)
        assertEquals(true, value2)
    }

    @Test
    fun testIntProperty() {
        var value by store.property("key", default = 5)
        assertEquals(5, value)
        value = 345
        assertEquals("""{"key":345}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = 10)
        assertEquals(345, value2)
    }

    @Test
    fun testFloatProperty() {
        var value by store.property("key", default = 1.5f)
        assertEquals(1.5f, value)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", default = 542f)
        assertEquals(2.5f, value2)
    }

    @Test
    fun testDoubleProperty() {
        var value by store.property("key", default = 1.5)
        assertEquals(1.5, value, 0.0)
        value = 2.3
        assertEquals("""{"key":"2.3"}""", store.toJson(forceString = true))
        val value2 by store.reload(store.toJson()).property("key", default = 5.5)
        assertEquals(2.3, value2, 0.0)
    }

    @Test
    fun testListItemValueProperty() {
        var value by store.property("key", TestIdItem.values(), defaultIndex = 1)
        assertEquals(Second, value)
        value = Third
        assertEquals("""{"key":"3"}""", store.toJson())
        val value2 by store.reload(store.toJson()).property("key", values(), default = First)
        assertEquals(Third, value2)
    }

    @Test
    fun testListValueProperty() {
        var value by store.property("key", TestIdItem.values(), default = listOf(First))
        assertEquals(null, store.get("key"))
        value = listOf(First, Third)
        assertEquals("""{"key":"1,3"}""", store.toJson())
        val value2 by store.reload(store.toJson())
            .property("key", values(), default = listOf(Second))
        assertEquals(listOf(First, Third), value2)
    }

    @Test
    fun testJsonTypeValueProperty() {
        val value by store.property<TestStringJsonType>("key")
        assertEquals("""{}""", store.toJson())
        assertEquals("string", value.string)
        assertNull(value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "new string"
        value.string = newString
        assertEquals("""{"key":{"stringId":"new string"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        val value2 by store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonTypeValuePropertyDefault() {
        val value by store.property("key", TestStringJsonType()
            .apply { string = "string 2" })
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value.string)
        assertEquals(null, value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "string 3"
        value.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        val value2 by store.reload(store.toJson()).property<TestStringJsonType>("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonListValueProperty() {
        val property = store.property<TestStringJsonType>("key", mutableListOf())
        assertEquals("""{}""", store.toJson())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType())
        property.value.add(TestStringJsonType().apply { lateString = "string" })
        property.save()
        assertEquals("""{"key":[{},{},{"lateStringId":"string"}]}""", store.toJson())
        property.value.last().lateString = "new string"
        property.save()

        val value by store.reload(store.toJson()).property<TestStringJsonType>("key", listOf())
        assertEquals("new string", value.last().lateString)
    }
}
```

## Renetik Android - Libraries
#### [https://github.com/renetik/renetik-android-core](https://github.com/renetik/renetik-android-core/) ➜ [Documentation](https://renetik.github.io/renetik-android-core/)
#### [https://github.com/renetik/renetik-android-json](https://github.com/renetik/renetik-android-json/) ➜ [Documentation](https://renetik.github.io/renetik-android-json/)
#### [https://github.com/renetik/renetik-android-event](https://github.com/renetik/renetik-android-event/) ➜ [Documentation](https://renetik.github.io/renetik-android-event/)
#### [https://github.com/renetik/renetik-android-store](https://github.com/renetik/renetik-android-store/) ➜ [Documentation](https://renetik.github.io/renetik-android-store/)
#### [https://github.com/renetik/renetik-android-preset](https://github.com/renetik/renetik-android-preset/) ➜ [Documentation](https://renetik.github.io/renetik-android-preset/)
#### [https://github.com/renetik/renetik-android-framework](https://github.com/renetik/renetik-android-framework/) ➜ [Documentation](https://renetik.github.io/renetik-android-framework/)
