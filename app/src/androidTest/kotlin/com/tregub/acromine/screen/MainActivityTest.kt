package com.tregub.acromine.screen

import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.android.material.textfield.TextInputLayout
import com.tregub.acromine.R
import com.tregub.acromine.feature.data.source.remote.AcronymsApiTestInterceptor
import com.tregub.acromine.screen.data.MainViewState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private lateinit var viewModel: MainViewModel

    private var acronymInput: TextView? = null

    @Before
    fun injectDependencies() {
        hiltRule.inject()
        activityRule.scenario.onActivity { activity ->
            viewModel = activity.viewModels<MainViewModel>().value
            acronymInput = activity.findViewById(R.id.acronym_input)
        }
    }

    @After
    fun releaseView() {
        acronymInput = null
    }

    @Test
    fun getDefaultViewState() {
        assertAcronymInputMatches("")
        assertDefinitionsItemCount(0)
    }

    @Test
    fun getLoadingViewState() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)
        assertDefinitionsItemCount(0)
        Espresso
            .onView(ViewMatchers.withId(R.id.progress_indication))
            .check(
                CustomViewAssertion<Boolean, SwipeRefreshLayout>(
                    expectedValue = true,
                    getActualValue = { view -> view.isRefreshing },
                )
            )
    }

    @Test
    fun getSuccessViewStateWithoutData() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITHOUT_DEFINITIONS_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITHOUT_DEFINITIONS_NAME)

        runBlocking {
            viewModel.viewState.first { state -> state is MainViewState.Success }
        }
        assertDefinitionsItemCount(0)
    }

    @Test
    fun getSuccessViewStateWithData() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITH_DEFINITIONS_NAME)

        runBlocking {
            viewModel.viewState.first { state -> state is MainViewState.Success }
        }
        assertDefinitionsItemCount(AcronymsApiTestInterceptor.ACRONYM_DEFINITIONS.size)
    }

    @Test
    fun getErrorViewState() {
        setAcronymInput(AcronymsApiTestInterceptor.ACRONYM_WITH_CRASH_NAME)

        assertAcronymInputMatches(AcronymsApiTestInterceptor.ACRONYM_WITH_CRASH_NAME)

        runBlocking {
            viewModel.viewState.first { state -> state is MainViewState.Error }
            // waiting until the error takes place
            delay(TimeUnit.SECONDS.toMillis(1L))
        }
        Espresso
            .onView(ViewMatchers.withId(R.id.acronym_input_layout))
            .check(
                CustomViewAssertion<Boolean, TextInputLayout>(
                    expectedValue = false,
                    getActualValue = { view -> view.error.isNullOrBlank() },
                )
            )
    }

    /**
     * Changes the text in the acronym input field
     *
     * @param acronym text that should be set
     */
    private fun setAcronymInput(acronym: String) {
        acronymInput?.apply { post { text = acronym } }
    }

    /**
     * Asserts the actual text that users see in the acronym input field
     *
     * @param expectedAcronym what text should already be set
     */
    private fun assertAcronymInputMatches(expectedAcronym: String) {
        Espresso
            .onView(ViewMatchers.withId(R.id.acronym_input))
            .check(ViewAssertions.matches(ViewMatchers.withText(expectedAcronym)))
    }

    /**
     * Asserts the actual number of items that users see in the definition list
     *
     * @param expectedItemCount how many items are expected in the list
     */
    private fun assertDefinitionsItemCount(expectedItemCount: Int) {
        Espresso
            .onView(ViewMatchers.withId(R.id.acronym_definitions))
            .check(
                CustomViewAssertion<Int, RecyclerView>(
                    expectedValue = expectedItemCount,
                    getActualValue = { view -> view.adapter?.itemCount ?: 0 },
                )
            )
    }

    /**
     * Local implementation of [ViewAssertion]
     *  that simplifies the process of asserting different views
     *
     * @param expectedValue something that will be compared to the actual value
     * @param getActualValue code that provides the actual value
     */
    private class CustomViewAssertion<T : Any, V : View>(
        private val expectedValue: T,
        private val getActualValue: (view: V) -> T,
    ) : ViewAssertion {

        @Suppress("UNCHECKED_CAST")
        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val actualValue: T = getActualValue(view as V)
            ViewMatchers.assertThat(actualValue, Matchers.`is`(expectedValue))
        }
    }
}
