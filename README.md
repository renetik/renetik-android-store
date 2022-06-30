<!---Header--->
[![Android CI](https://github.com/renetik/renetik-android-store/workflows/Android%20CI/badge.svg)
](https://github.com/renetik/renetik-android-store/actions/workflows/android.yml)

# Renetik Android - Store

#### [https://github.com/renetik/renetik-android-store](https://github.com/renetik/renetik-android-store/)

#### [Documentation](https://renetik.github.io/renetik-android-store/)

Framework to enjoy, improve and speed up your application development while writing readable code.
Used as library in music production and performance app Renetik Instruments www.renetik.com as well
as in other projects.


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
