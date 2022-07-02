package renetik.android.store

import renetik.android.core.lang.CSHasId

enum class TestIdItem(override val id: String) : CSHasId {
    First("1"), Second("2"), Third("3"), Fourth("4")
}