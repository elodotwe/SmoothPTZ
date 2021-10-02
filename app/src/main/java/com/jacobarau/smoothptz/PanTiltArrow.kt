package com.jacobarau.smoothptz

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.MotionEventCompat

/**
 * TODO: document your custom view class.
 */
class PanTiltArrow : View {

    private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            ACTION_DOWN -> {
                Log.d("foo", "down")
                exampleColor = Color.GREEN
            }
            ACTION_UP -> {
                Log.d("foo", "up")
                exampleColor = Color.RED
            }
        }
        return false
    }

    /**
     * The text to draw
     */
    var exampleString: String?
        get() = _exampleString
        set(value) {
            _exampleString = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.PanTiltArrow, defStyle, 0)

        _exampleString = a.getString(
                R.styleable.PanTiltArrow_exampleString)
        _exampleColor = a.getColor(
                R.styleable.PanTiltArrow_exampleColor,
                exampleColor)
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _exampleDimension = a.getDimension(
                R.styleable.PanTiltArrow_exampleDimension,
                exampleDimension)

        if (a.hasValue(R.styleable.PanTiltArrow_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                    R.styleable.PanTiltArrow_exampleDrawable)
            exampleDrawable?.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
            color = Color.RED
            style = Paint.Style.STROKE
            textSize = 500f
        }

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = exampleDimension
            it.color = exampleColor
            textWidth = it.measureText(exampleString)
            textHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        Log.d("foo", "PanTiltArrow onDraw $exampleString")

            val circleRadius = 100F
            val paint = Paint().apply {
                color = ContextCompat.getColor(context, R.color.teal_700)
                style = Paint.Style.STROKE
                strokeWidth = 0f
                textSize = 210f
            }

            canvas.drawCircle(width / 2F, height / 2F, circleRadius, paint)

        canvas.drawText("fuck", width / 2f, height / 2f, paint)


        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        exampleString?.let {
            // Draw the text.
            canvas.drawText(it,
                    paddingLeft + (contentWidth - textWidth) / 2,
                    paddingTop + (contentHeight + textHeight) / 2,
                    textPaint)
        }

        // Draw the example drawable on top of the text.
        exampleDrawable?.let {
            it.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight)
            it.draw(canvas)
        }
    }
}