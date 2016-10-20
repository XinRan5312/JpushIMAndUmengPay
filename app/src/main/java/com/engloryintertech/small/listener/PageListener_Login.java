package com.engloryintertech.small.listener;


import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Administrator on 2016/8/15.
 */
public class PageListener_Login implements ViewPager.OnPageChangeListener{

    private int[] ima;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int index=position%ima.length;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
