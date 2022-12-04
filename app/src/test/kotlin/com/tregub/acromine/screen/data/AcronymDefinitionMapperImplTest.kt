package com.tregub.acromine.screen.data

import com.tregub.acromine.feature.data.model.AcronymDefinition
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AcronymDefinitionMapperImplTest {

    private companion object {

        const val DEFINITION_NAME: String = "DEFINITION"
        const val DEFINITION_FREQUENCY: Int = 6668
        const val DEFINITION_YEAR: Int = 2022
        val DEFINITION_VARIATIONS: List<AcronymDefinition> = listOf()
    }

    private lateinit var mapper: AcronymDefinitionMapper

    @Before
    fun createAcronymDefinitionMapper() {
        mapper = AcronymDefinitionMapperImpl()
    }

    @Test
    fun toViewState() {
        val definition = AcronymDefinition(
            name = DEFINITION_NAME,
            frequency = DEFINITION_FREQUENCY,
            year = DEFINITION_YEAR,
            variations = DEFINITION_VARIATIONS,
        )
        val state: AcronymDefinitionViewState = mapper.toViewState(definition)

        Assert.assertEquals(DEFINITION_NAME, state.name)
        Assert.assertEquals(DEFINITION_FREQUENCY, state.frequency)
        Assert.assertEquals(DEFINITION_YEAR, state.year)
        Assert.assertEquals(DEFINITION_VARIATIONS.size, state.variationsCount)
    }
}
