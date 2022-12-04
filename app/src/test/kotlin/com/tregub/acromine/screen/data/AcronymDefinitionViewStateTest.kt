package com.tregub.acromine.screen.data

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AcronymDefinitionViewStateTest {

    private companion object {

        const val DEFINITION_NAME: String = "DEFINITION"
        const val DEFINITION_FREQUENCY: Int = 6668
        const val DEFINITION_YEAR: Int = 2022
        const val DEFINITION_VARIATIONS_COUNT: Int = 1
    }

    private lateinit var definition: AcronymDefinitionViewState

    @Before
    fun createAcronymDefinition() {
        definition = AcronymDefinitionViewState(
            name = DEFINITION_NAME,
            frequency = DEFINITION_FREQUENCY,
            year = DEFINITION_YEAR,
            variationsCount = DEFINITION_VARIATIONS_COUNT,
        )
    }

    @Test
    fun getName() {
        Assert.assertEquals(DEFINITION_NAME, definition.name)
    }

    @Test
    fun getFrequency() {
        Assert.assertEquals(DEFINITION_FREQUENCY, definition.frequency)
    }

    @Test
    fun getYear() {
        Assert.assertEquals(DEFINITION_YEAR, definition.year)
    }

    @Test
    fun getVariationsCount() {
        Assert.assertEquals(DEFINITION_VARIATIONS_COUNT, definition.variationsCount)
    }
}
