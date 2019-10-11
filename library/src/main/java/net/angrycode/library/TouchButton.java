package net.angrycode.library;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


/**
 * Author - wecodexyz@gmail.com
 * GitHub - https://github.com/wecodexyz
 */
public class TouchButton extends View {

    private static final String TAG = TouchButton.class.getSimpleName();

    private Paint mPaint;
    private Paint mTextPaint;

    private float mCenterX, mCenterY;

    private float mRingRadius;
    private float mRadius;
    private float mAnimPadding;

    private Path mPath;

    private boolean mTouchDown = false;
    private float mAnimatorValue;

    private float mCurrentValue;
    private float mDefaultStroke;

    private String mText;
    private float mTextWidth;
    private float mTextSize;

    private float mLastX, mLastY;
    /**
     * parent view width and height
     */
    private int mParentWidth, mParentHeight;
    /**
     * original position
     */
    private int mLeft, mTop, mRight, mBottom;
    int mBtnBackground;
    int mTextColor;
    int mTextSizeDp = 16;

    public TouchButton(Context context) {
        this(context, null);
    }

    public TouchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.8F, -0.8F);

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TouchButton, defStyleAttr, 0);
        mBtnBackground = attributes.getColor(R.styleable.TouchButton_tb_color, ContextCompat.getColor(context, R.color.colorRed));
        mTextColor = attributes.getColor(R.styleable.TouchButton_tb_text_color, ContextCompat.getColor(context, android.R.color.white));
        mText = attributes.getString(R.styleable.TouchButton_tb_text);
        mAnimPadding = attributes.getDimension(R.styleable.TouchButton_tb_anim_padding, dip2px(getContext(), 10));
        attributes.recycle();

        mDefaultStroke = mAnimPadding / 2;

//        mAnimPadding = dip2px(getContext(), 10);
//        mDefaultStroke = dip2px(getContext(), 8);
        mCurrentValue = mDefaultStroke;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCurrentValue);
        mPaint.setColor(mBtnBackground);

        mTextSize = dip2px(getContext(), mTextSizeDp);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        mPath = new Path();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                Log.d(TAG, "value:" + mAnimatorValue);
                invalidate();
            }
        });
        valueAnimator.setDuration(900);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

        mTextWidth = mTextPaint.measureText(mText);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0, height = 0;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                width = dip2px(getContext(), 80);
                break;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                height = dip2px(getContext(), 80);
                break;
        }
        mRadius = Math.min(width, height) / 2;
        mRingRadius = mRadius + mAnimPadding;

        width = (int) (mRingRadius * 2 + mAnimPadding + mDefaultStroke*1.5);
        height = (int) (mRingRadius * 2 + mAnimPadding + mDefaultStroke*1.5);

        mCenterX = width / 2;
        mCenterY = height / 2;

        Log.e(TAG, "width:" + width + ",height:" + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!changed) {
            mLeft = left;
            mTop = top;
            mRight = right;
            mBottom = bottom;
        }

        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            mParentWidth = ((ViewGroup) parent).getMeasuredWidth();
            mParentHeight = ((ViewGroup) parent).getMeasuredHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTouchDown) {
            drawRing(canvas);
        } else {
            drawCircle(canvas);
        }

    }

    private void drawRing(Canvas canvas) {
        mPath.reset();
        mPath.addCircle(mCenterX, mCenterY, mRingRadius, Path.Direction.CW);
        mPaint.setStyle(Paint.Style.STROKE);
        mCurrentValue = mCurrentValue + mAnimatorValue;
        Log.d(TAG, "current:" + mCurrentValue);
        mPaint.setStrokeWidth(mCurrentValue);
        canvas.drawPath(mPath, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        mPath.reset();
        mPath.addCircle(mCenterX, mCenterY, mRadius, Path.Direction.CW);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, mPaint);
        //draw text
        float x = (getMeasuredWidth() - mTextWidth) / 2;
        float y = mCenterY + mTextSize / 3;
        canvas.drawText(mText, x, y, mTextPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getRawX();
                mLastY = event.getRawY();
                mTouchDown = true;
                valueAnimator.start();
                postInvalidate();
                if (mOnHoldListener != null) {
                    mOnHoldListener.onHold(true);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getRawX() - mLastX);
                int dy = (int) (event.getRawY() - mLastY);
                int l = getLeft() + dx;
                int b = getBottom() + dy;
                int r = getRight() + dx;
                int t = getTop() + dy;
                // 下面判断移动是否超出屏幕
                if (l < 0) {
                    l = 0;
                    r = l + getWidth();
                }
                if (t < 0) {
                    t = 0;
                    b = t + getHeight();
                }
                if (r > mParentWidth) {
                    r = mParentWidth;
                    l = r - getWidth();
                }
                if (b > mParentHeight) {
                    b = mParentHeight;
                    t = b - getHeight();
                }
                layout(l, t, r, b);
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mTouchDown = false;
                mCurrentValue = mDefaultStroke;
                valueAnimator.cancel();
//                layout(mLeft, mTop, mRight, mBottom);
                postInvalidate();
                if (mOnHoldListener != null) {
                    mOnHoldListener.onHold(false);
                }
                return true;
            default:
        }
        return super.onTouchEvent(event);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private OnHoldListener mOnHoldListener;

    public void setOnHoldListener(OnHoldListener listener) {
        mOnHoldListener = listener;
    }

    public interface OnHoldListener {
        void onHold(boolean hold);
    }
}
