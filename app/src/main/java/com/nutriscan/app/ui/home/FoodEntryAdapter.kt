package com.nutriscan.app.ui.home

import android.view.LayoutInflater; import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nutriscan.app.data.model.FoodEntry
import com.nutriscan.app.databinding.ItemFoodEntryBinding

class FoodEntryAdapter(private val items: List<FoodEntry>) :
    RecyclerView.Adapter<FoodEntryAdapter.ViewHolder>() {
    inner class ViewHolder(val b: ItemFoodEntryBinding) : RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        ViewHolder(ItemFoodEntryBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: ViewHolder, i: Int) {
        val item = items[i]
        h.b.tvFoodEmoji.text = item.emoji; h.b.tvFoodName.text = item.name
        h.b.tvFoodMeta.text = item.meta; h.b.tvFoodKcal.text = item.calories.toString()
        h.b.progressFood.progress = item.progressPercent
    }
}
