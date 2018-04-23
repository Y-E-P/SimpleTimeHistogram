package com.steelkiwi.simplehistogram

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * Created by yuriy_poudanen on 29.03.2018.
 */
class SimpleHistogramView : View {

    private var itemsList: List<TimeData> = listOf()
    private val paintLines = Paint()
    private val paintColumns = Paint()
    private val paintText = Paint()

    companion object {
        const val DEFAULT_DIVIDERS_COUNT = 25
        const val DEFAULT_HOURS_COUNT = 24
        const val DEFAULT_TEXT_SIZE = 30
        const val DEFAULT_STEP_COLUMN = 3
        const val DEFAULT_POSITION_Y = 0.0f
        const val DEFAULT_POSITION_X = 0.0f
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }


    private fun init(context: Context) {
        paintText.textSize = 24.0f
        paintLines.isAntiAlias = true
        paintColumns.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /**
         * Dividers drawing
         */
        canvas?.let { itCanvas ->
            var currentColumnIndex = 0
            var canvasHeight: Float = (itCanvas.height - (paddingTop + paddingBottom)).toFloat()
            var canvasWidth: Float = (itCanvas.width - (paddingEnd + paddingStart)).toFloat()
            var x = DEFAULT_POSITION_X
            while (currentColumnIndex < DEFAULT_DIVIDERS_COUNT) {
                if (currentColumnIndex.rem(DEFAULT_STEP_COLUMN) == 0) {
                    paintLines.strokeWidth = 5.0f
                    itCanvas.drawLine(x, DEFAULT_POSITION_Y, x, canvasHeight - DEFAULT_TEXT_SIZE, paintLines)
                    itCanvas.drawText(calculateTime(currentColumnIndex).toString(), x, canvasHeight, paintText)
                } else {
                    paintLines.strokeWidth = 1.0f
                    itCanvas.drawLine(x, DEFAULT_POSITION_Y, x, canvasHeight - DEFAULT_TEXT_SIZE, paintLines)
                }
                x += canvasWidth / DEFAULT_HOURS_COUNT
                currentColumnIndex++
            }
        }

        /**
         *  Hours columns
         */
        canvas?.let { itCanvas ->
            var currentColumnIndex = 0
            var canvasHeight: Float = (itCanvas.height - (paddingTop + paddingBottom)).toFloat()
            var canvasWidth: Float = (itCanvas.width - (paddingEnd + paddingStart)).toFloat()
            var x = (canvasWidth / DEFAULT_HOURS_COUNT) * 0.50f
            while (currentColumnIndex < itemsList.size) {
                val timeData = itemsList[currentColumnIndex]
                paintColumns.color = Color.parseColor(timeData.colorHex)
                var heightWithText = canvasHeight - DEFAULT_TEXT_SIZE
                var calculatedHeight = heightWithText - ((heightWithText / 100) * timeData.loadPercentage)
                calculatedHeight = if (calculatedHeight > 0) calculatedHeight else 0.0f
                paintColumns.strokeWidth = (canvasWidth / DEFAULT_HOURS_COUNT) * 0.5f
                itCanvas.drawLine(x, calculatedHeight, x, heightWithText, paintColumns)
                x += canvasWidth / DEFAULT_HOURS_COUNT
                currentColumnIndex++
            }
        }
    }

    private fun calculateTime(currentColumnIndex: Int): Int =
            if (currentColumnIndex < 18) {
                currentColumnIndex + 6
            } else {
                currentColumnIndex - 18
            }


    fun setItems(items: List<TimeData>?) {
        this.itemsList = items ?: listOf()
        invalidate()
        requestLayout()
    }


}