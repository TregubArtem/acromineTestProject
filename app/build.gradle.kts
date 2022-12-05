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

        testInstrumentationRunner = "com.tregub.acromine.HiltTestRunner"
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
        arrayOf(
            "androidTest",
            "main",
            "test",
        ).forEach { dir ->
            findByName(dir)?.java?.srcDirs(project.file("src/$dir/kotlin"))
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-opt-in=kotlinx.coroutines.FlowPreview",
        )
    }
    buildFeatures.dataBinding = true
    compileOptions.encoding = "UTF-8"
    packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {
    arrayOf(
        "androidx.core:core-ktx:1.9.0",
        "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1",
        "androidx.appcompat:appcompat:1.5.1",
        "com.google.android.material:material:1.7.0",
        "androidx.constraintlayout:constraintlayout:2.1.4",
        "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0",
        "androidx.recyclerview:recyclerview:1.2.1",
        "com.google.dagger:hilt-android:${Versions.HILT}",
        "androidx.activity:activity-ktx:1.6.1",
        "com.jakewharton.timber:timber:5.0.1",
        "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}",
        "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}",
        "com.squareup.okhttp3:okhttp:${Versions.OK_HTTP}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.OK_HTTP}",
        "com.squareup.moshi:moshi:${Versions.MOSHI}",
    ).forEach { dependency ->
        implementation(dependency)
    }
    arrayOf(
        "com.google.dagger:hilt-android-compiler:${Versions.HILT}",
        "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}",
    ).forEach { dependency ->
        kapt(dependency)
    }
    testImplementation("junit:junit:4.13.2")
    arrayOf(
        "androidx.test.ext:junit:1.1.4",
        "androidx.test.espresso:espresso-core:3.5.0",
        "com.google.dagger:hilt-android-testing:${Versions.HILT}",
    ).forEach { dependency ->
        androidTestImplementation(dependency)
    }
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${Versions.HILT}")
}
