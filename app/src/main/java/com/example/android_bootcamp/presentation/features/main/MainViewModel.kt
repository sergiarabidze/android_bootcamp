package com.example.android_bootcamp.presentation.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.domain.useCase.GetCourseUseCase
import com.example.android_bootcamp.presentation.features.model.AccountPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCourseUseCase: GetCourseUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.AddFromAccount -> {
                addFromAccount(event.account)
            }

            is MainEvent.AddToAccount -> {
                addToAccount(event.account)
            }

            is MainEvent.UpdateSellFirst -> {
                updateSellFirst(event.amount)
            }

            is MainEvent.UpdateSellSecond -> {
                updateSellSecond(event.amount)
            }
        }
    }

    private fun addFromAccount(account: AccountPresentation) {
        _state.value = _state.value.copy(fromAccount = account)
        fetchConversionRate()
    }

    private fun addToAccount(account: AccountPresentation) {
        _state.value = _state.value.copy(toAccount = account)
        fetchConversionRate()
    }

    private fun fetchConversionRate() {
        val fromAccount = _state.value.fromAccount
        val toAccount = _state.value.toAccount

        if (fromAccount != null && toAccount != null && fromAccount.valuteType != toAccount.valuteType) {
            viewModelScope.launch {
                val course = getCourseUseCase(fromAccount.valuteType, toAccount.valuteType)
                _state.value = _state.value.copy(course = course.toInt())
            }
        }

    }

    private fun updateSellFirst(amount: String) {
        val numericAmount = amount.toIntOrNull() ?: 0
        val course = _state.value.course ?: 1
        _state.value = _state.value.copy(
            firstSell = numericAmount,
            secondSell = (numericAmount * course)
        )
    }

    private fun updateSellSecond(amount: String) {
        val numericAmount = amount.toIntOrNull() ?: 0
        val course = _state.value.course ?: 1
        _state.value = _state.value.copy(
            secondSell = numericAmount,
            firstSell = (numericAmount / course)
        )
    }
}
