package com.example.testapp.Text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author jingqingqing
 * @date 2022/3/23
 */
public class MyTextView extends AppCompatTextView {
    private Paint mPaint;
    private int mRadius;

    public MyTextView(Context context) {
        super(context);
        initPaint();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(h > 0) {
            mRadius = h / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 在 TextView 上画圆
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);
    }

    public void setRadius(int r) {
        mRadius = r;
        postInvalidate();
    }
}