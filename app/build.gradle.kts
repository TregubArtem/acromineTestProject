plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            versionNameSuffix = ".debug"
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }
    sourceSets {
        listOf(
            "androidTest",
            "main",
            "test",
        ).forEach { dir ->
            findByName(dir)?.java?.srcDirs(project.file("src/$dir/kotlin"))
        }
    }
    buildFeatures.dataBinding = true
    kotlinOptions.jvmTarget = "1.8"
    compileOptions.encoding = "UTF-8"
    packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {
    arrayOf(
        "androidx.core:core-ktx:1.9.0",
        "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1",
        "androidx.appcompat:appcompat:1.5.1",
        "com.google.android.material:material:1.7.0",
        "androidx.constraintlayout:constraintlayout:2.1.4",
        "com.google.dagger:hilt-android:${Versions.HILT}",
        "com.jakewharton.timber:timber:5.0.1",
    ).forEach { dependency ->
        implementation(dependency)
    }
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")
    testImplementation("junit:junit:4.13.2")
    arrayOf(
        "androidx.test.ext:junit:1.1.4",
        "androidx.test.espresso:espresso-core:3.5.0",
    ).forEach { dependency ->
        androidTestImplementation(dependency)
    }
}
