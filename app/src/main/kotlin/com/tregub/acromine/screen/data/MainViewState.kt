package com.tregub.acromine.screen.data

import com.tregub.acromine.screen.MainActivity

/**
 * The final set of states that [MainActivity] can handle
 */
sealed interface MainViewState {

    /**
     * Represents either an empty initial state or a successful result after data has been fetched
     *
     * @param data a collection of acronym definitions ready to be shown to the user
     */
    class Success(val data: List<AcronymDefinitionViewState>) : MainViewState

    /**
     * Represents the data fetch state
     */
    object Loading : MainViewState

    /**
     * Represents the data fetch error state
     *
     * @param message details to be shown to the user
     */
    class Error(val message: String) : MainViewState
}
