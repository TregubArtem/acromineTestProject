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
    buildFeatures.compose = true
    kotlinOptions.jvmTarget = "1.8"
    compileOptions.encoding = "UTF-8"
    composeOptions.kotlinCompilerExtensionVersion = Versions.COMPOSE
    packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {
    arrayOf(
        "androidx.core:core-ktx:1.7.0",
        "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1",
        "androidx.activity:activity-compose:${Versions.COMPOSE}",
        "androidx.compose.ui:ui:${Versions.COMPOSE}",
        "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}",
        "androidx.compose.material:material:1.1.1",
        "com.google.dagger:hilt-android:${Versions.HILT}",
        "androidx.hilt:hilt-navigation-compose:1.0.0",
    ).forEach { dependency ->
        implementation(dependency)
    }
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT}")
    arrayOf(
        "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}",
        "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}",
    ).forEach { dependency ->
        debugImplementation(dependency)
    }
    testImplementation("junit:junit:4.13.2")
    arrayOf(
        "androidx.test.ext:junit:1.1.4",
        "androidx.test.espresso:espresso-core:3.5.0",
        "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}",
    ).forEach { dependency ->
        androidTestImplementation(dependency)
    }
}
