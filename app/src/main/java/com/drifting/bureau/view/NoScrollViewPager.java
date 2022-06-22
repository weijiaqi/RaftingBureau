package com.drifting.bureau.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;
    public NoScrollViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public NoScrollViewPager(Context context) {
        super(context);
    }
    /**
     * 1.dispatchTouchEvent⼀般情况不做处理
     *,如果修改了默认的返回值,⼦孩⼦都⽆法收到事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev); // return true;不⾏
    }
    /**
     * 是否拦截
     * 拦截:会⾛到⾃⼰的onTouchEvent⽅法⾥⾯来
     * 不拦截:事件传递给⼦孩⼦
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // return false;//可⾏,不拦截事件,
        // return true;//不⾏,孩⼦⽆法处理事件
        //return super.onInterceptTouchEvent(ev);//不⾏,会有细微移动
        if (isScroll){
            return super.onInterceptTouchEvent(ev);
        }else{
            return false;
        }
    }
    /**
     * 是否消费事件
     * 消费:事件就结束
     * 不消费:往⽗控件传
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //return false;// 可⾏,不消费,传给⽗控件
        //return true;// 可⾏,消费,拦截事件
        //super.onTouchEvent(ev); //不⾏,
        //虽然onInterceptTouchEvent中拦截了,
        //但是如果viewpage⾥⾯⼦控件不是viewgroup,还是会调⽤这个⽅法.
        if (isScroll){
            return super.onTouchEvent(ev);
        }else {
            return true;// 可⾏,消费,拦截事件
        }
    }
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
