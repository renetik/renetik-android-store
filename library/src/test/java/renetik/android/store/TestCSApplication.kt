package renetik.android.store

import androidx.appcompat.app.AppCompatActivity
import renetik.android.core.base.CSApplication

class TestCSApplication : CSApplication<AppCompatActivity>() {
    override val activityClass = AppCompatActivity::class
}