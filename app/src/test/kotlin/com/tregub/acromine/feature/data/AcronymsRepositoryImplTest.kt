package com.tregub.acromine.feature.data

import com.tregub.acromine.feature.data.model.AcronymDefinition
import com.tregub.acromine.feature.data.source.local.AcronymsCacheTestImpl
import com.tregub.acromine.feature.data.source.remote.AcronymsApiTestImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AcronymsRepositoryImplTest {

    private lateinit var repository: AcronymsRepository

    @Before
    fun createAcronymsRepository() {
        repository = AcronymsRepositoryImpl(
            nonUiContext = Dispatchers.Unconfined,
            ioContext = Dispatchers.Unconfined,
            api = AcronymsApiTestImpl(),
            cache = AcronymsCacheTestImpl(),
        )
    }

    @Test
    fun getDefinitions() {
        val definitions: List<AcronymDefinition> = runBlocking {
            repository.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITH_DEFINITIONS_NAME)
        }
        Assert.assertEquals(AcronymsApiTestImpl.ACRONYM_DEFINITIONS, definitions)

        val definition: AcronymDefinition = definitions.first()
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_NAME, definition.name)
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_FREQUENCY, definition.frequency)
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_YEAR, definition.year)
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_VARIATIONS, definition.variations)
    }

    @Test
    fun getNoDefinitions() {
        val definitions: List<AcronymDefinition> = runBlocking {
            repository.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITHOUT_DEFINITIONS_NAME)
        }
        Assert.assertTrue(definitions.isEmpty())
    }
}
