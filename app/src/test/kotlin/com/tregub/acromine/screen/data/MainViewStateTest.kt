package com.tregub.acromine.screen.data

import org.junit.Assert
import org.junit.Test

class MainViewStateTest {

    private companion object {

        const val DEFINITION_NAME: String = "DEFINITION"
        const val DEFINITION_FREQUENCY: Int = 6668
        const val DEFINITION_YEAR: Int = 2022
        const val DEFINITION_VARIATIONS_COUNT: Int = 1
    }

    @Test
    fun successStateWithoutValues() {
        val expected: List<AcronymDefinitionViewState> = emptyList()
        val state = MainViewState.Success(data = expected)

        Assert.assertEquals(expected, state.data)
    }

    @Test
    fun successStateWithValues() {
        val expected: List<AcronymDefinitionViewState> = listOf(
            AcronymDefinitionViewState(
                name = DEFINITION_NAME,
                frequency = DEFINITION_FREQUENCY,
                year = DEFINITION_YEAR,
                variationsCount = DEFINITION_VARIATIONS_COUNT,
            ),
        )
        val state = MainViewState.Success(data = expected)
        Assert.assertEquals(expected, state.data)

        val definition: AcronymDefinitionViewState = state.data.first()
        Assert.assertEquals(DEFINITION_NAME, definition.name)
        Assert.assertEquals(DEFINITION_FREQUENCY, definition.frequency)
        Assert.assertEquals(DEFINITION_YEAR, definition.year)
        Assert.assertEquals(DEFINITION_VARIATIONS_COUNT, definition.variationsCount)
    }

    @Test
    fun loadingState() {
        val actual: MainViewState = MainViewState.Loading
        Assert.assertEquals(MainViewState.Loading, actual)
    }

    @Test
    fun errorState() {
        val expected = "Error message"
        val state = MainViewState.Error(message = expected)

        Assert.assertEquals(expected, state.message)
    }
}
