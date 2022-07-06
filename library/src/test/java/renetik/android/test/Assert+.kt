package renetik.android.test

import org.junit.Assert

fun assertThrows(function: () -> Unit) {
    Assert.assertThrows(Exception::class.java, function)
}

fun assertFail(function: () -> Unit) = assertThrows(function)