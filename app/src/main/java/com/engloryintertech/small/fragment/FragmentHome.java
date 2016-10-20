package com.engloryintertech.small.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.activity.BaseActivity;
import com.engloryintertech.small.activity.HomeWebviewActivity;
import com.engloryintertech.small.activity.LocationActivity;
import com.engloryintertech.small.activity.MainActivity;
import com.engloryintertech.small.adapter.HorizonAdapter;
import com.engloryintertech.small.adapter.page.HomeAdapter;
import com.engloryintertech.small.adapter.page.PagerAdapter_home;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.map.GetLocation;
import com.engloryintertech.small.model.bean.HomeBean;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.Tools;
import com.engloryintertech.small.ui.AutoTextView;
import com.engloryintertech.small.ui.HorizontalListView;
import com.engloryintertech.small.ui.xlistview.XListView;
import com.engloryintertech.small.webview.WebActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FragmentHome extends Fragment implements View.OnClickListener, XListView.IXListViewListener {

    private String TAG = getClass().getName();
    private View view;
    private TextView tv_current_location;
    private MainActivity mainActivity;
    private ViewPager viewpager_home;
    private LinearLayout line_home_shape;
    private ArrayList<ImageView> viewPagerList = new ArrayList<ImageView>();
    private int current;
    private int lastIndex;
    private HorizontalListView horizon_listview;
    private XListView lv_home;//主页数据
    private AutoTextView tv_ad;
    private int sCount = 0;//
    private DataChangedListener mDataListener = new DataChangedListener();
    private int[] ima = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,};
    private ArrayAdapter<String> mAdapter;//xlistview
    private ArrayList<String> items = new ArrayList<String>();//xlistview
    private int start = 0;//xlistview
    private static int loadMoreCnt = 0;//xlistview
    private boolean horizon_one = true;
    private ArrayList<HomeBean.SortList> sortList;
    private LinearLayout line_message;
    private View view2;
    private View view3;
    private boolean isContinue = true;
    private ArrayList<HomeBean.BroadCast> broadCast;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    viewpager_home.setCurrentItem(viewpager_home.getCurrentItem() + 1);
                    handler.sendEmptyMessageDelayed(0, 3000);
                    break;
                case 1:
                    if (broadCast.size() != 0) {
                        tv_ad.setText(broadCast.get(sCount).Contents);
                    }
                    sCount++;
                    if (sCount == broadCast.size()) {
                        sCount = 0;
                    }
                    handler.sendEmptyMessageDelayed(1, 2000);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        registerDataListener();
        geneItems();//xlistview
        getHomeInfo(0, 15);
        initView();
        getViewPager();
        initData();
        return view;
    }

    private void geneItems() {
        if (loadMoreCnt <= 3) {
            for (int i = 0; i != 20; ++i) {
                items.add("refresh cnt " + (++start));
            }
        }
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.HomeInfoFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.HomeInfoSuccess, mDataListener);
    }

    private void getListView(final ArrayList<HomeBean.SortList> sortlist, final ArrayList<HomeBean.ServiceList> serviceLists, boolean one) {
        if (one) {//判断是否是第一次,horizontallistvie只加载一遍
            HorizonAdapter horizonAdapter = new HorizonAdapter(mainActivity, sortlist);
            horizon_listview.setAdapter(horizonAdapter);
            horizon_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    WebActivity.startActivity(BaseActivity.getActivity(), HttpConstants.childTypeListUrl + sortlist.get(i).SortListId
                             , "", WebActivity.OPEN_TYPE_GET, false);
                }
            });
            one = false;
        }
        HomeAdapter homeAdapter = new HomeAdapter(mainActivity, serviceLists);
        lv_home.setAdapter(homeAdapter);
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                WebActivity.startActivity(BaseActivity.getActivity(), HttpConstants.serviceDetailUrl + serviceLists.get(position).ServiceListId  + HttpConstants.serviceDetailUrlParam
                         , "", WebActivity.OPEN_TYPE_GET, false);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < ima.length; i++) {
            ImageView imageView = new ImageView(mainActivity);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage("http://192.168.199.30:27002/resources/20160818/40/1309361835A534A300.jpg_300A400A0A100.jpg", imageView);
            viewPagerList.add(imageView);
            //创建指示点
            ImageView image = new ImageView(mainActivity);
            image.setBackgroundResource(R.drawable.point_bg_home);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            params.gravity = Gravity.CENTER_HORIZONTAL;
            image.setLayoutParams(params);
            line_home_shape.addView(image);
            if (i == 0) {
                image.setEnabled(true);
            } else {
                image.setEnabled(false);
            }
        }
    }

    //获得viewpager
    private void getViewPager() {
        viewpager_home.setAdapter(new PagerAdapter_home(mainActivity, viewPagerList));
        current = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ima.length;
        viewpager_home.setCurrentItem(current);//设置当前页
        handler.sendEmptyMessageDelayed(0, 2000);//无限轮播
//      viewpager_home.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.e("FragmentHome", "按下");
//                        isContinue = false;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e("FragmentHome", "向上");
//                        isContinue = false;
//                        break;
//                    default:
//                        Log.e("FragmentHome", "默认");
//                        isContinue = true;
//                        break;
//                }
//                return false;
//            }
//      });

        viewpager_home.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int index = position % ima.length;
                line_home_shape.getChildAt(index).setEnabled(true);
                line_home_shape.getChildAt(lastIndex).setEnabled(false);
                lastIndex = index;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initView() {
        mainActivity = (MainActivity) getActivity();
        view2 = View.inflate(mainActivity, R.layout.xlistview_header2, null);
        view3 = View.inflate(mainActivity, R.layout.xlistview_header3, null);
        tv_current_location = (TextView) view2.findViewById(R.id.tv_current_location);//得到当前位置
        viewpager_home = (ViewPager) view2.findViewById(R.id.viewpager_home);
        line_home_shape = (LinearLayout) view2.findViewById(R.id.line_home_shape);
        line_message = (LinearLayout) view2.findViewById(R.id.line_message);//消息
        horizon_listview = (HorizontalListView) view3.findViewById(R.id.horizon_listview);
        lv_home = (XListView) view.findViewById(R.id.lv_home);
        tv_ad = (AutoTextView) view3.findViewById(R.id.tv_ad);
        tv_ad.setOnClickListener(this);//广告点击进入相应链接
        tv_current_location.setOnClickListener(this);
        line_message.setOnClickListener(this);
        handler.sendEmptyMessageDelayed(1, 2000);//滚动条
        lv_home.addHeaderView(view2);//添加头部
        lv_home.addHeaderView(view3);//添加头部
        lv_home.setFocusable(true);
        lv_home.setPullLoadEnable(true);
//      mAdapter = new ArrayAdapter<String>(mainActivity, R.layout.list_item, items);
//      lv_home.setAdapter(mAdapter);
//      lv_home.setPullLoadEnable(false);
//      lv_home.setPullRefreshEnable(false);
        lv_home.setXListViewListener(this);
//      lv_home.doAutoRefresh();
        lv_home.doAutoLoadMore();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (GetLocation.LocationInfo == null || GetLocation.LocationInfo.equals("")) {
            tv_current_location.setText("送至: 乐天超市 (上地东路)...");
        } else {
            tv_current_location.setText(GetLocation.LocationInfo);
        }
        super.onActivityCreated(savedInstanceState);
    }

    //获取首页信息
    private void getHomeInfo(int isupquery, int count) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isupquery", isupquery);
        params.put("count", count);
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.GetHoneInfo_URL, params, "GetHomeInfo",
                BaseApplication.DataType.HomeInfoFail, BaseApplication.DataType.HomeInfoSuccess);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_current_location:
                Intent intent = new Intent(mainActivity, LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.line_message:
                break;
            case R.id.tv_ad:
                Intent intent1 = new Intent(mainActivity, HomeWebviewActivity.class);
                intent1.putExtra("Links", broadCast.get(sCount).Links);
                startActivity(intent1);
                Log.e("FragmentHome", "" + broadCast.get(sCount).Links);
                Log.e("FragmentHome", "" + sCount);
                break;
            default:
                break;
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        getHomeInfo(0, 15);
    }

    private void onLoad() {//xlistview
        lv_home.stopRefresh();
        if (loadMoreCnt == 3) {
            lv_home.stopLoadMore(false);
        } else {
            lv_home.stopLoadMore(true);
        }
    }

    //加载
    @Override
    public void onLoadMore() {
        getHomeInfo(1, 15);
    }

    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case HomeInfoFail:
                    Tools.error(TAG, data.toString() + "HomeInFail");
                    break;//
                case HomeInfoSuccess:
                    HomeBean homeBean = (HomeBean) data;
                    ArrayList<HomeBean.ImageList> imageList = homeBean.ImageList;
                    ArrayList<HomeBean.ServiceList> serviceList = homeBean.ServiceList;
                    broadCast = homeBean.BroadCast;
                    sortList = homeBean.SortList;
                    getListView(sortList, serviceList, horizon_one);
                    break;
            }
        }
    }
}