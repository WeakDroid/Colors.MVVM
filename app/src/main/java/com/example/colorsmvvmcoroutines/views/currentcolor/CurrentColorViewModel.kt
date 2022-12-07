package com.example.colorsmvvmcoroutines.views.currentcolor

import com.example.colorsmvvmcoroutines.R
import com.example.colorsmvvmcoroutines.model.colors.ColorListener
import com.example.colorsmvvmcoroutines.model.colors.ColorsRepository
import com.example.colorsmvvmcoroutines.model.colors.NamedColor
import com.example.colorsmvvmcoroutines.views.changecolor.ChangeColorFragment
import com.example.foundation.model.PendingResult
import com.example.foundation.model.SuccessResult
import com.example.foundation.model.takeSuccess
import com.example.foundation.model.tasks.dispatchers.Dispatcher
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UiActions
import com.example.foundation.views.BaseViewModel
import com.example.foundation.views.LiveResult
import com.example.foundation.views.MutableLiveResult

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val colorsRepository: ColorsRepository,
    dispatcher: Dispatcher
) : BaseViewModel(dispatcher) {

    private val _currentColor = MutableLiveResult<NamedColor>(PendingResult())
    val currentColor: LiveResult<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(SuccessResult(it))
    }

    // --- example of listening results via model layer

    init {
        colorsRepository.addListener(colorListener)
        load()
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    // --- example of listening results directly from the screen

    override fun onResult(result: Any) {
        super.onResult(result)
        if (result is NamedColor) {
            val message = uiActions.getString(R.string.changed_color, result.name)
            uiActions.toast(message)
        }
    }

    // ---
    fun changeColor() {
        val currentColor = currentColor.value.takeSuccess() ?: return
        val screen = ChangeColorFragment.Screen(currentColor.id)
        navigator.launch(screen)
    }

    fun tryAgain() {
        load()
    }

    private fun load() {
        colorsRepository.getCurrentColor().into(_currentColor)
    }
}