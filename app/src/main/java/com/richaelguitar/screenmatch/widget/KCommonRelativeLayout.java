package com.richaelguitar.screenmatch.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * 布局适配
 */
public class KCommonRelativeLayout extends RelativeLayout {


    private boolean isInit;
    //这个尺寸是基于设计稿720*1280的
    private static  final  float  baseWidth = 720;
    private static final  float  baseHeight = 1280;

    private int appWidth;

    private int appHeight;

    private float scaleX = 1.0f;

    private float scaleY = 1.0f;

    public KCommonRelativeLayout(Context context) {
        this(context,null);
    }

    public KCommonRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public KCommonRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if(windowManager!=null){
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            appWidth = displayMetrics.widthPixels;
            appHeight = displayMetrics.heightPixels;
            scaleX = appWidth/ baseWidth;
            scaleY = appHeight/baseHeight;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!isInit){
            int childCount = getChildCount();
            for(int i=0;i<childCount;i++){
                View child  = getChildAt(i);
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                if(layoutParams.width !=  LayoutParams.MATCH_PARENT && layoutParams.width!= LayoutParams.WRAP_CONTENT){
                    layoutParams.width *= scaleX;
                }

                if(layoutParams.height !=  LayoutParams.MATCH_PARENT && layoutParams.height!= LayoutParams.WRAP_CONTENT){
                    layoutParams.height *= scaleY;
                }

                //设置间距
                layoutParams.topMargin *= scaleY;
                layoutParams.bottomMargin *= scaleY;
                layoutParams.leftMargin *= scaleX;
                layoutParams.rightMargin *= scaleX;
                child.setLayoutParams(layoutParams);
            }

            isInit = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
