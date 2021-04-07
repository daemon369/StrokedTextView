package me.daemon.strokedtextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class StrokedTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var text: String = ""
        set(value) {
            if (field == value) return
            field = value
            requestLayout()
            invalidate()
        }

    var textSize: Float
        get() = solidPaint.textSize
        set(value) {
            solidPaint.textSize = value
            strokePaint.textSize = value
        }

    var solidTextColor: Int
        get() = solidPaint.color
        set(value) {
            solidPaint.color = value
            invalidate()
        }

    var strokeTextColor: Int
        get() = strokePaint.color
        set(value) {
            strokePaint.color = value
            invalidate()
        }

    var strokeWidth: Float
        get() = strokePaint.strokeWidth
        set(value) {
            strokePaint.strokeWidth = strokeWidth
            invalidate()
        }

    private val solidPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.BLACK
        style = Paint.Style.FILL
        strokeWidth = 10f
    }

    private val strokePaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawText(text, 100f, 100f, strokePaint)
        canvas.drawText(text, 100f, 100f, solidPaint)
    }

}