package com.tregub.acromine.screen

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textfield.TextInputLayout
import com.tregub.acromine.screen.data.MainViewState
import timber.log.Timber

/**
 * Allows to change the progress indication state for [SwipeRefreshLayout]
 *
 * @param state actual instance of [MainViewState]
 */
@BindingAdapter("setRefreshingMainViewState")
fun SwipeRefreshLayout.setRefreshingMainViewState(state: MainViewState?) {
    Timber.v("setRefreshingMainViewState $state")
    isRefreshing = state is MainViewState.Loading
}

/**
 * Allows to show a possible error state [MainViewState]
 *
 * @param state actual instance of [MainViewState]
 */
@BindingAdapter("setErrorMainViewState")
fun TextInputLayout.setErrorMainViewState(state: MainViewState?) {
    val message: String? = if (state is MainViewState.Error) state.message else null

    isErrorEnabled = !message.isNullOrBlank()
    error = message
}
