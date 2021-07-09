package me.daemon.strokedtextview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristics
import android.text.TextPaint
import android.util.AttributeSet
import android.view.Gravity
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
        get() = paint.textSize
        set(value) {
            if (paint.textSize == value) return
            paint.textSize = value
            requestLayout()
            invalidate()
        }

    var solidTextColor: Int = Color.BLACK
        set(value) {
            if (field == value) return
            field = value
            invalidate()
        }

    var strokeTextColor: Int = Color.WHITE
        set(value) {
            if (field == value) return
            field = value
            invalidate()
        }

    var showStroke = true
        set(value) {
            if (field == value) return
            field = value
            invalidate()
        }

    var strokeWidth: Float
        get() = paint.strokeWidth
        set(value) {
            if (paint.strokeWidth == value) return
            paint.strokeWidth = value
            invalidate()
        }

    var gravity: Int = Gravity.START or Gravity.TOP
        set(value) {
            if (field == value) return
            field = value
            invalidate()
        }

    private val paint = TextPaint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.BLACK
        style = Paint.Style.FILL
        strokeWidth = 10f
    }

    private lateinit var layout: StaticLayout

    init {
        attrs?.apply {
            val t =
                context.obtainStyledAttributes(this, R.styleable.StrokedTextView, defStyleAttr, 0)
            try {
                text = t.getString(R.styleable.StrokedTextView_daemon_text) ?: ""
                textSize = t.getDimension(R.styleable.StrokedTextView_daemon_text_size, 0f)
                solidTextColor =
                    t.getColor(R.styleable.StrokedTextView_daemon_solid_text_color, Color.BLACK)
                showStroke = t.getBoolean(R.styleable.StrokedTextView_daemon_show_stroke, true)
                strokeTextColor =
                    t.getColor(R.styleable.StrokedTextView_daemon_stroke_text_color, Color.WHITE)
                strokeWidth = t.getDimension(
                    R.styleable.StrokedTextView_daemon_stroke_width,
                    paint.strokeWidth
                )
                gravity = t.getInteger(
                    R.styleable.StrokedTextView_daemon_gravity,
                    Gravity.START or Gravity.TOP
                )
            } finally {
                t.recycle()
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val wSize = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)

        val w = if (wMode == MeasureSpec.EXACTLY) {
            wSize
        } else {
            min(Layout.getDesiredWidth(text, paint).toInt(), wSize)
        }

        initLayout(w)

        val h = if (hMode == MeasureSpec.EXACTLY) {
            hSize
        } else {
            min(layout.height, hSize)
        }

        setMeasuredDimension(w, h)

    }

    override fun onDraw(canvas: Canvas) {
        if (text.isEmpty()) return

        canvas.save()

        val textH = layout.height

        val top = when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
            Gravity.CENTER_VERTICAL -> (measuredHeight - textH) / 2f
            Gravity.BOTTOM -> (measuredHeight - textH).toFloat()
            else -> 0f
        }

        canvas.translate(0f, top)

        if (showStroke) {
            paint.style = Paint.Style.STROKE
            paint.color = strokeTextColor
            layout.draw(canvas)
        }

        paint.style = Paint.Style.FILL
        paint.color = solidTextColor
        layout.draw(canvas)


        canvas.restore()
    }

    private fun initLayout(w: Int) {
        val layoutDirection = layoutDirection
        val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)

        @SuppressLint("RtlHardcoded")
        val alignment = when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
            Gravity.CENTER_HORIZONTAL -> Layout.Alignment.ALIGN_CENTER
            Gravity.RIGHT -> {
                Layout.Alignment.ALIGN_OPPOSITE
            }
            else -> Layout.Alignment.ALIGN_NORMAL
        }

        layout = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            StaticLayout.Builder
                    .obtain(
                            text,
                            0,
                            text.length,
                            paint,
                            w
                    )
                    .setAlignment(alignment)
                    .setTextDirection(TextDirectionHeuristics.LTR)
                    .setLineSpacing(0f, 1f)
//                    .setBreakStrategy(breakStrategy)
//                    .setJustificationMode(justificationMode)
                    .build()
        } else {
            @Suppress("DEPRECATION")
            StaticLayout(
                    text,
                    paint,
                    w,
                    alignment,
                    1f,
                    0f,
                    true
            )
        }
    }

}