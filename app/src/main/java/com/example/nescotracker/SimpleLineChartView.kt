package com.example.nescotracker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.nescotracker.data.BalanceRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * খুব হালকা একটি লাইন-চার্ট ভিউ, ব্যালেন্স হিস্ট্রি দেখানোর জন্য।
 * কোনো external লাইব্রেরি লাগে না, তাই build.gradle/repositories নিয়ে
 * ঝামেলায় পড়তে হবে না।
 */
class SimpleLineChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var records: List<BalanceRecord> = emptyList()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1FCB6B")
        style = Paint.Style.STROKE
        strokeWidth = 6f
        strokeCap = Paint.Cap.ROUND
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#331FCB6B")
        style = Paint.Style.FILL
    }

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#12181B")
        style = Paint.Style.FILL
    }

    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#E0E0E0")
        strokeWidth = 2f
    }

    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#757575")
        textSize = 26f
    }

    private val dateFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)

    fun setData(data: List<BalanceRecord>) {
        records = data
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paddingLeft = 90f
        val paddingRight = 30f
        val paddingTop = 30f
        val paddingBottom = 60f

        val chartWidth = width - paddingLeft - paddingRight
        val chartHeight = height - paddingTop - paddingBottom

        if (records.size < 2) {
            canvas.drawText(
                "যথেষ্ট ডেটা নেই — আরও কিছুদিন চেক করলে গ্রাফ দেখা যাবে",
                paddingLeft, height / 2f, labelPaint
            )
            return
        }

        val maxBalance = records.maxOf { it.balance }.coerceAtLeast(1f)
        val minBalance = records.minOf { it.balance }.coerceAtMost(0f)
        val range = (maxBalance - minBalance).coerceAtLeast(1f)

        // Horizontal grid lines + Y-axis labels (৪টি লেভেল)
        for (i in 0..3) {
            val y = paddingTop + chartHeight * i / 3
            canvas.drawLine(paddingLeft, y, width - paddingRight, y, gridPaint)
            val value = maxBalance - (range * i / 3)
            canvas.drawText("৳${value.toInt()}", 5f, y + 8f, labelPaint)
        }

        // Line path + area fill
        val linePath = Path()
        val fillPath = Path()
        val stepX = chartWidth / (records.size - 1)

        records.forEachIndexed { index, record ->
            val x = paddingLeft + stepX * index
            val y = paddingTop + chartHeight - ((record.balance - minBalance) / range * chartHeight)

            if (index == 0) {
                linePath.moveTo(x, y)
                fillPath.moveTo(x, paddingTop + chartHeight)
                fillPath.lineTo(x, y)
            } else {
                linePath.lineTo(x, y)
                fillPath.lineTo(x, y)
            }

            if (index == records.size - 1) {
                fillPath.lineTo(x, paddingTop + chartHeight)
                fillPath.close()
            }

            canvas.drawCircle(x, y, 8f, dotPaint)
        }

        canvas.drawPath(fillPath, fillPaint)
        canvas.drawPath(linePath, linePaint)

        // X-axis labels: শুরু, মাঝামাঝি, শেষ তারিখ
        val firstDate = dateFormat.format(Date(records.first().checkedAt))
        val lastDate = dateFormat.format(Date(records.last().checkedAt))
        canvas.drawText(firstDate, paddingLeft, height - 15f, labelPaint)
        canvas.drawText(lastDate, width - paddingRight - 100f, height - 15f, labelPaint)
    }
}
