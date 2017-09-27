package com.qk365.widget.circledial.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.qk365.widget.circledial.R;

import java.text.DecimalFormat;


/**
 * 自定义进度控件
 */

public class QKDegreeView extends View {


    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmapBig;
    private Bitmap mBitmapSmall;
    private int drawableIDBig;
    private int drawableIDSmall;
    private int bigWidth, bigHeight, smallWidth, smallHeight;

    private int arcWidth;//圆环宽度
    private RectF rectF;//圆环为该矩形的内切圆
    private int x;//圆环所在矩形距左距离
    private int y;//圆环所在矩形距上距离

    private int startAngle = 45;//圆弧开始的角度
    private float sweepAngle;//圆弧的角度
    private int arcColorID;//圆环颜色
    private int textColorID;//字体颜色
    private int textSize;
    private float percent;
    private float mSweepAnglePer;

    private BarAnimation anim;
    private String textPre = "成交率%";
    private DecimalFormat df = new DecimalFormat("#.##");
    private int mDUration;

    public QKDegreeView(Context context) {
        this(context, null);
    }


    public QKDegreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.QKDegreeView);
        textPre = ta.getString(R.styleable.QKDegreeView_mTextPre);
        if (TextUtils.isEmpty(textPre)) {
            textPre = "成功率:";
        }
        drawableIDBig = ta.getResourceId(R.styleable.QKDegreeView_bigCircleBg, R.drawable.big_circle);
        drawableIDSmall = ta.getResourceId(R.styleable.QKDegreeView_smallCircleBg, R.drawable.small_circle);
        textSize = (int) ta.getDimension(R.styleable.QKDegreeView_mTextSize, 18);
        textColorID = ta.getColor(R.styleable.QKDegreeView_mTextColor, getResources().getColor(R.color.colorAccent));
        arcColorID = ta.getColor(R.styleable.QKDegreeView_mArcColor, getResources().getColor(R.color.colorPrimary));
        percent = ta.getFloat(R.styleable.QKDegreeView_mPercent, -1);
        mDUration = ta.getInteger(R.styleable.QKDegreeView_mDuration, 2000);
        ta.recycle();
        mPaint = new Paint();
        mBitmapBig = BitmapFactory.decodeResource(getResources(), drawableIDBig);
        mBitmapSmall = BitmapFactory.decodeResource(getResources(), drawableIDSmall);
        bigHeight = mBitmapBig.getHeight();
        bigWidth = mBitmapBig.getWidth();
        smallHeight = mBitmapSmall.getHeight();
        smallWidth = mBitmapSmall.getWidth();
        anim = new BarAnimation();
        anim.setDuration(mDUration);
        if (percent > 0) {
            change(percent);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initValues();
    }

    public void change(float percent) {
        this.percent = percent;
        if (percent > 100) {
            percent = 100;
        } else if (percent < 0) {
            percent = 0;
        }
        sweepAngle = 360 * percent / 100;
        startAnimation(anim);
    }


    /**
     * 初始化测量值
     */
    private void initValues() {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        arcWidth = (bigWidth - smallWidth) / 2;//圆环宽度
        x = (mWidth - bigWidth) / 2 + arcWidth / 2;//left
        y = (mHeight - bigHeight) / 2 + arcWidth / 2;//top
        rectF = new RectF(x, y, mWidth - x, mHeight - y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //最大的圆
        canvas.drawBitmap(mBitmapBig, (getWidth() - bigWidth) / 2, (getHeight() - bigHeight) / 2, mPaint);
        //内圆
        canvas.drawBitmap(mBitmapSmall, (getWidth() - smallWidth) / 2, (getHeight() - smallHeight) / 2, mPaint);
        //中间的圆环
        drawArc(canvas);
        //画圆环两端圆弧
        drawCircle(canvas);

        //写字
        drawText(canvas);
    }

    /**
     * 写字
     */
    private void drawText(Canvas canvas) {
        initPaint();
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColorID);
        String text = textPre + df.format(percent) + "%";
        float textLength = mPaint.measureText(text);
        canvas.drawText(text, (mWidth - textLength) / 2, mHeight / 2, mPaint);
    }

    /**
     * 画圆环
     */
    private void drawArc(Canvas canvas) {
        initPaint();
        mPaint.setColor(arcColorID);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(arcWidth);
        canvas.drawArc(rectF, startAngle, mSweepAnglePer, false, mPaint);
    }


    /**
     * 画圆环头部圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        if (sweepAngle <= 0) {
            return;
        }
        initPaint();
        int mRadiu = (smallWidth + arcWidth) / 2;
//        mPaint.setStrokeWidth(mRadiu - smallWidth / 2);
        mPaint.setColor(arcColorID);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int[] pointStart = getPointFromAngleAndRadius(startAngle, mRadiu);
        int[] pointEnd = getPointFromAngleAndRadius((int) (mSweepAnglePer + startAngle), mRadiu);
        canvas.drawCircle(pointStart[0], pointStart[1], arcWidth / 2, mPaint);
        canvas.drawCircle(pointEnd[0], pointEnd[1], arcWidth / 2, mPaint);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
    }

    /**
     * 根据角度和半径，求一个点的坐标
     */
    private int[] getPointFromAngleAndRadius(int angle, int radius) {
        double x = radius * Math.cos(angle * Math.PI / 180) + mWidth / 2;
        double y = radius * Math.sin(angle * Math.PI / 180) + mHeight / 2;
        return new int[]{(int) x, (int) y};
    }

    public class BarAnimation extends Animation {
        public BarAnimation() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                mSweepAnglePer = interpolatedTime * sweepAngle;
            } else {
                mSweepAnglePer = sweepAngle;
            }
            postInvalidate();
        }
    }


}
