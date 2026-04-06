package com.nutriscan.app.ui.scan

import android.view.LayoutInflater; import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nutriscan.app.data.model.FoodEntry
import com.nutriscan.app.databinding.ItemDetectedFoodBinding

class DetectedFoodAdapter(private val items: List<FoodEntry>) :
    RecyclerView.Adapter<DetectedFoodAdapter.ViewHolder>() {
    inner class ViewHolder(val b: ItemDetectedFoodBinding) : RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(p: ViewGroup, v: Int) =
        ViewHolder(ItemDetectedFoodBinding.inflate(LayoutInflater.from(p.context), p, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: ViewHolder, i: Int) {
        val item = items[i]
        h.b.tvDetectedEmoji.text = item.emoji; h.b.tvDetectedName.text = item.name
        h.b.tvDetectedMeta.text = item.meta;   h.b.tvDetectedKcal.text = "${item.calories} kcal"
    }
}
