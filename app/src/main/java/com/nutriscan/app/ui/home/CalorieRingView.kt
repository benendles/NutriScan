package com.nutriscan.app.ui.home

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CalorieRingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var consumed = 1247; private var goal = 2000

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE; strokeWidth = 28f
        color = Color.parseColor("#0FFFFFFF"); strokeCap = Paint.Cap.ROUND
    }
    private val outerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE; strokeWidth = 28f
        color = Color.parseColor("#8FA672"); strokeCap = Paint.Cap.ROUND
    }
    private val innerBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE; strokeWidth = 18f
        color = Color.parseColor("#0AFFFFFF"); strokeCap = Paint.Cap.ROUND
    }
    private val innerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE; strokeWidth = 18f
        color = Color.parseColor("#D4A03A"); strokeCap = Paint.Cap.ROUND
    }

    fun setCalories(consumed: Int, goal: Int) { this.consumed = consumed; this.goal = goal; invalidate() }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cx = width / 2f; val cy = height / 2f
        val outerR = min(width, height) / 2f - 30f
        val innerR = outerR - 42f
        val outerRect = RectF(cx - outerR, cy - outerR, cx + outerR, cy + outerR)
        val innerRect = RectF(cx - innerR, cy - innerR, cx + innerR, cy + innerR)
        canvas.drawArc(outerRect, -90f, 360f, false, bgPaint)
        canvas.drawArc(outerRect, -90f, (consumed.toFloat() / goal) * 360f, false, outerPaint)
        canvas.drawArc(innerRect, -90f, 360f, false, innerBgPaint)
        canvas.drawArc(innerRect, -90f, 252f, false, innerPaint)
    }
}
