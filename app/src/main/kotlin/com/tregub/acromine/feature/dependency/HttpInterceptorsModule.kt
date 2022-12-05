package com.tregub.acromine.feature.dependency

import com.tregub.acromine.feature.whenBuildType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Defines interceptors for HTTP requests
 */
@Module
@InstallIn(SingletonComponent::class)
class HttpInterceptorsModule {

    @DiConstant.HttpInterceptors
    @Provides
    fun getHttpInterceptors(): List<Interceptor> {
        val interceptors: MutableList<Interceptor> = mutableListOf()
        whenBuildType(
            onRelease = { /* no interceptors needed */ },
            onDebug = {
                interceptors += HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            },
        )
        return interceptors
    }
}