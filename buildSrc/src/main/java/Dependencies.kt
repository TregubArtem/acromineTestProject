@file:Suppress("unused", "MemberVisibilityCanBePrivate")

/**
 * Constants that are used to define key project configurations
 */
object AppConfig {

    const val APPLICATION_ID: String = "com.tregub.acromine"
    const val VERSION_CODE: Int = 1
    const val VERSION_NAME = "0"

    const val COMPILE_SDK: Int = 33
    const val MIN_SDK: Int = 25
    const val TARGET_SDK: Int = COMPILE_SDK
}

/**
 * Constants that are used to determine the versions of linked libraries
 */
object Versions {

    const val ANDROID: String = "7.3.1"
    const val HILT: String = "2.44.2"
    const val KOTLIN: String = "1.7.10"
    const val COMPOSE: String = "1.3.1"
    const val RETROFIT: String = "2.9.0"
    const val OK_HTTP: String = "4.9.1"
    const val MOSHI: String = "1.14.0"
}
