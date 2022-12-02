package com.tregub.acromine.feature.dependency

import com.squareup.moshi.Moshi
import com.tregub.acromine.feature.data.source.remote.AcronymsApi
import com.tregub.acromine.feature.whenBuildType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

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
}
