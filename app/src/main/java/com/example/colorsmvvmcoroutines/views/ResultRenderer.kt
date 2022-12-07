package com.example.colorsmvvmcoroutines.views

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.colorsmvvmcoroutines.R
import com.example.colorsmvvmcoroutines.databinding.PartResultBinding
import com.example.foundation.views.BaseFragment
import com.example.foundation.model.Result

fun <T> BaseFragment.renderSimpleResult(
    root: ViewGroup,
    result: Result<T>,
    onSuccess: (T) -> Unit
) {
    val binding = PartResultBinding.bind(root)
    renderResult(
        root = root,
        result = result,
        onPending = {
            binding.progressBar.isVisible = true
        },
        onError = {
            binding.errorContainer.isVisible = true
        },
        onSuccess = { successData ->
            root.children
                .filter { it.id != R.id.progressBar && it.id != R.id.errorContainer }
                .forEach { it.isVisible = true }
            onSuccess(successData)
        }
    )
}

fun BaseFragment.onTryAgain(root: View, onTryAgainPressed: () -> Unit) {

    root.findViewById<Button>(R.id.tryAgainButton).setOnClickListener {
        onTryAgainPressed()
    }
}