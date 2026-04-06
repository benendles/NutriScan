package com.nutriscan.app.ui.ai

import android.graphics.Color; import android.os.Bundle; import android.view.*
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.*; import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.nutriscan.app.databinding.FragmentAiBinding

class AiFragment : Fragment() {
    private var _b: FragmentAiBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentAiBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        b.tvNutritionScore.text = "74"; b.tvScoreDesc.text = "Good — room to improve protein intake"; b.progressScore.progress = 74
        setupChart()
    }
    private fun setupChart() {
        val days = listOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
        val cals = listOf(1850f,2100f,1700f,1950f,2300f,1800f,1247f)
        val entries = cals.mapIndexed { i,v -> BarEntry(i.toFloat(),v) }
        val colors = cals.map { if (it > 2000f) Color.parseColor("#C4522A") else Color.parseColor("#8FA672") }
        val ds = BarDataSet(entries,"Calories").apply { this.colors=colors; valueTextColor=Color.parseColor("#5A5A50"); valueTextSize=9f }
        b.weeklyChart.apply {
            data=BarData(ds).apply { barWidth=0.6f }; description.isEnabled=false; legend.isEnabled=false
            setDrawGridBackground(false); setBackgroundColor(Color.TRANSPARENT)
            xAxis.apply { position=XAxis.XAxisPosition.BOTTOM; valueFormatter=IndexAxisValueFormatter(days); granularity=1f; textColor=Color.parseColor("#8A8A7A"); textSize=10f; setDrawGridLines(false) }
            axisLeft.apply {
                textColor=Color.parseColor("#8A8A7A"); textSize=9f
                addLimitLine(LimitLine(2000f,"Goal").apply { lineColor=Color.parseColor("#3D4A2E"); lineWidth=1f; textColor=Color.parseColor("#3D4A2E"); textSize=10f })
            }
            axisRight.isEnabled=false; animateY(600); invalidate()
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
