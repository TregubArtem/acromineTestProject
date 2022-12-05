package com.tregub.acromine.feature.data.source.remote

import com.tregub.acromine.feature.data.model.AcronymDefinition
import com.tregub.acromine.feature.data.model.AcronymWithDefinitions

/**
 * Test implementation of [AcronymsApi] that provides different result for different acronyms:
 * * [AcronymsApiTestImpl.ACRONYM_WITH_DEFINITIONS_NAME] -
 *  one instance of [AcronymWithDefinitions] which includes exactly one definition
 * * [AcronymsApiTestImpl.ACRONYM_WITHOUT_DEFINITIONS_NAME] - empty list without definitions
 * * [AcronymsApiTestImpl.ACRONYM_WITH_CRASH_NAME] - throws general exception
 */
class AcronymsApiTestImpl : AcronymsApi {

    companion object {

        const val DEFINITION_NAME: String = "DEFINITION"
        const val DEFINITION_FREQUENCY: Int = 6668
        const val DEFINITION_YEAR: Int = 2022
        val DEFINITION_VARIATIONS: List<AcronymDefinition> = listOf()

        const val ACRONYM_WITH_DEFINITIONS_NAME: String = "ACRONYM_WITH_DEFINITIONS"
        val ACRONYM_DEFINITIONS: List<AcronymDefinition> = listOf(
            AcronymDefinition(
                name = DEFINITION_NAME,
                frequency = DEFINITION_FREQUENCY,
                year = DEFINITION_YEAR,
                variations = DEFINITION_VARIATIONS,
            ),
        )
        const val ACRONYM_WITHOUT_DEFINITIONS_NAME: String = "ACRONYM_WITHOUT_DEFINITIONS"
        const val ACRONYM_WITH_CRASH_NAME: String = "ACRONYM_WITH_CRASH"

        const val ACRONYMS_COUNT: Int = 1
    }

    override suspend fun getDefinitions(acronym: String): List<AcronymWithDefinitions> =
        when (acronym) {
            ACRONYM_WITH_DEFINITIONS_NAME -> {
                listOf(
                    AcronymWithDefinitions(
                        name = ACRONYM_WITH_DEFINITIONS_NAME,
                        definitions = ACRONYM_DEFINITIONS,
                    )
                )
            }
            ACRONYM_WITHOUT_DEFINITIONS_NAME -> {
                emptyList()
            }
            ACRONYM_WITH_CRASH_NAME -> {
                throw Exception("Crash for testing")
            }
            else -> {
                throw IllegalStateException("Unknown case")
            }
        }
}
