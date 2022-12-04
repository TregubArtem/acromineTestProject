package com.tregub.acromine.feature.data.source.local

import com.tregub.acromine.feature.data.model.AcronymDefinition
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AcronymsCacheImplTest {

    private companion object {

        const val CACHE_SIZE: Int = 4

        const val ACRONYM_NAME: String = "ACRONYM"

        const val DEFINITION_NAME: String = "DEFINITION"
        const val DEFINITION_FREQUENCY: Int = 6668
        const val DEFINITION_YEAR: Int = 2022
        val DEFINITION_VARIATIONS: List<AcronymDefinition> = listOf()
    }

    private lateinit var cache: AcronymsCache

    @Before
    fun createAcronymsCache() {
        cache = AcronymsCacheImpl(CACHE_SIZE)
    }

    @Test
    fun getBeforeSet() {
        val definitions: List<AcronymDefinition> = cache[ACRONYM_NAME]
        Assert.assertTrue(definitions.isEmpty())
    }

    @Test
    fun getAfterSet() {
        val expected: List<AcronymDefinition> = createAcronymDefinitions()
        cache[ACRONYM_NAME] = expected
        val actual: List<AcronymDefinition> = cache[ACRONYM_NAME]

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getAfterReachingTheLimit() {
        val unexpected: List<AcronymDefinition> = createAcronymDefinitions()
        cache[ACRONYM_NAME] = unexpected

        (0 until CACHE_SIZE).forEach { i ->
            val definitionName = "$ACRONYM_NAME$i"
            cache[definitionName] = createAcronymDefinitions(definitionName)
        }
        val actual: List<AcronymDefinition> = cache[ACRONYM_NAME]
        Assert.assertNotEquals(unexpected, actual)
    }

    /**
     * @param definitionName same as [AcronymDefinition.name]
     * @return list with exactly one definition
     */
    private fun createAcronymDefinitions(
        definitionName: String = DEFINITION_NAME,
    ): List<AcronymDefinition> =
        listOf(
            AcronymDefinition(
                name = definitionName,
                frequency = DEFINITION_FREQUENCY,
                year = DEFINITION_YEAR,
                variations = DEFINITION_VARIATIONS,
            ),
        )
}
