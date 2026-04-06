package com.nutriscan.app.ui.scan

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class ScanOverlayView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    private var scanFrac = 0f
    private var anim: ValueAnimator? = null
    private var active = false
    
    private val cornerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#8FA672")
        style = Paint.Style.STROKE
        strokeWidth = 6f
        strokeCap = Paint.Cap.ROUND
    }
    
    private val dimPaint = Paint().apply {
        color = Color.parseColor("#88000000")
    }
    
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }

    private val scanColors = intArrayOf(
        Color.TRANSPARENT,
        Color.parseColor("#CC8FA672"),
        Color.TRANSPARENT
    )

    fun showScanning(on: Boolean) {
        active = on
        if (on) {
            anim = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 1800
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
                interpolator = LinearInterpolator()
                addUpdateListener {
                    scanFrac = it.animatedValue as Float
                    invalidate()
                }
                start()
            }
        } else {
            anim?.cancel()
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val fs = minOf(w, h) * 0.6f
        val l = (w - fs) / 2f
        val t = (h - fs) / 2f
        val r = l + fs
        val bm = t + fs
        val cl = 50f
        
        canvas.drawRect(0f, 0f, w, t, dimPaint)
        canvas.drawRect(0f, bm, w, h, dimPaint)
        canvas.drawRect(0f, t, l, bm, dimPaint)
        canvas.drawRect(r, t, w, bm, dimPaint)
        
        canvas.drawLine(l, t + cl, l, t, cornerPaint)
        canvas.drawLine(l, t, l + cl, t, cornerPaint)
        canvas.drawLine(r - cl, t, r, t, cornerPaint)
        canvas.drawLine(r, t, r, t + cl, cornerPaint)
        canvas.drawLine(l, bm - cl, l, bm, cornerPaint)
        canvas.drawLine(l, bm, l + cl, bm, cornerPaint)
        canvas.drawLine(r - cl, bm, r, bm, cornerPaint)
        canvas.drawLine(r, bm, r, bm - cl, cornerPaint)
        
        if (active) {
            val ly = t + scanFrac * fs
            linePaint.shader = LinearGradient(l, ly, r, ly, scanColors, null, Shader.TileMode.CLAMP)
            canvas.drawLine(l + 8f, ly, r - 8f, ly, linePaint)
        }
    }
}
