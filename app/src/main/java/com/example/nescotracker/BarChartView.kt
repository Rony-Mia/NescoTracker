package com.example.nescotracker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.example.nescotracker.data.MonthlyUsage

/**
 * মাসিক খরচের রিপোর্ট দেখানোর জন্য হালকা একটি বার-চার্ট।
 * কোনো external লাইব্রেরি ছাড়াই আঁকা হয়।
 */
class BarChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var data: List<MonthlyUsage> = emptyList()
    private var isDark = false

    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 26f
        textAlign = Paint.Align.CENTER
    }
    private val valuePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 24f
        textAlign = Paint.Align.CENTER
        isFakeBoldText = true
    }

    fun setData(newData: List<MonthlyUsage>, darkMode: Boolean = false) {
        data = newData
        isDark = darkMode
        labelPaint.color = if (isDark) Color.parseColor("#B0B0B0") else Color.parseColor("#757575")
        valuePaint.color = if (isDark) Color.parseColor("#F5F5F5") else Color.parseColor("#212121")
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paddingBottom = 50f
        val paddingTop = 40f
        val paddingSide = 20f

        if (data.isEmpty()) {
            labelPaint.textAlign = Paint.Align.LEFT
            canvas.drawText(
                "এখনো যথেষ্ট ডেটা নেই — কয়েক সপ্তাহ ব্যবহারের পর মাসিক রিপোর্ট দেখা যাবে",
                paddingSide, height / 2f, labelPaint
            )
            labelPaint.textAlign = Paint.Align.CENTER
            return
        }

        val chartHeight = height - paddingBottom - paddingTop
        val chartWidth = width - paddingSide * 2
        val maxUsage = data.maxOf { it.usage }.coerceAtLeast(1f)

        val barCount = data.size
        val slotWidth = chartWidth / barCount
        val barWidth = (slotWidth * 0.5f).coerceAtMost(70f)

        data.forEachIndexed { index, monthly ->
            val slotCenter = paddingSide + slotWidth * index + slotWidth / 2f
            val barHeight = (monthly.usage / maxUsage) * chartHeight
            val top = paddingTop + (chartHeight - barHeight)
            val left = slotCenter - barWidth / 2f
            val right = slotCenter + barWidth / 2f

            val gradient = LinearGradient(
                0f, top, 0f, paddingTop + chartHeight,
                Color.parseColor("#1FCB6B"), Color.parseColor("#8CF0BB"),
                Shader.TileMode.CLAMP
            )
            barPaint.shader = gradient

            val rect = RectF(left, top, right, paddingTop + chartHeight)
            canvas.drawRoundRect(rect, 12f, 12f, barPaint)

            canvas.drawText("৳${monthly.usage.toInt()}", slotCenter, top - 10f, valuePaint)
            canvas.drawText(monthly.monthLabel, slotCenter, height - 15f, labelPaint)
        }
    }
}
