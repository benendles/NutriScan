package com.nutriscan.app.data.model

data class FoodEntry(
    val id: Long = System.currentTimeMillis(),
    val emoji: String,
    val name: String,
    val meta: String,
    val calories: Int,
    val progressPercent: Int,
    val mealType: MealType = MealType.LUNCH
)

data class MacroData(val proteinG: Int, val carbsG: Int, val fatsG: Int, val waterL: Float)
data class WeightEntry(val date: String, val weightKg: Float)
data class Suggestion(val icon: String, val text: String, val sub: String, val bgColorRes: Int)
data class AiTip(val icon: String, val title: String, val body: String, val priority: TipPriority)
enum class MealType { BREAKFAST, SNACK, LUNCH, DINNER }
enum class TipPriority { HIGH, MEDIUM, LOW }
