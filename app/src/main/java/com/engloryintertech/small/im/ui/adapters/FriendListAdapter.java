package com.engloryintertech.small.im.ui.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.im.XinRanApp;

import com.engloryintertech.small.im.db.Friend;
import com.engloryintertech.small.im.ui.views.SelectableRoundedImageView;
import com.engloryintertech.small.im.utils.RongGenerate;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by qixinh on 16/10/18.
 */
public class FriendListAdapter extends BaseAdapter{

    private Context context;

    private List<Friend> list;

    public FriendListAdapter(Context context, List<Friend> list) {
        this.context = context;
        this.list = list==null?null:list;
    }


    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<Friend> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null:list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final Friend mContent = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.friend_item, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.friendname);
            viewHolder.mImageView = (SelectableRoundedImageView) convertView.findViewById(R.id.frienduri);
            viewHolder.tvUserId = (TextView) convertView.findViewById(R.id.friend_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (mContent.isExitsDisplayName()) {
            viewHolder.tvTitle.setText(this.list.get(position).getDisplayName());
        } else {
            viewHolder.tvTitle.setText(this.list.get(position).getName());
        }
        if (TextUtils.isEmpty(list.get(position).getPortraitUri())) {
            String s = RongGenerate.generateDefaultAvatar(list.get(position).getName(), list.get(position).getUserId());
            ImageLoader.getInstance().displayImage(s, viewHolder.mImageView, XinRanApp.getOptions());
        } else {
            ImageLoader.getInstance().displayImage(list.get(position).getPortraitUri(), viewHolder.mImageView, XinRanApp.getOptions());
        }
        if (context.getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean("isDebug", false)) {
            viewHolder.tvUserId.setVisibility(View.VISIBLE);
            viewHolder.tvUserId.setText(list.get(position).getUserId());
        }
        return convertView;
    }


    final static class ViewHolder {

        /**
         * 昵称
         */
        TextView tvTitle;
        /**
         * 头像
         */
        SelectableRoundedImageView mImageView;
        /**
         * userid
         */
        TextView tvUserId;
    }
}
