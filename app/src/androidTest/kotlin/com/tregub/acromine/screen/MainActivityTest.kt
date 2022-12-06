package com.tregub.acromine.screen

import androidx.activity.viewModels
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.tregub.acromine.feature.data.source.remote.AcronymsApiTestInterceptor
import com.tregub.acromine.screen.data.MainViewState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
        createAndroidComposeRule()

    @Before
    fun injectDependencies() {
        hiltRule.inject()
    }

    @Test
    fun getDefaultViewState() {
        setAcronymInput("")
        assertDefinitionsItemsCount(0)
    }

    @Test
    fun getLoadingViewState() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)

        composeRule
            .onNodeWithContentDescription("Progress Indication")
            .assertIsDisplayed()
    }

    @Test
    fun getSuccessViewStateWithoutData() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITHOUT_DEFINITIONS_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITHOUT_DEFINITIONS_NAME)

        withViewModel {
            viewState.first { state -> state is MainViewState.Success }
        }
        assertDefinitionsItemsCount(0)
    }

    @Test
    fun getSuccessViewStateWithData() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)

        withViewModel {
            viewState.first { state -> state is MainViewState.Success }
        }
        assertDefinitionsItemsCount(AcronymsApiTestInterceptor.ACRONYM_DEFINITIONS.size)
    }

    @Test
    fun getErrorViewState() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITH_CRASH_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITH_CRASH_NAME)

        withViewModel {
            viewState.first { state -> state is MainViewState.Error }
            // waiting until the error takes place
            delay(TimeUnit.SECONDS.toMillis(1L))
        }
        composeRule
            .onNodeWithContentDescription("Acronym Search Error")
            .assertIsDisplayed()
    }

    /**
     * Changes the text in the acronym input field
     *
     * @param acronym text that should be set
     */
    private fun setAcronymInput(acronym: String) {
        composeRule
            .onNodeWithContentDescription("Acronym Input Field")
            .performTextInput(acronym)
    }

    /**
     * Asserts the actual text that users see in the acronym input field
     *
     * @param expectedAcronym what text should already be set
     */
    private fun assertAcronymInputMatches(expectedAcronym: String) {
        composeRule
            .onNodeWithContentDescription("Acronym Input Field")
            .assertTextEquals(expectedAcronym)
    }

    /**
     * Asserts the actual number of items that users see in the definition list
     *
     * @param expectedItemCount how many items are expected in the list
     */
    private fun assertDefinitionsItemsCount(expectedItemCount: Int) {
        composeRule
            .onAllNodesWithContentDescription("Acronym Definition")
            .assertCountEquals(expectedItemCount)
    }

    /**
     * Provides ability to use [MainViewModel]
     *
     * @param block code that uses view model
     */
    private inline fun withViewModel(
        crossinline block: suspend MainViewModel.() -> Unit,
    ) {
        val viewModel: MainViewModel by composeRule.activity.viewModels()
        runBlocking { block(viewModel) }
    }
}
