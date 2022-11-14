package eu.codeplumbers.kittens_kt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KittenApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: KittenApp
            private set
    }
}