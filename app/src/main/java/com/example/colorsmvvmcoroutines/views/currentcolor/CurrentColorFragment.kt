package com.example.colorsmvvmcoroutines.views.currentcolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.colorsmvvmcoroutines.databinding.FragmentCurrentColorBinding
import com.example.colorsmvvmcoroutines.databinding.PartResultBinding
import com.example.colorsmvvmcoroutines.views.onTryAgain
import com.example.colorsmvvmcoroutines.views.renderSimpleResult
import com.example.foundation.model.ErrorResult
import com.example.foundation.model.PendingResult
import com.example.foundation.model.SuccessResult
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel

class CurrentColorFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<CurrentColorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)
        viewModel.currentColor.observe(viewLifecycleOwner) { result ->
            renderSimpleResult(root = binding.root,
                result = result,
                onSuccess = {
                    binding.colorView.setBackgroundColor(it.value)
                }
            )
        }
        binding.changeColorButton.setOnClickListener {
            viewModel.changeColor()
        }
        onTryAgain(binding.root) {
            viewModel.tryAgain()
        }
        return binding.root
    }
}