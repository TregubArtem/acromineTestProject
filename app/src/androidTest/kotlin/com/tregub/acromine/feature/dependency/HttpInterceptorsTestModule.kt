package com.tregub.acromine.feature.dependency

import com.tregub.acromine.feature.data.source.remote.AcronymsApiTestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.Interceptor

/**
 * Defines test interceptors for HTTP requests
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HttpInterceptorsModule::class],
)
class HttpInterceptorsTestModule {

    @DiConstant.HttpInterceptors
    @Provides
    fun getHttpInterceptors(): List<Interceptor> =
        listOf(AcronymsApiTestInterceptor())
}
