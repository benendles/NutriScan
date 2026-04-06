package com.nutriscan.app.ui.home

import android.os.Bundle; import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nutriscan.app.R
import com.nutriscan.app.data.model.FoodEntry
import com.nutriscan.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _b: FragmentHomeBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentHomeBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        b.btnScan.setOnClickListener { findNavController().navigate(R.id.action_home_to_scan) }
        b.btnVoice.setOnClickListener { findNavController().navigate(R.id.action_home_to_voice) }
        b.tvSeeAll.setOnClickListener { findNavController().navigate(R.id.logFragment) }
        b.tvViewAllAi.setOnClickListener { findNavController().navigate(R.id.aiFragment) }
        b.calorieRing.setCalories(1247, 2000)
        b.tvCaloriesConsumed.text = "1,247"
        b.macroProtein.tvMacroValue.text = "84g";  b.macroProtein.tvMacroLabel.text = "Protein"
        b.macroCarbs.tvMacroValue.text  = "156g"; b.macroCarbs.tvMacroLabel.text   = "Carbs"
        b.macroFats.tvMacroValue.text   = "42g";  b.macroFats.tvMacroLabel.text    = "Fats"
        b.macroWater.tvMacroValue.text  = "1.8L"; b.macroWater.tvMacroLabel.text   = "Water"
        val foods = listOf(
            FoodEntry(emoji="🥑", name="Avocado Toast",  meta="08:30 · BREAKFAST · 280g", calories=420, progressPercent=65),
            FoodEntry(emoji="🍌", name="Banana",          meta="10:15 · SNACK · 120g",    calories=89,  progressPercent=30),
            FoodEntry(emoji="🍚", name="Rice & Chicken",  meta="12:45 · LUNCH · 350g",    calories=738, progressPercent=90))
        b.rvFoodPreview.layoutManager = LinearLayoutManager(requireContext())
        b.rvFoodPreview.adapter = FoodEntryAdapter(foods)
        b.suggestionCalories.tvSuggestionIcon.text = "💡"
        b.suggestionCalories.tvSuggestionText.text = "You're 753 kcal short of your goal today"
        b.suggestionCalories.tvSuggestionSub.text  = "ADD A PROTEIN-RICH SNACK BEFORE 8PM"
        b.suggestionWater.tvSuggestionIcon.text = "💧"
        b.suggestionWater.tvSuggestionText.text = "Almost at hydration goal! Drink 200ml more"
        b.suggestionWater.tvSuggestionSub.text  = "HYDRATION GOAL: 2L/DAY"
        b.suggestionWater.root.setBackgroundResource(R.drawable.bg_card_olive_pale)
        b.suggestionCalories.root.setOnClickListener { findNavController().navigate(R.id.aiFragment) }
        b.suggestionWater.root.setOnClickListener    { findNavController().navigate(R.id.aiFragment) }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
