package com.tregub.acromine.feature.dependency

import com.tregub.acromine.feature.data.AcronymsRepository
import com.tregub.acromine.feature.data.AcronymsRepositoryImpl
import com.tregub.acromine.feature.data.source.local.AcronymsCache
import com.tregub.acromine.feature.data.source.local.AcronymsCacheImpl
import com.tregub.acromine.screen.data.AcronymDefinitionMapper
import com.tregub.acromine.screen.data.AcronymDefinitionMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The definition of initialization for objects
 *  that are available while the application is available
 */
@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface BindsModule {

    @Singleton
    @Binds
    fun getAcronymsCache(
        impl: AcronymsCacheImpl
    ): AcronymsCache

    @Binds
    fun getAcronymsRepository(
        impl: AcronymsRepositoryImpl
    ): AcronymsRepository

    @Binds
    fun getAcronymDefinitionMapper(
        impl: AcronymDefinitionMapperImpl
    ): AcronymDefinitionMapper
}
