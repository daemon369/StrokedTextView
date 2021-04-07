package me.daemon.strokedtextview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

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

    private val solidPaint = TextPaint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.BLACK
        style = Paint.Style.FILL
        strokeWidth = 10f
    }

    private val strokePaint = TextPaint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }


    private lateinit var solidLayout: StaticLayout
    private lateinit var strokeLayout: StaticLayout

    init {
//        initLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)

        val w = if (wMode == MeasureSpec.EXACTLY) {
            wSize
        } else {
            min(Layout.getDesiredWidth(text, solidPaint).toInt(), wSize)
        }

        initLayout(w)

        val h = if (hMode == MeasureSpec.EXACTLY) {
            hSize
        } else {
            min(solidLayout.height, hSize)
        }

        setMeasuredDimension(w, h)

    }

    override fun onDraw(canvas: Canvas) {
        strokeLayout.draw(canvas)
        solidLayout.draw(canvas)
//        canvas.drawText(text, 100f, 100f, strokePaint)
//        canvas.drawText(text, 100f, 100f, solidPaint)
    }

    private fun initLayout(w: Int) {
        solidLayout =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    StaticLayout.Builder
                            .obtain(
                                    text,
                                    0,
                                    text.length,
                                    solidPaint,
                                    w
                            )
                            .setAlignment(Layout.Alignment.ALIGN_CENTER)
                            .setTextDirection(TextDirectionHeuristics.LTR)
                            .setLineSpacing(0f, 1f)
//                    .setBreakStrategy(breakStrategy)
//                    .setJustificationMode(justificationMode)
                            .build()
                } else {
                    StaticLayout(
                            text,
                            solidPaint,
                            w,
                            Layout.Alignment.ALIGN_CENTER,
                            1f,
                            0f,
                            true
                    )
                }
        strokeLayout =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    StaticLayout.Builder
                            .obtain(
                                    text,
                                    0,
                                    text.length,
                                    strokePaint,
                                    w
                            )
                            .setAlignment(Layout.Alignment.ALIGN_CENTER)
                            .setTextDirection(TextDirectionHeuristics.LTR)
                            .setLineSpacing(0f, 1f)
//                    .setBreakStrategy(breakStrategy)
//                    .setJustificationMode(justificationMode)
                            .build()
                } else {
                    StaticLayout(
                            text,
                            strokePaint,
                            w,
                            Layout.Alignment.ALIGN_CENTER,
                            1f,
                            0f,
                            true
                    )
                }
    }

}