package com.engloryintertech.small.adapter.page;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.model.bean.HomeBean;
import com.engloryintertech.small.tools.CustomImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<HomeBean.ServiceList> serviceLists;

    public HomeAdapter() {
    }

    public HomeAdapter(Context context, List<HomeBean.ServiceList> serviceLists) {
        this.context = context;
        this.serviceLists = serviceLists;
    }

    @Override
    public int getCount() {
        return serviceLists.size();
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
    public View getView(int position, View convertview, ViewGroup viewGroup) {
        ServiceViewHolder viewHolder;
        if (convertview == null) {
            viewHolder = new ServiceViewHolder();
            convertview = View.inflate(context, R.layout.item_home, null);
            viewHolder.ima_item_service = (ImageView) convertview.findViewById(R.id.ima_item_service);//服务图片
            viewHolder.tv_service_title = (TextView) convertview.findViewById(R.id.tv_service_title);//服务标题
            viewHolder.card_renzheng = (ImageView) convertview.findViewById(R.id.card_renzheng);
            viewHolder.jiangbei_renzheng = (ImageView) convertview.findViewById(R.id.jiangbei_renzheng);
            viewHolder.phone_renzheng = (ImageView) convertview.findViewById(R.id.phone_renzheng);
            viewHolder.tv_home_price = (TextView) convertview.findViewById(R.id.tv_home_price);//服务价格
            viewHolder.tv_home_mark = (TextView) convertview.findViewById(R.id.tv_home_mark);//服务备注
            viewHolder.rb_bar_home = (RatingBar) convertview.findViewById(R.id.rb_bar_home);//评星条
            viewHolder.tv_home_credit = (TextView) convertview.findViewById(R.id.tv_home_credit);//信用值
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ServiceViewHolder) convertview.getTag();
        }
        if (serviceLists.get(position).ImageUrlLst.size() != 0) {
            CustomImageLoader.getInstance(context).displayImage(serviceLists.get(position).ImageUrlLst.get(0), viewHolder.ima_item_service);
        }
        viewHolder.tv_service_title.setText(serviceLists.get(position).UserNickName);
        if (serviceLists.get(position).AuthenticationState == 1) {
            viewHolder.card_renzheng.setImageResource(R.mipmap.card_h);
        } else {
            viewHolder.card_renzheng.setImageResource(R.mipmap.card_l);
        }
        if (serviceLists.get(position).CertificationStatus == 1) {
            viewHolder.jiangbei_renzheng.setImageResource(R.mipmap.jiangbei_h);
        } else {
            viewHolder.jiangbei_renzheng.setImageResource(R.mipmap.jiangbei_l);
        }
        if (serviceLists.get(position).TellPhoneState == 1) {
            viewHolder.phone_renzheng.setImageResource(R.mipmap.phone_h);
        } else {
            viewHolder.phone_renzheng.setImageResource(R.mipmap.phone_l);
        }
        viewHolder.tv_home_price.setText(serviceLists.get(position).Price + "元/次");
        viewHolder.tv_home_mark.setText(serviceLists.get(position).Mark);
        viewHolder.rb_bar_home.setRating(serviceLists.get(position).UserStar);
        viewHolder.tv_home_credit.setText("信用值：" + serviceLists.get(position).CreditValue);
        return convertview;
    }

    class ServiceViewHolder {
        private ImageView ima_item_service;
        private TextView tv_service_title;
        private ImageView card_renzheng;
        private ImageView jiangbei_renzheng;
        private ImageView phone_renzheng;
        private TextView tv_home_price;
        private TextView tv_home_mark;
        private RatingBar rb_bar_home;
        private TextView tv_home_credit;
    }
}