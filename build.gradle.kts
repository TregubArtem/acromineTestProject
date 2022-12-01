buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
    }
}

plugins {
    id("com.android.application").version(Versions.ANDROID).apply(false)
    id("com.android.library").version(Versions.ANDROID).apply(false)
    id("org.jetbrains.kotlin.android").version(Versions.KOTLIN).apply(false)
}

tasks.register("clean").configure {
    delete("build")
}
