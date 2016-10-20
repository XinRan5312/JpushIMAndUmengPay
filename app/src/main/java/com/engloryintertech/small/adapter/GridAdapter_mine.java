package com.engloryintertech.small.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.model.bean.MineBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/17.
 */

public class GridAdapter_mine extends BaseAdapter {

    private Context context;
    private ArrayList<MineBean> list;

    public GridAdapter_mine(Context context, ArrayList<MineBean> list) {
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
            convertView = View.inflate(context, R.layout.item_mine_fragment, null);
            viewHolder.ima_mine = (ImageView) convertView.findViewById(R.id.ima_mine);
            viewHolder.tv_mine = (TextView) convertView.findViewById(R.id.tv_mine);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ima_mine.setImageResource(list.get(position).getIma());
        viewHolder.tv_mine.setText(list.get(position).getStr());
        return convertView;
    }

    public class ViewHolder {
        private ImageView ima_mine;
        private TextView tv_mine;
    }
}