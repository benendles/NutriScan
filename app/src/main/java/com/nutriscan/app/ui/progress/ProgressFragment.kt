package com.nutriscan.app.ui.progress

import android.graphics.Color; import android.os.Bundle; import android.view.*; import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.nutriscan.app.databinding.FragmentProgressBinding

class ProgressFragment : Fragment() {
    private var _b: FragmentProgressBinding? = null; private val b get() = _b!!
    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?): View {
        _b = FragmentProgressBinding.inflate(i, c, false); return b.root
    }
    override fun onViewCreated(view: View, s: Bundle?) {
        super.onViewCreated(view, s)
        b.tvCurrentWeight.text = "72.4"; b.tvWeightChange.text = "↓ 0.6 kg this week"; b.progressWeightGoal.progress = 68
        setupChart()
        b.btnSaveWeight.setOnClickListener {
            val v = b.etWeightInput.text.toString()
            if (v.isNotEmpty()) { Toast.makeText(requireContext(), "Weight ${v} kg logged ✓", Toast.LENGTH_SHORT).show(); b.etWeightInput.text?.clear() }
        }
    }
    private fun setupChart() {
        val entries = listOf(0f to 78.0f,1f to 77.4f,2f to 76.8f,3f to 75.9f,4f to 75.2f,5f to 74.1f,6f to 73.3f,7f to 72.8f,8f to 72.4f).map { (x,y) -> Entry(x,y) }
        val labels = listOf("Mar 1","Mar 5","Mar 9","Mar 13","Mar 17","Mar 21","Mar 24","Mar 27","Today")
        val ds = LineDataSet(entries,"Weight").apply {
            color=Color.parseColor("#8FA672"); setCircleColor(Color.parseColor("#8FA672")); lineWidth=2.5f; circleRadius=4f
            setDrawFilled(true); fillColor=Color.parseColor("#8FA672"); fillAlpha=40
            mode=LineDataSet.Mode.CUBIC_BEZIER; valueTextColor=Color.parseColor("#5A5A50"); valueTextSize=10f
        }
        b.weightChart.apply {
            data=LineData(ds); description.isEnabled=false; legend.isEnabled=false
            setDrawGridBackground(false); setBackgroundColor(Color.TRANSPARENT)
            xAxis.apply { position=XAxis.XAxisPosition.BOTTOM; valueFormatter=IndexAxisValueFormatter(labels); granularity=1f; textColor=Color.parseColor("#8A8A7A"); textSize=9f; setDrawGridLines(false); labelRotationAngle=-30f }
            axisLeft.apply { textColor=Color.parseColor("#8A8A7A"); textSize=9f; setDrawGridLines(true); gridColor=Color.parseColor("#EAE3D3") }
            axisRight.isEnabled=false; animateX(800); invalidate()
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
