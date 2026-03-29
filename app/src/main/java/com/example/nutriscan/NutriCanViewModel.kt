package com.example.nutriscan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NutriCanViewModel : ViewModel() {
    private val _entries = MutableStateFlow<List<FoodEntry>>(emptyList())
    val entries: StateFlow<List<FoodEntry>> = _entries.asStateFlow()

    private val _totalCalories = MutableStateFlow(0f)
    val totalCalories: StateFlow<Float> = _totalCalories.asStateFlow()

    fun addSimulatedScan() {
        val added = FoodEntry(name = "Rice Bowl", calories = 250f)
        _entries.value = _entries.value + added
        recalcTotal()
    }

    fun addSimulatedVoice() {
        val added = FoodEntry(name = "Grilled Chicken", calories = 320f)
        _entries.value = _entries.value + added
        recalcTotal()
    }

    private fun recalcTotal() {
        _totalCalories.value = _entries.value.sumOf { it.calories.toDouble() }.toFloat()
    }

    fun clearAll() {
        _entries.value = emptyList()
        _totalCalories.value = 0f
    }
}
