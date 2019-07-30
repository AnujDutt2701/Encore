package com.alacritystudios.encore.util

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

object UiUtil {

    fun setLoadingBehaviour(
        swipeRefreshLayout: SwipeRefreshLayout, recyclerView: RecyclerView,
        constraintLayout: ConstraintLayout, contentProviderResponseUtil: ContentProviderResponseUtil<*>
    ) {
        Timber.d("setLoadingBehaviour")
        swipeRefreshLayout.isRefreshing =
            contentProviderResponseUtil.contentProviderState === ContentProviderState.LOADING
        if (contentProviderResponseUtil.contentProviderState === ContentProviderState.LOADING) {
            recyclerView.visibility = View.VISIBLE
            constraintLayout.visibility = View.GONE
        } else if (contentProviderResponseUtil.contentProviderState === ContentProviderState.ERROR) {
            recyclerView.visibility = View.GONE
            constraintLayout.visibility = View.VISIBLE
            Timber.d("Failed.")
        } else if (contentProviderResponseUtil.contentProviderState === ContentProviderState.LOADED) {
            recyclerView.visibility = View.VISIBLE
            constraintLayout.visibility = View.GONE
            Timber.d("Success.")
        }
    }
}
