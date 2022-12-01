package com.tregub.acromine

import android.app.Application
import android.os.StrictMode
import com.tregub.acromine.feature.whenBuildType
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Root class that system instantiates before any other class when an app's process starts
 */
@HiltAndroidApp
class AcromineApp : Application() {

    override fun onCreate() {
        super.onCreate()

        whenBuildType(
            onRelease = { },
            onDebug = {
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .build()
                    .let(StrictMode::setThreadPolicy)

                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .build()
                    .let(StrictMode::setVmPolicy)

                Timber.plant(Timber.DebugTree())
            },
        )
    }
}
