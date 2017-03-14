package com.yuqirong.searchbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * @author qirong
 */

public class SearchBarView extends View {

    private static final String TAG = "SearchBarView";
    private static final int DEFAULT_ANIMATION_DURATION = 500;
    private static final int STATUS_OPEN = 1;
    private static final int STATUS_CLOSE = 1 << 2;
    private static final int STATUS_PROCESS = 1 << 3;
    private static final int DEFAULT_SEARCH_BAR_COLOR = Color.WHITE;
    private static final int DEFAULT_RIGHT_POSITION = 1;
    private static final int DEFAULT_LEFT_POSITION = 1 << 2;
    private static final int DEFAULT_SEARCH_TEXT_COLOR = Color.GRAY;
    private static final int DEFAULT_HINT_TEXT_SIZE = 14;

    private int mWidth;
    private int mHeight;
    private int mRadius;
    private int mStatus;
    private Paint mPaint;
    private int mPosition;
    private int mOffsetX;
    private RectF mRectF;
    private RectF mDstRectF;
    private CharSequence mSearchText;
    private int searchBarColor;
    private int searchTextColor;
    private ValueAnimator openAnimator = new ValueAnimator();
    private ValueAnimator closeAnimator = new ValueAnimator();
    private Bitmap bitmap;

    public SearchBarView(Context context) {
        this(context, null);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SearchBarView);
        searchBarColor = array.getColor(R.styleable.SearchBarView_search_bar_color, DEFAULT_SEARCH_BAR_COLOR);
        mPosition = array.getInteger(R.styleable.SearchBarView_search_bar_position, DEFAULT_RIGHT_POSITION);
        mStatus = array.getInteger(R.styleable.SearchBarView_search_bar_status, STATUS_CLOSE);
        int mDuration = array.getInteger(R.styleable.SearchBarView_search_bar_duration, DEFAULT_ANIMATION_DURATION);
        int searchBarIcon = array.getResourceId(R.styleable.SearchBarView_search_bar_icon, android.R.drawable.ic_search_category_default);
        mSearchText = array.getText(R.styleable.SearchBarView_search_bar_hint_text);
        searchTextColor = array.getColor(R.styleable.SearchBarView_search_bar_hint_text_color, DEFAULT_SEARCH_TEXT_COLOR);
        float defaultTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_HINT_TEXT_SIZE, getResources().getDisplayMetrics());
        float searchTextSize = array.getDimension(R.styleable.SearchBarView_search_bar_hint_text_size, defaultTextSize);
        array.recycle();
        if (mStatus == STATUS_OPEN) {

        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(searchBarColor);
        mPaint.setTextSize(searchTextSize);
        mRectF = new RectF();
        mDstRectF = new RectF();
        bitmap = BitmapFactory.decodeResource(getResources(), searchBarIcon);
        initAnimator(mDuration);
    }

    private void initAnimator(long duration) {
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetX = (int) animation.getAnimatedValue();
                invalidate();
            }
        };
        // init open animator
        openAnimator = new ValueAnimator();
        openAnimator.setInterpolator(accelerateInterpolator);
        openAnimator.setDuration(duration);
        openAnimator.addUpdateListener(animatorUpdateListener);
        openAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = STATUS_PROCESS;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = STATUS_OPEN;
                invalidate();
            }
        });
        // init close animator
        closeAnimator = new ValueAnimator();
        openAnimator.setInterpolator(accelerateInterpolator);
        closeAnimator.setDuration(duration);
        closeAnimator.addUpdateListener(animatorUpdateListener);
        closeAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = STATUS_PROCESS;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = STATUS_CLOSE;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // TODO
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            // TODO
        }
        mRadius = Math.min(mWidth, mHeight) / 2;
        if (mStatus == STATUS_OPEN) {
            mOffsetX = mWidth - mRadius * 2;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw search bar
        mPaint.setColor(searchBarColor);
        int left = mPosition == DEFAULT_RIGHT_POSITION ? mWidth - 2 * mRadius - mOffsetX : 0;
        int right = mPosition == DEFAULT_RIGHT_POSITION ? mWidth : 2 * mRadius + mOffsetX;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(left, 0, right, mHeight, mRadius, mRadius, mPaint);
        } else {
            mRectF.set(left, 0, right, mHeight);
            canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
        }
        // draw search bar icon
        mDstRectF.set(left + (int) ((1 - Math.sqrt(2) / 2) * mRadius), (int) ((1 - Math.sqrt(2) / 2) * mRadius),
                left + (int) ((1 + Math.sqrt(2) / 2) * mRadius), (int) ((1 + Math.sqrt(2) / 2) * mRadius));
        canvas.drawBitmap(bitmap, null, mDstRectF, mPaint);
        // draw search bar text
        if (mStatus == STATUS_OPEN && !TextUtils.isEmpty(mSearchText)) {
            mPaint.setColor(searchTextColor);
            Paint.FontMetrics fm = mPaint.getFontMetrics();
            double textHeight = Math.ceil(fm.descent - fm.ascent);
            canvas.drawText(mSearchText.toString(), 2 * mRadius, (float) (mRadius + textHeight / 2 - fm.descent), mPaint);
        }
    }

    /**
     * 判断搜索栏是否为打开状态
     *
     * @return
     */
    public boolean isOpen() {
        return mStatus == STATUS_OPEN;
    }

    /**
     * 判断搜索栏是否为关闭状态
     *
     * @return
     */
    public boolean isClose() {
        return mStatus == STATUS_CLOSE;
    }

    /**
     * 打开搜索栏
     */
    public void startOpen() {
        if (isOpen()) {
            return;
        } else if (closeAnimator.isStarted()) {
            closeAnimator.cancel();
        }
        openAnimator.setIntValues(mOffsetX, mWidth - mRadius * 2);
        openAnimator.start();
    }

    /**
     * 关闭搜索栏
     */
    public void startClose() {
        if (isClose()) {
            return;
        } else if (openAnimator.isStarted()) {
            openAnimator.cancel();
        }
        closeAnimator.setIntValues(mOffsetX, 0);
        closeAnimator.start();
    }

}
