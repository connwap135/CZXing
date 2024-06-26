package me.devilsen.czxing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import me.devilsen.czxing.R;
import me.devilsen.czxing.util.BarCodeUtil;
import me.devilsen.czxing.util.BitmapUtil;

public class PointView extends View {

    private Context mContext;
    private Paint mPaint;
    private Paint mBitmapPaint;
    private int mColor;
    private int mWhiteColor;
    private int mBigRadius = 15;
    private int mLittleRadius = 12;

    private int mX;
    private int mY;
    private Bitmap mArrowBitmap;
    private boolean mNeedArrow;

    public PointView(Context context) {
        this(context, null);
    }

    public PointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        mBigRadius = BarCodeUtil.dp2px(context, 15);
        mLittleRadius = BarCodeUtil.dp2px(context, 12);
        mColor = context.getResources().getColor(R.color.czxing_point_green);
        mWhiteColor = context.getResources().getColor(R.color.czxing_point_white);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmapPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.makeMeasureSpec(mBigRadius * 2, MeasureSpec.EXACTLY);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mX = (right - left) / 2;
            mY = (bottom - top) / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mWhiteColor);
        canvas.drawCircle(mX, mY, mBigRadius, mPaint);

        mPaint.setColor(mColor);
        canvas.drawCircle(mX, mY, mLittleRadius, mPaint);

        if (mNeedArrow) {
            drawArrow(canvas);
        }
    }

    public void setColor(@ColorRes int color) {
        mColor = mContext.getResources().getColor(color);
    }

    private void drawArrow(Canvas canvas) {
        if (mArrowBitmap == null) {
            mArrowBitmap = BitmapUtil.getBitmap(getContext(), R.drawable.ic_baseline_arrow_forward_24);
        }
        assert mArrowBitmap != null;
        int left = mX - mArrowBitmap.getWidth() / 2;
        int top = mY - mArrowBitmap.getHeight() / 2;
        canvas.drawBitmap(mArrowBitmap, left, top, mBitmapPaint);
    }

    public void drawArrow() {
        mNeedArrow = true;
    }
}
