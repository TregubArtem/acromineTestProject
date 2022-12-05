package com.tregub.acromine.feature.dependency

import com.squareup.moshi.Moshi
import com.tregub.acromine.feature.data.source.remote.AcronymsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 * The definition of initialization for objects
 *  that are available while the application is available
 */
@Module
@InstallIn(SingletonComponent::class)
class ProvidesModule {

    @DiConstant.ApiBaseUrl
    @Provides
    fun getApiBaseUrl(): String =
        "http://www.nactem.ac.uk/software/acromine/"

    @Provides
    fun getHttpClient(
        @DiConstant.HttpInterceptors
        interceptors: List<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient =
        OkHttpClient.Builder()
            .also { client -> interceptors.forEach(client::addInterceptor) }
            .build()

    @Provides
    fun getRemoteDataConverterFactory(): Converter.Factory =
        MoshiConverterFactory.create(Moshi.Builder().build())

    @Singleton
    @Provides
    fun getAcronymsApi(
        @DiConstant.ApiBaseUrl
        baseUrl: String,
        converterFactory: Converter.Factory,
        httpClient: OkHttpClient,
    ): AcronymsApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(AcronymsApi::class.java)

    @DiConstant.AcronymsDefinitionsCacheSize
    @Provides
    fun getAcronymsDefinitionsCacheSize(): Int =
        4 * 1024

    @DiConstant.CoroutineContextIO
    @Provides
    fun getCoroutineContextIO(): CoroutineContext =
        Dispatchers.IO

    @DiConstant.CoroutineContextNonUI
    @Provides
    fun getCoroutineContextNonUI(): CoroutineContext =
        Dispatchers.Default
}
