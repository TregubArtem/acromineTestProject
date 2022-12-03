package com.tregub.acromine.ui

import android.graphics.Color
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * Allows for initial setup of [SwipeRefreshLayout]
 *
 * @param shouldInit dummy flag that indicates that initialization should be done
 */
@BindingAdapter("initProgressIndication")
fun SwipeRefreshLayout.initProgressIndication(shouldInit: Boolean?) {
    if (shouldInit == true) {
        isEnabled = false
        setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE)
    }
}
