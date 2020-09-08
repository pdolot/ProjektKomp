package com.example.myapplication.view.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Indicator @JvmOverloads constructor(
    context: Context,
    var mWidth: Int,
    var mHeight: Int,
    var margin: Int = 0,
    var color: Int = Color.WHITE,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = this@Indicator.color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth + 2 * margin, mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawOval(margin.toFloat(), 0f, measuredWidth.toFloat() - margin, measuredHeight.toFloat(), paint)
    }
}