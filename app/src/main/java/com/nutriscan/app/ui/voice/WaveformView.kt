package com.nutriscan.app.ui.voice

import android.animation.ValueAnimator; import android.content.Context
import android.graphics.*; import android.util.AttributeSet; import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.sin; import kotlin.random.Random

class WaveformView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var anim: ValueAnimator? = null; private var phase = 0f; private var amplitude = 0.5f
    private val bars = FloatArray(24) { Random.nextFloat() * 0.5f + 0.2f }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#8FA672"); style = Paint.Style.FILL }

    fun startAnimation() {
        visibility = VISIBLE
        anim = ValueAnimator.ofFloat(0f, (2 * Math.PI).toFloat()).apply {
            duration = 800; repeatCount = ValueAnimator.INFINITE; interpolator = LinearInterpolator()
            addUpdateListener { v -> phase = v.animatedValue as Float; bars.indices.forEach { i -> bars[i] = ((sin(phase + i * 0.5f) + 1f) / 2f) * amplitude * 0.7f + 0.15f }; invalidate() }
            start()
        }
    }
    fun stopAnimation() { anim?.cancel(); visibility = INVISIBLE }
    fun updateAmplitude(rms: Float) { amplitude = (rms / 10f).coerceIn(0.1f, 1f) }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bw = width / (bars.size * 2f); val gap = bw * 0.5f
        bars.forEachIndexed { i, frac ->
            val bh = frac * height; val x = i * (bw + gap) + gap; val top = (height - bh) / 2f
            canvas.drawRoundRect(x, top, x + bw, top + bh, 4f, 4f, paint)
        }
    }
}
