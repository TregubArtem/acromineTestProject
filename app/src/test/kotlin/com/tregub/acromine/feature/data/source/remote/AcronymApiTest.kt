package com.tregub.acromine.feature.data.source.remote

import com.tregub.acromine.feature.data.model.AcronymDefinition
import com.tregub.acromine.feature.data.model.AcronymWithDefinitions
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AcronymApiTest {

    private lateinit var api: AcronymsApi

    @Before
    fun createAcronymsApi() {
        api = AcronymsApiTestImpl()
    }

    @Test
    fun getDefinitions() {
        val definitions: List<AcronymWithDefinitions> = runBlocking {
            api.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITH_DEFINITIONS_NAME)
        }
        Assert.assertEquals(AcronymsApiTestImpl.ACRONYMS_COUNT, definitions.size)

        val acronym: AcronymWithDefinitions = definitions.first()
        Assert.assertEquals(AcronymsApiTestImpl.ACRONYM_WITH_DEFINITIONS_NAME, acronym.name)
        Assert.assertEquals(AcronymsApiTestImpl.ACRONYM_DEFINITIONS, acronym.definitions)

        val definition: AcronymDefinition = acronym.definitions.first()
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_NAME, definition.name)
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_FREQUENCY, definition.frequency)
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_YEAR, definition.year)
        Assert.assertEquals(AcronymsApiTestImpl.DEFINITION_VARIATIONS, definition.variations)
    }

    @Test
    fun getNoDefinitions() {
        val definitions: List<AcronymWithDefinitions> = runBlocking {
            api.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITHOUT_DEFINITIONS_NAME)
        }
        Assert.assertTrue(definitions.isEmpty())
    }
}
