package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.zhangbo.zlibrary.R;
import com.zhangbo.zlibrary.util.LogUtils;

/**
 * Author: zhangbo
 * Data：2019/8/22
 * TODO: 宽高等比的ImageView
 */
public class RatioImageView extends AppCompatImageView {

    //给定的比例
    private float mRatio;
    private boolean mIsFixWidth = true; //默认为宽/高的比例 如果为false 则表示为高/宽

    public RatioImageView(Context context) {
        this(context,null);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.RatioImage);
        mRatio=array.getFloat(R.styleable.RatioImage_riv_ratio,0);
        mIsFixWidth=array.getBoolean(R.styleable.RatioImage_riv_w2h,true);
        array.recycle();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mRatio>0){
            int measureSize = getDefaultSize(0, mIsFixWidth?widthMeasureSpec:heightMeasureSpec);
            //LogUtils.e("measureSize=="+measureSize);
            if(mIsFixWidth){
                int height = (int) (measureSize/mRatio);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
                //LogUtils.e("height=="+height);
            }else{
                int width = (int) (measureSize/mRatio);
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
                //LogUtils.e("width=="+width);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
