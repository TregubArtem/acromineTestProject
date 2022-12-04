package com.tregub.acromine.screen

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tregub.acromine.feature.data.AcronymsRepository
import com.tregub.acromine.feature.dependency.DiConstant
import com.tregub.acromine.screen.data.AcronymDefinitionMapper
import com.tregub.acromine.screen.data.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Business logic and state holder for [MainActivity]
 *
 * @param repository source of acronyms definitions
 * @param definitionMapper used to convert data model to UI model
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AcronymsRepository,
    private val definitionMapper: AcronymDefinitionMapper,
) : ViewModel() {

    companion object {

        /**
         * Used to wait before delivering the final view state in [actualAcronym]
         */
        @TestOnly
        @VisibleForTesting
        const val DEBOUNCE_DELAY_MS: Long = 1_000L
    }

    /**
     * Used to manage entered acronyms on a subscription basis
     */
    private val actualAcronym: MutableStateFlow<String> = MutableStateFlow("")

    /**
     * Changeable holder for the actual [MainViewState] instance
     */
    private val _viewState: MutableStateFlow<MainViewState> =
        MutableStateFlow(MainViewState.Success(data = emptyList()))

    /**
     * Read-only holder for the actual [MainViewState] instance
     */
    val viewState: StateFlow<MainViewState> = _viewState.asStateFlow()

    /**
     * Replacing the init block to call long-lasting operations
     *
     * @param nonUiContext where the subscription to [actualAcronym] should be done
     */
    @Inject
    @TestOnly
    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun onNonUiContextReady(
        @DiConstant.CoroutineContextNonUI
        nonUiContext: CoroutineContext,
    ) {
        Timber.v("onNonUiContextReady")
        viewModelScope.launch(nonUiContext) {
            actualAcronym
                // shows the loading status before the actual fetch
                .onEach { _viewState.value = MainViewState.Loading }
                // converts acronym to the expected form
                .map { acronym -> acronym.trim() }
                // trying to successfully get acronym definitions,
                //  if that fails, it wraps the unexpected error as a state
                .map { acronym ->
                    Timber.v("\t getDefinitions $acronym")
                    try {
                        repository
                            .getDefinitions(acronym.trim())
                            .map(definitionMapper::toViewState)
                            .let { data -> MainViewState.Success(data = data) }
                    } catch (e: Exception) {
                        Timber.e(e)
                        MainViewState.Error(message = e.message ?: "Unknown error")
                    }
                }
                // waiting some time before delivering final view state
                .debounce(DEBOUNCE_DELAY_MS)
                // passes view state to observers
                .collect(_viewState::emit)

            Timber.e("acronymsInput collection end")
        }
    }

    /**
     * Makes a request for definitions related to [acronym]
     *
     * @param acronym abbreviation for which definitions should be provided
     */
    fun getDefinitions(acronym: String) {
        Timber.v("getDefinitions $acronym")
        actualAcronym.value = acronym
    }
}
