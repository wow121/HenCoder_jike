package com.xianrui.hencoder_jike;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by xianrui on 2017/10/16.
 */

public class LikeNumberView extends View {

    TextPaint mPaint;

    List<Number> mNumberList;
    String mNumber;

    int mGravity = Gravity.CENTER;

    Scroller mScroller;

    int mWidth;
    int mHeight;

    public enum Animation {
        UP, DOWN, NONE
    }


    public LikeNumberView(Context context) {
        this(context, null);
    }

    public LikeNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        mPaint.setTextAlign(Paint.Align.CENTER);
        mScroller = new Scroller(getContext());
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    public void setTextSize(int size) {
        mPaint.setTextSize(size);
        requestLayout();
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    public void setNumber(int number) {
        setNumber(number, Animation.NONE);
    }

    public int getNumber() {
        return Integer.parseInt(mNumber);
    }

    public void setNumber(int number, Animation animation) {
        if (mNumberList == null) {
            mNumber = String.valueOf(number);
            mNumberList = numberToList(mNumber);
            for (Number num : mNumberList) {
                num.setAnimation(animation);
            }
        } else {
            if (!mNumber.equals(String.valueOf(number))) {
                List<Number> tempNumberList = numberToList(String.valueOf(number));
                if (tempNumberList.size() == mNumberList.size()) {
                    for (int i = 0; i < tempNumberList.size(); i++) {
                        Number tempNumber = tempNumberList.get(i);
                        if (!tempNumber.getNumber().equals(mNumberList.get(i).getNumber())) {
                            tempNumber.setAnimation(animation);
                        }
                    }
                } else {
                    for (Number num : tempNumberList) {
                        num.setAnimation(animation);
                    }
                }
                mNumber = String.valueOf(number);
                mNumberList = tempNumberList;
            }
        }
        requestLayout();
        invalidate();
    }

    private List<Number> numberToList(String number) {
        List<Number> numberList = new ArrayList<>();
        for (String num : number.split("")) {
            if (!TextUtils.isEmpty(num)) {
                Number n = new Number(num);
                numberList.add(n);
            }
        }
        return numberList;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mNumberList != null) {
            for (Number number : mNumberList) {
                number.draw(canvas, mPaint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (TextUtils.isEmpty(mNumber)) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

            final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            final int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            int width = 0;
            int height = 0;

            int textWidth = (int) mPaint.measureText(mNumber);
//            StaticLayout staticLayout = new StaticLayout(mNumber, mPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true);
//            Rect rect = new Rect();
//            mPaint.getTextBounds(mNumber, 0, mNumber.length(), rect);
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());

            switch (widthSpecMode) {
                case MeasureSpec.EXACTLY:
                    width = widthSpecSize;
                    break;
                case MeasureSpec.AT_MOST:
                    width = textWidth > widthSpecSize ? widthSpecSize : textWidth;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    width = widthSpecSize;
                    break;
            }

            switch (heightSpecMode) {
                case MeasureSpec.EXACTLY:
                    height = heightSpecSize;
                    break;
                case MeasureSpec.AT_MOST:
                    height = textHeight > heightSpecSize ? heightSpecSize : textHeight;
                    break;
                case MeasureSpec.UNSPECIFIED:
                    height = heightSpecSize;
                    break;
            }

            float left = 0;
            float top = 0;

            switch (mGravity) {
                case Gravity.CENTER:
                    left = (width - textWidth) / 2;
                    top = (height - textHeight) / 2;
                    break;
            }

            for (Number number : mNumberList) {
                float wordWidth = mPaint.measureText(number.getNumber());
                number.setLocation(new RectF(left, top, left + wordWidth, top + textHeight));
                left += wordWidth;
            }
            mWidth = width;
            mHeight = height;
            setMeasuredDimension(width, height);
            startScroller();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            for (Number number : mNumberList) {
                number.setProgress(mScroller.getCurrX());
            }
            invalidate();
        }
    }

    private void startScroller() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        mScroller.startScroll(0, 0, 100, 100, 500);
    }


    private static class Number {


        private String number;
        private RectF mRectF;

        private Animation animation = Animation.NONE;

        private int progress;


        Number(String number) {
            this.number = number;
        }

        public void setAnimation(Animation animation) {
            this.animation = animation;
        }

        public void setLocation(RectF rectF) {
            mRectF = rectF;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public String getNumber() {
            return number;
        }

        public void draw(Canvas canvas, Paint paint) {
            int tempAlpha = paint.getAlpha();
            float yPos = mRectF.height() / 2 - ((paint.descent() + paint.ascent()) / 2) + mRectF.top;
            if (progress != 0) {
                int alpha;
                switch (animation) {
                    case UP:
                        yPos += ((float) (100 - progress) / 100) * mRectF.height();
                        alpha = (int) (((float) (progress) / 100) * 255);
                        paint.setAlpha(alpha);
                        canvas.drawText(number, mRectF.centerX(), yPos, paint);
                        paint.setAlpha(255 - alpha);
                        canvas.drawText(getLastNumber(number), mRectF.centerX(), yPos - mRectF.height(), paint);
                        break;
                    case DOWN:
                        yPos -= ((float) (100 - progress) / 100) * mRectF.height();
                        alpha = (int) (((float) (100 - progress) / 100) * 255);
                        paint.setAlpha(255 - alpha);
                        canvas.drawText(number, mRectF.centerX(), yPos, paint);
                        paint.setAlpha(alpha);
                        canvas.drawText(getNextNumber(number), mRectF.centerX(), yPos + mRectF.height(), paint);
                        break;
                    case NONE:
                        canvas.drawText(number, mRectF.centerX(), yPos, paint);
                        break;
                }
            } else {
                canvas.drawText(number, mRectF.centerX(), yPos, paint);
            }
            paint.setAlpha(tempAlpha);
        }


        private static String getLastNumber(String num) {
            int number = Integer.valueOf(num);
            if (number == 0) {
                return String.valueOf(9);
            } else {
                return String.valueOf(number - 1);
            }
        }

        private static String getNextNumber(String num) {
            int number = Integer.valueOf(num);
            if (number == 9) {
                return String.valueOf(0);
            } else {
                return String.valueOf(number + 1);
            }
        }

    }


}
