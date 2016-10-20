package com.engloryintertech.small.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.model.bean.HomeBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/19.
 */
public class HorizonAdapter extends BaseAdapter {

    private Context context;
    private List<HomeBean.SortList> list;

    public HorizonAdapter(Context context, List<HomeBean.SortList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_horizon, null);
            viewHolder.tv_horizon = (TextView) convertView.findViewById(R.id.tv_horizon);
            viewHolder.ima_horizon = (ImageView) convertView.findViewById(R.id.ima_horizon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_horizon.setText(list.get(position).Title);
        if (list.get(position).Image == null || list.get(position).Image.equals("")) {
            viewHolder.ima_horizon.setImageResource(R.mipmap.ic_launcher);
        } else {
            ImageLoader.getInstance().displayImage(list.get(position).Image, viewHolder.ima_horizon);
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView tv_horizon;
        private ImageView ima_horizon;
    }
}