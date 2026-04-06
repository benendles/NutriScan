package com.nutriscan.app.ui.log

import android.os.Bundle; import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutriscan.app.data.model.FoodEntry; import com.nutriscan.app.data.model.MealType
import com.nutriscan.app.databinding.FragmentLogBinding

class LogFragment : Fragment() {
    private var _b: FragmentLogBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentLogBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        val foods = listOf(
            FoodEntry(emoji="🥑",name="Avocado Toast",  meta="08:30 · 280g",calories=420,progressPercent=65,mealType=MealType.BREAKFAST),
            FoodEntry(emoji="☕",name="Black Coffee",   meta="08:35 · 240ml",calories=5,  progressPercent=5, mealType=MealType.BREAKFAST),
            FoodEntry(emoji="🍊",name="Orange Juice",  meta="08:40 · 200ml",calories=84, progressPercent=25,mealType=MealType.BREAKFAST),
            FoodEntry(emoji="🍌",name="Banana",         meta="10:15 · 120g",calories=89, progressPercent=20,mealType=MealType.SNACK),
            FoodEntry(emoji="🍚",name="White Rice",     meta="12:45 · 200g",calories=260,progressPercent=60,mealType=MealType.LUNCH),
            FoodEntry(emoji="🍗",name="Grilled Chicken",meta="12:45 · 200g",calories=335,progressPercent=80,mealType=MealType.LUNCH),
            FoodEntry(emoji="🥗",name="Garden Salad",  meta="12:50 · 80g", calories=54, progressPercent=15,mealType=MealType.LUNCH))
        b.rvLogFoods.layoutManager = LinearLayoutManager(requireContext())
        b.rvLogFoods.adapter = LogFoodAdapter(foods)
        b.tvTotalCalories.text = "1,247"; b.tvTotalGoal.text = "Goal: 2,000"
        b.progressDayCalories.progress = 62; b.tvProgressPercent.text = "62% of goal"
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
