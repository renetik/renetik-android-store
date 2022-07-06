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

## Examples
```kotlin
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
```

```kotlin
class ValueStorePropertyTest {
    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var eventCount = 0
        var value: String by store.property("key", default = "initial") { eventCount += 1 }
        assertEquals("initial", value)
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())

        store.reload("""{"key":"new value 2"}""")
        val value2: String by store.property("key", default = "")

        assertEquals("new value 2", value)
        assertEquals("new value 2", value2)
        assertEquals(2, eventCount)
    }

    @Test
    fun testBooleanProperty() {
        var value: Boolean by store.property("key", default = false)
        assertEquals(false, value)
        value = true
        assertEquals("""{"key":true}""", store.toJson())

        store.reload(store.toJson())
        val value2: Boolean by store.property("key", default = false)
        assertEquals(true, value2)
    }

    @Test
    fun testIntProperty() {
        var value: Int by store.property("key", default = 5)
        assertEquals(5, value)
        value = 345
        assertEquals("""{"key":345}""", store.toJson())

        store.reload(store.toJson())
        val value2: Int by store.property("key", default = 10)
        assertEquals(345, value2)
    }

    @Test
    fun testFloatProperty() {
        var eventCount = 0
        var value: Float by store.property("key", default = 1.5f) { eventCount += 1 }
        assertEquals(1.5f, value)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())

        store.reload("""{"key":2.3}""")
        assertEquals(2, eventCount)

        val value2: Float by store.property("key", default = 542f)
        assertEquals(2.3f, value2)
    }

    @Test
    fun testDoubleProperty() {
        var eventCount = 0
        var value: Double by store.property("key", default = 1.5) { eventCount += 1 }
        assertEquals(1.5, value, 0.0)
        value = 2.3
        assertEquals("""{"key":"2.3"}""", store.toJson(forceString = true))

        store.reload(store.toJson())
        val value2: Double by store.property("key", default = 5.5)
        assertEquals(2.3, value2, 0.0)
        assertEquals(1, eventCount)
    }

    @Test
    fun testListItemValueProperty() {
        var value: TestIdItem by store.property("key",
            TestIdItems, defaultIndex = 1)
        assertEquals(Second, value)
        value = Third
        assertEquals("""{"key":"id3"}""", store.toJson())

        store.reload(store.toJson())
        val value2: TestIdItem by store.property("key",
            TestIdItems, default = First)
        assertEquals(Third, value2)
    }

    @Test
    fun testListValueProperty() {
        var value: List<TestIdItem> by store.property("key",
            TestIdItems, default = listOf(First))
        assertEquals(null, store.get("key"))
        value = listOf(First, Third)
        assertEquals("""{"key":"id1,id3"}""", store.toJson())

        store.reload(store.toJson())
        val value2: List<TestIdItem> by store.property("key",
            TestIdItems, default = listOf(Second))
        assertEquals(listOf(First, Third), value2)
    }

    @Test
    fun testJsonValueProperty() {
        val value: TestStringJsonType by store.property("key")
        assertEquals("""{}""", store.toJson())
        assertEquals("string", value.string)
        assertNull(value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "new string"
        value.string = newString
        assertEquals("""{"key":{"stringId":"new string"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        store.reload(store.toJson())
        val value2: TestStringJsonType by store.property("key")
        assertEquals(newString, value2.string)
        assertEquals(newString, value2.nullString)
        assertEquals(newString, value2.lateString)
    }

    @Test
    fun testJsonValuePropertyDefault() {
        val value: TestStringJsonType by store.property("key",
            TestStringJsonType(string = "string 2"))
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value.string)
        assertEquals(null, value.nullString)
        assertThrows(Exception::class.java) { value.lateString }

        val newString = "string 3"
        value.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value.nullString = newString
        value.lateString = newString

        store.reload(store.toJson())
        val value2: TestStringJsonType by store.property("key")
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
        property.value.add(TestStringJsonType(lateString = "string"))
        property.save()
        assertEquals("""{"key":[{},{},{"lateStringId":"string"}]}""", store.toJson())
        property.value.last().lateString = "new string"
        property.save()

        store.reload(store.toJson())
        val value: List<TestStringJsonType> by store.property("key", listOf())
        assertEquals("new string", value.last().lateString)
    }
}
```
```kotlin
class NullStorePropertyTest {
    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var value: String? by store.nullStringProperty("key", "initial")
        value = "new value"
        assertEquals("""{"key":"new value"}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals("initial", value)

        store.reload(store.toJson())
        val value2: String? by store.nullStringProperty("key")
        assertNull(value2)
    }

    @Test
    fun testBooleanProperty() {
        CSJson.forceStringInJson = true
        var value: Boolean? by store.nullBoolProperty("key")
        assertNull(value)
        value = true
        assertEquals("""{"key":"true"}""", store.toJson())

        store.reload(store.toJson())
        val value2: Boolean? by store.nullBoolProperty("key")
        assertEquals(true, value2)
        CSJson.forceStringInJson = false
    }

    @Test
    fun testIntProperty() {
        var value: Int? by store.nullIntProperty("key", 5)
        value = 10
        assertEquals("""{"key":10}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(5, value)

        store.reload(store.toJson())
        val value2: Int? by store.nullIntProperty("key")
        assertNull(value2)
    }

    @Test
    fun testFloatProperty() {
        var value: Float? by store.nullFloatProperty("key", 1.5f)
        value = 2.5f
        assertEquals("""{"key":2.5}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(1.5f, value)

        store.reload(store.toJson())
        val value2: Float? by store.nullFloatProperty("key")
        assertNull(value2)
    }

    @Test
    fun testListItemValueProperty() {
        var value: TestIdItem? by store.nullListItemProperty("key",
            TestIdItems, defaultIndex = 1)
        value = First
        assertEquals("""{"key":"id1"}""", store.toJson())
        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(Second, value)

        store.reload(store.toJson())
        val value2: TestIdItem? by store.nullListItemProperty("key", TestIdItems)
        assertNull(value2)
    }

    @Test
    fun testJsonProperty() {
        val default = TestStringJsonType(string = "string 2")
        var value: TestStringJsonType? by store.nullJsonProperty("key", default)
        assertEquals("""{}""", store.toJson())
        assertEquals("string 2", value!!.string)
        assertEquals(null, value!!.nullString)
        assertThrows(Exception::class.java) { value!!.lateString }

        val newString = "string 3"
        value!!.string = newString
        assertEquals("""{"key":{"stringId":"string 3"}}""", store.toJson())
        value!!.nullString = newString
        value!!.lateString = newString

        value = null
        assertEquals("""{}""", store.toJson())
        assertEquals(default, value)
        assertNotSame(default, value)

        store.reload(store.toJson())
        val value2: TestStringJsonType? by store.nullJsonProperty("key")
        assertNull(value2)
    }
}
```
```kotlin
class LateStorePropertyTest {
    private val store: CSStore = CSJsonObjectStore()

    @Test
    fun testStringProperty() {
        var eventCount = 0
        var newValue: String? = null
        var value: String by store.lateStringProperty("key") {
            newValue = it
            eventCount += 1
        }
        value = "value 1"

        assertEquals("""{"key":"value 1"}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":"value 2"}""")
        assertEquals(newValue, "value 2")
        assertEquals(2, eventCount)
    }


    @Test
    fun testBooleanProperty() {
        var newValue: Boolean? = null
        var value: Boolean by store.lateBoolProperty("key") { newValue = it }
        value = false

        assertEquals("""{"key":false}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":true}""")
        assertTrue(newValue!!)
    }

    @Test
    fun testIntProperty() {
        forceStringInJson = true
        var newValue: Int? = null
        var value: Int by store.lateIntProperty("key") { newValue = it }
        value = 34

        assertEquals("""{"key":"34"}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":"56"}""")
        assertEquals(56, newValue)
        forceStringInJson = false
    }

    @Test
    fun testListItemValueProperty() {
        var eventCount = 0
        var newValue: TestIdItem? = null
        var value: TestIdItem by store.lateListItemProperty("key", TestIdItems) {
            newValue = it
            eventCount += 1
        }
        value = Fourth

        assertEquals("""{"key":"id4"}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":"id2"}""")
        assertEquals(newValue, Second)
        assertEquals(2, eventCount)
    }

    @Test
    fun testJsonProperty() {
        var newValue: TestStringJsonType? = null
        var value: TestStringJsonType by store.lateJsonProperty("key") { newValue = it }
        value = TestStringJsonType()

        assertEquals("""{"key":{}}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":{"stringId":"string 1"}}""")
        assertEquals(newValue, TestStringJsonType().apply { string = "string 1" })
    }

    @Test
    fun testJsonListValueProperty() {
        var newValue: List<TestStringJsonType>? = null
        var value: List<TestStringJsonType> by store.lateJsonListProperty("key") { newValue = it }
        value = listOf(TestStringJsonType(), TestStringJsonType(lateString = "string 1"))

        assertEquals("""{"key":[{},{"lateStringId":"string 1"}]}""", store.toJson())
        assertEquals(newValue, value)

        store.reload("""{"key":[{"nullStringId":"string 2"},{}]}""")
        assertEquals(newValue,
            listOf(TestStringJsonType(nullString = "string 2"), TestStringJsonType()))
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