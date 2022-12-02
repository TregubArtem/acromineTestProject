package com.tregub.acromine.feature.dependency

import javax.inject.Qualifier

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
}
