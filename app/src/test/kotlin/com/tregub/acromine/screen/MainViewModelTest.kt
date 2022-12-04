package com.tregub.acromine.screen

import androidx.lifecycle.viewModelScope
import com.tregub.acromine.feature.data.AcronymsRepositoryImpl
import com.tregub.acromine.feature.data.model.AcronymDefinition
import com.tregub.acromine.feature.data.source.local.AcronymsCacheTestImpl
import com.tregub.acromine.feature.data.source.remote.AcronymsApiTestImpl
import com.tregub.acromine.screen.data.AcronymDefinitionMapperImpl
import com.tregub.acromine.screen.data.AcronymDefinitionViewState
import com.tregub.acromine.screen.data.MainViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun createMainViewModel() {
        viewModel = MainViewModel(
            repository = AcronymsRepositoryImpl(
                nonUiContext = Dispatchers.Unconfined,
                ioContext = Dispatchers.Unconfined,
                api = AcronymsApiTestImpl(),
                cache = AcronymsCacheTestImpl(),
            ),
            definitionMapper = AcronymDefinitionMapperImpl(),
        )
        viewModel.onNonUiContextReady(nonUiContext = Dispatchers.Default)
    }

    @After
    fun releaseMainViewModel() {
        viewModel.viewModelScope.cancel()
    }

    @Test
    fun getDefaultViewState() {
        val expected: MainViewState.Success = MainViewState.Success(data = emptyList())
        val actual: MainViewState = viewModel.viewState.value

        Assert.assertTrue(actual is MainViewState.Success)

        actual as MainViewState.Success
        Assert.assertEquals(expected.data.size, actual.data.size)
    }

    @Test
    fun getLoadingViewState() {
        viewModel.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITH_DEFINITIONS_NAME)

        val expected: MainViewState = MainViewState.Loading
        val actual: MainViewState = runBlocking {
            viewModel.viewState.first { state -> state is MainViewState.Loading }
        }
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getSuccessViewStateWithoutData() {
        viewModel.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITHOUT_DEFINITIONS_NAME)

        val actual: MainViewState = runBlocking {
            delay(MainViewModel.DEBOUNCE_DELAY_MS)
            viewModel.viewState.first { state -> state is MainViewState.Success }
        }
        Assert.assertTrue(actual is MainViewState.Success)

        actual as MainViewState.Success
        Assert.assertTrue(actual.data.isEmpty())
    }

    @Test
    fun getSuccessViewStateWithData() {
        viewModel.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITH_DEFINITIONS_NAME)

        val actual: MainViewState = runBlocking {
            delay(MainViewModel.DEBOUNCE_DELAY_MS)
            viewModel.viewState.first { state -> state is MainViewState.Success }
        }
        Assert.assertTrue(actual is MainViewState.Success)

        actual as MainViewState.Success
        val expectedDefinitions: List<AcronymDefinition> = AcronymsApiTestImpl.ACRONYM_DEFINITIONS
        val actualDefinitions: List<AcronymDefinitionViewState> = actual.data

        Assert.assertEquals(expectedDefinitions.size, actualDefinitions.size)

        val expectedDefinition: AcronymDefinition = expectedDefinitions.first()
        val actualDefinition: AcronymDefinitionViewState = actualDefinitions.first()

        Assert.assertEquals(expectedDefinition.name, actualDefinition.name)
        Assert.assertEquals(expectedDefinition.frequency, actualDefinition.frequency)
        Assert.assertEquals(expectedDefinition.year, actualDefinition.year)
        Assert.assertEquals(expectedDefinition.variations.size, actualDefinition.variationsCount)
    }

    @Test
    fun getErrorViewState() {
        viewModel.getDefinitions(AcronymsApiTestImpl.ACRONYM_WITH_CRASH_NAME)
        val actual: MainViewState = runBlocking {
            viewModel.viewState.first { state -> state is MainViewState.Error }
        }
        Assert.assertTrue(actual is MainViewState.Error)
    }
}
