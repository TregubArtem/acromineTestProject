package com.tregub.acromine.feature.data.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AcronymDefinitionTest {

    private companion object {

        const val DEFINITION_NAME: String = "DEFINITION"
        const val DEFINITION_FREQUENCY: Int = 6668
        const val DEFINITION_YEAR: Int = 2022
        val DEFINITION_VARIATIONS: List<AcronymDefinition> = listOf()
    }

    private lateinit var definition: AcronymDefinition

    @Before
    fun createAcronymDefinition() {
        definition = AcronymDefinition(
            name = DEFINITION_NAME,
            frequency = DEFINITION_FREQUENCY,
            year = DEFINITION_YEAR,
            variations = DEFINITION_VARIATIONS,
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
    fun getVariations() {
        Assert.assertEquals(DEFINITION_VARIATIONS, definition.variations)
    }
}
