package com.tregub.acromine.feature.data.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AcronymWithDefinitionsTest {

    private companion object {

        const val ACRONYM_NAME: String = "ACRONYM"
        val ACRONYM_DEFINITIONS: List<AcronymDefinition> = listOf()
    }

    private lateinit var acronym: AcronymWithDefinitions

    @Before
    fun createAcronymWithDefinitions() {
        acronym = AcronymWithDefinitions(
            name = ACRONYM_NAME,
            definitions = ACRONYM_DEFINITIONS,
        )
    }

    @Test
    fun getName() {
        Assert.assertEquals(ACRONYM_NAME, acronym.name)
    }

    @Test
    fun getDefinitions() {
        Assert.assertEquals(ACRONYM_DEFINITIONS, acronym.definitions)
    }
}
