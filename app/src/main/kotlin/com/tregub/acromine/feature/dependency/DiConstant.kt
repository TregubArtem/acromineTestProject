package com.tregub.acromine.feature.dependency

import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext

/**
 * Helps DI to understand what kind of object is needed
 */
@Qualifier
@Retention
annotation class DiConstant {

    /**
     * Represents the Internet address of the data source
     */
    @Qualifier
    @Retention
    annotation class ApiBaseUrl

    /**
     * Represents a collection of http interceptors
     */
    @Qualifier
    @Retention
    annotation class HttpInterceptors

    /**
     * Represents the maximum number of cached objects that can be used to store acronym definitions
     */
    @Qualifier
    @Retention
    annotation class AcronymsDefinitionsCacheSize

    /**
     * Represents a [CoroutineContext] that can be used for I/O operations.
     */
    @Qualifier
    @Retention
    annotation class CoroutineContextIO

    /**
     * Represents a [CoroutineContext] that implies computations outside the UI thread, but not I/O
     */
    @Qualifier
    @Retention
    annotation class CoroutineContextNonUI
}
