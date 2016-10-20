package com.engloryintertech.small.adapter.page;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class PagerAdapter_home extends PagerAdapter {

    private Context context;
    private List<ImageView> list;

    public PagerAdapter_home(Context context, List<ImageView> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (list.get(position % list.size()).getParent() == null) {
            container.addView(list.get(position % list.size()));
        } else {
            container.removeView(list.get(position % list.size()));
            container.addView(list.get(position % list.size()));
        }
        return list.get(position % list.size());
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
