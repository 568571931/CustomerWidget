package com.qk365.widget.circledial.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */

public class QKStepView extends View {

    private Paint paint;
    private int width;
    private int height;
    private int leftTextWidth;//左边文字宽度

    private int cX;//圆心x轴坐标
    private int cY;//圆心y轴坐标

    private List<Integer> yList = new ArrayList<>();
    private List<Integer> xList = new ArrayList<>();

    private int stepCount = 4;//总共的步骤
    private int currentStep = 0;//当前步骤

    private String color1 = "#d8d8d8";
    private String color2 = "#f3f3f3";
    private String color3 = "#000000";
    private String colorLine = "#cccccc";
    private float[] lines;//两条直线
    private float sX1;//直线一起始x坐标
    private float sX2;//直线二起始x欧标
    private String[] stepName = new String[]{"报修", "派单",
            "维修", "评价"};
    private String[] stepStatus = new String[]{
            "待报修", "待派单",
            "待维修", "待评价"
    };
    private float leftTextSize = 38;//左边字体大小
    private int circleWidth = 100;//大圆直径

    private int leftTextHeight;//左边文字高度
    private int rightTextHeight;//右边文字高度
    private int rightTextSpace = 10;//右边文字行间距
    private int leftTextStartX;//左边文字起始x轴坐标
    private int rightTextStartX;//右边文字起始x轴坐标

    public QKStepView(Context context) {
        this(context, null);
    }

    public QKStepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initValues();
    }


    private void initValues() {
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        leftTextWidth = width / 7;
        cX = leftTextWidth + circleWidth / 2;
        cY = circleWidth / 2;
        sX1 = leftTextWidth + circleWidth / 3;
        sX2 = leftTextWidth + circleWidth / 3 * 2;
        float sY = circleWidth / 2;
        float eY = height - circleWidth / 2;
        lines = new float[]{sX1, sY, sX1, eY,
                sX2, sY, sX2, eY};
        Rect rect = new Rect();
        paint.setTextSize(leftTextSize);
        paint.getTextBounds(stepName[0], 0, stepName[0].length(), rect);
        leftTextHeight = rect.height();
        int tW = rect.width();
        leftTextStartX = (leftTextWidth - tW) / 2;
        rightTextHeight = leftTextHeight * 2 + rightTextSpace;
        rightTextStartX = leftTextWidth + circleWidth + leftTextStartX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawShape(canvas);
    }

    public void setStep(int currentStep, String... stepDes) {
        int size = stepDes.length;
        for (int i = 0; i < stepDescribe.length; i++) {
            stepDescribe[i] = "";
        }
        for (int i = 0; i < size; i++) {
            if (size <= stepCount) {
                stepDescribe[i] = stepDes[i];
            }
        }
        this.currentStep = currentStep;
        invalidate();
    }

    /**
     * 开始画自定义的图形和文字
     */
    private void drawShape(Canvas canvas) {
        yList.clear();
        xList.clear();
        int averageH = (height - circleWidth) / (stepCount - 1);
        int circleRadius = circleWidth / 2;
        for (int i = 0; i < stepCount; i++) {
            int cY = circleRadius + averageH * i;
            yList.add(cY);
        }
        drawLine(canvas);
        drawText(canvas);
        for (int i = 0; i < stepCount; i++) {
            initPaint();
            if (currentStep == i) {
                drawCurrentCircle(canvas, yList.get(i));
            } else if (currentStep < i) {
                drawIncompleteCircle(canvas, yList.get(i));
            } else if (currentStep > i) {
                drawFinishedCircle(canvas, yList.get(i));
            }
        }
    }

    /**
     * 画线条
     */
    private void drawLine(Canvas canvas) {
        //画出两条细线
        paint.setColor(Color.parseColor(colorLine));
        paint.setStrokeWidth(1);
        canvas.drawLines(lines, paint);
        float lineW = sX2 - sX1;
        float x = lineW / 2 + sX1;
        //画出已完成进度的粗线
        initPaint();
        paint.setColor(Color.parseColor(colorLine));
        paint.setStrokeWidth(lineW);
        canvas.drawLine(x, circleWidth / 2, x, yList.get(currentStep), paint);
    }

    private String stepDescribe[] = new String[4];

    /**
     * 画文字
     */
    private void drawText(Canvas canvas) {
        initPaint();
        paint.setTextSize(leftTextSize);
        paint.setColor(Color.BLACK);
        for (int i = 0; i < stepCount; i++) {
            int startY = yList.get(i) + leftTextHeight / 2;
            canvas.drawText(stepName[i], leftTextStartX, startY, paint);
        }

        for (int i = 0; i < stepDescribe.length; i++) {
            int startY = rightTextHeight / 2 / 2;
            if (currentStep + 1 == i) {
                canvas.drawText(stepStatus[i], rightTextStartX, yList.get(i) + startY, paint);
            }
            if (TextUtils.isEmpty(stepDescribe[i])) {
                return;
            }
            String text = stepDescribe[i];
            if (text.contains(";")) {
                String[] texts = text.split(";");
                if (!TextUtils.isEmpty(texts[0])) {
                    canvas.drawText(texts[0], rightTextStartX, yList.get(i) - rightTextSpace / 2, paint);
                }
                if (!TextUtils.isEmpty(texts[1])) {
                    canvas.drawText(texts[1], rightTextStartX, yList.get(i) + startY * 2 + rightTextSpace / 2, paint);
                }
            } else {
                canvas.drawText(stepDescribe[i], rightTextStartX, yList.get(i) + startY, paint);
            }
        }


    }

    /**
     * 画出已完成圆
     */
    private void drawFinishedCircle(Canvas canvas, int cY) {
        int aW = circleWidth / 2 / 3;
        int a1 = aW / 2;
        int a2 = aW + a1;
        int a3 = aW * 2 + a1;
        paint.setColor(Color.parseColor(color1));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(aW);
        canvas.drawCircle(cX, cY, a3, paint);
        initPaint();
        paint.setColor(Color.parseColor(color2));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(aW);
        canvas.drawCircle(cX, cY, a2, paint);
        initPaint();
        paint.setColor(Color.parseColor(color1));
        canvas.drawCircle(cX, cY, aW, paint);
    }

    /**
     * 画出未完成圆
     */
    private void drawIncompleteCircle(Canvas canvas, int cY) {
        int a1 = circleWidth / 2 / 5 * 2;
        int a2 = circleWidth / 2 / 5 * 3;
        int a1Radius = circleWidth / 2 - a1 / 2;
        paint.setColor(Color.parseColor(color1));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(a1);
        canvas.drawCircle(cX, cY, a1Radius, paint);
        initPaint();
        paint.setColor(Color.parseColor(color2));
        canvas.drawCircle(cX, cY, a2, paint);
    }

    /**
     * 画出当前进度的圆
     */
    private void drawCurrentCircle(Canvas canvas, int cY) {
        int a1 = circleWidth / 2 / 5;
        int a2 = a1 * 2;
        int a3 = a2;
        int a1Radius = circleWidth / 2 - a1 / 2;
        int a2Radius = circleWidth / 2 - a1 - a2 / 2;
        paint.setColor(Color.parseColor(color3));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(a1);
        canvas.drawCircle(cX, cY, a1Radius, paint);
        initPaint();
        paint.setColor(Color.parseColor(color2));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(a2);
        canvas.drawCircle(cX, cY, a2Radius, paint);
        initPaint();
        paint.setColor(Color.parseColor(color3));
        canvas.drawCircle(cX, cY, a3, paint);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
    }
}
