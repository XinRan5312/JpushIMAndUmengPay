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
import com.engloryintertech.small.constant.Constants;
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
import java.util.List;
import java.util.Map;

public class CustomFragmentHome extends Fragment implements View.OnClickListener, XListView.IXListViewListener {

     private View fragmentView,view2,view3;
     private TextView tv_current_location;
     private ViewPager viewpager_home;
     private LinearLayout line_home_shape,line_message;
     private HorizontalListView horizon_listview;
     private XListView listview_home;//主页数据
     private AutoTextView mAutoTextView;

     private boolean mIsFreshOrLoad     = false;             //下拉刷新(获取新数据)=false  or  上拉（历史数据)=true
     private boolean isAddHeader        = false;
     private boolean isRefreshing       = false;
     private boolean isLoadingMore      = false;
     private int sCount                 = 0;
     private int current;
     private int lastIndex;

     private HorizonAdapter horizonAdapter;
     private HomeAdapter homeAdapter;

     private List<HomeBean.ImageList>   mImageList;       //轮播图
     private List<HomeBean.SortList>    mSortList;        //分类列表
     private List<HomeBean.BroadCast>   mBroadCastList;   //小广播
     private List<HomeBean.ServiceList> mServiceList;     //服务列表
     private ArrayList<ImageView>       viewPagerList = new ArrayList<ImageView>();

     private final int handler_type_viewpager = 0;
     private final int handler_type_broadCaet = 1;

     private Handler handler = new Handler() {
          @Override
          public void handleMessage(Message msg) {
               switch (msg.what) {
                    case handler_type_viewpager:
                         viewpager_home.setCurrentItem(viewpager_home.getCurrentItem() + 1);
                         handler.sendEmptyMessageDelayed(0, 3000);
                         break;
                    case handler_type_broadCaet:
                         mAutoTextView.setText(mBroadCastList.get(sCount).Contents);
                         sCount++;
                         if (sCount == (mBroadCastList.size() - 1)) {
                              sCount = 0;
                         }
                         handler.sendEmptyMessageDelayed(handler_type_broadCaet, 2000);
                         break;
               }
          }
     };

     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
          registerDataListener();
          initView();
          initDataImageList();
          initDataSortList();
          initDataBroadCastList();
          initDataServiceList();
          getHomeInfoData(false, Constants.Isupquery_Up,0);
          return fragmentView;
     }

     private void initView(){
          view2 = View.inflate(getActivity(), R.layout.xlistview_header2, null);
          view3 = View.inflate(getActivity(), R.layout.xlistview_header3, null);
          tv_current_location = (TextView) view2.findViewById(R.id.tv_current_location);//得到当前位置
          viewpager_home = (ViewPager) view2.findViewById(R.id.viewpager_home);
          line_home_shape = (LinearLayout) view2.findViewById(R.id.line_home_shape);
          line_message = (LinearLayout) view2.findViewById(R.id.line_message);//消息
          horizon_listview = (HorizontalListView) view3.findViewById(R.id.horizon_listview);
          listview_home = (XListView) fragmentView.findViewById(R.id.lv_home);
          mAutoTextView = (AutoTextView) view3.findViewById(R.id.tv_ad);
          mAutoTextView.setOnClickListener(this);//广告点击进入相应链接
          tv_current_location.setOnClickListener(this);
          line_message.setOnClickListener(this);
          listview_home.addHeaderView(view2);//添加头部
          listview_home.addHeaderView(view3);//添加头部
          listview_home.setFocusable(true);
          listview_home.setPullLoadEnable(true);
//        mAdapter = new ArrayAdapter<String>(mainActivity, R.layout.list_item, items);
//        lv_home.setAdapter(mAdapter);
//        lv_home.setPullLoadEnable(false);
//        lv_home.setPullRefreshEnable(false);
          listview_home.setXListViewListener(this);
//        lv_home.doAutoRefresh();
          listview_home.doAutoLoadMore();
     }

     private void initDataImageList(){
          viewpager_home.setAdapter(new PagerAdapter_home(getActivity(), viewPagerList));
          viewpager_home.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
               @Override
               public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               }

               @Override
               public void onPageSelected(int position) {
                    int index = position % mImageList.size();
                    line_home_shape.getChildAt(index).setEnabled(true);
                    line_home_shape.getChildAt(lastIndex).setEnabled(false);
                    lastIndex = index;
               }

               @Override
               public void onPageScrollStateChanged(int state) {
               }
          });
     }

     private void initDataSortList(){
          horizonAdapter = new HorizonAdapter(getActivity(), mSortList);
          horizon_listview.setAdapter(horizonAdapter);
          horizon_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    WebActivity.startActivity(BaseActivity.getActivity(), HttpConstants.childTypeListUrl + mSortList.get(i).SortListId
                             , "", WebActivity.OPEN_TYPE_GET, false);
               }
          });
     }

     private void initDataBroadCastList(){

     }

     private void initDataServiceList(){
          homeAdapter = new HomeAdapter(getActivity(), mServiceList);
          listview_home.setAdapter(homeAdapter);
          listview_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    WebActivity.startActivity(BaseActivity.getActivity(), HttpConstants.serviceDetailUrl + mServiceList.get(position).ServiceListId  + HttpConstants.serviceDetailUrlParam
                             , "", WebActivity.OPEN_TYPE_GET, false);
               }
          });
     }

     /**获取首页数据*/
     private void getHomeInfoData(boolean isFreshOrLoad,int isupquery,long serviceId){
          mIsFreshOrLoad = isFreshOrLoad;
          if(!mIsFreshOrLoad){
               if (isRefreshing) {
                    return;
               }
               isRefreshing = true;
          }else{
               if (isLoadingMore) {
                    return;
               }
               isLoadingMore = true;
          }
          Map<String, Object> params = new HashMap<String, Object>();
          params.put("serviceid", serviceId);
          params.put("isupquery", isupquery);
          params.put("count", Constants.loadListSize);
          LogicRequest.sharedInstance().getRequestListLogic("getHomeInfoData", HttpConstants.GetHoneInfo_URL, params, "GetHomeInfo",
                   BaseApplication.DataType.HomeInfoFail, BaseApplication.DataType.HomeInfoSuccess);
     }

     private void registerDataListener() {
          BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.HomeInfoFail, mDataListener);
          BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.HomeInfoSuccess, mDataListener);
     }

     private CustomFragmentHome.DataChangedListener mDataListener = new CustomFragmentHome.DataChangedListener();
     private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
          @Override
          public void onChange(BaseApplication.DataType type, Object data) {
               // TODO Auto-generated method stub
               switch (type) {
                    case HomeInfoFail:
                         showFailureResult();
                         break;
                    case HomeInfoSuccess:
                         getMessageContent((HomeBean) data);
                         break;
               }
          }
     }

     private void getMessageContent(HomeBean homeBean) {
          if(!mIsFreshOrLoad){   //下拉刷新
               imageListSuccessInfo(homeBean.ImageList);
               sortListSuccessInfo(homeBean.SortList);
               broadCastSuccessInfo(homeBean.BroadCast);
               serviceListSuccessInfo(homeBean.ServiceList);
          }else{                 //上拉
               serviceListSuccessInfoLoad(homeBean.ServiceList);
          }
     }

     /**处理轮播图数据*/
     private void imageListSuccessInfo(List<HomeBean.ImageList> imageList){
          if(imageList.size() != 0 && imageList != null){
               viewPagerList.clear();
               mImageList.clear();
               mImageList.addAll(imageList);
               for (int i = 0; i < imageList.size(); i++) {
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ImageLoader.getInstance().displayImage(imageList.get(i).ImageUrl, imageView);
                    viewPagerList.add(imageView);
                    //创建指示点
                    ImageView image = new ImageView(getActivity());
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
               current = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mImageList.size();
               viewpager_home.setCurrentItem(current);//设置当前页
               handler.sendEmptyMessageDelayed(handler_type_viewpager, 2000);//无限轮播
          }else{
               viewpager_home.setVisibility(View.GONE);
          }
     }

     /**处理分类列表数据*/
     private void sortListSuccessInfo(List<HomeBean.SortList> sortList){
          mSortList.clear();
          mSortList.addAll(sortList);
          if(horizon_listview != null &&mSortList.size() != 0){
               horizon_listview.setVisibility(View.VISIBLE);
          }else{
               horizon_listview.setVisibility(View.GONE);
          }
          horizonAdapter.notifyDataSetChanged();
     }

     /**处理小广播的数据*/
     private void broadCastSuccessInfo(List<HomeBean.BroadCast> broadCastList){
          mBroadCastList.clear();
          mBroadCastList.addAll(broadCastList);
          if(mBroadCastList != null && mBroadCastList.size() != 0){
               mAutoTextView.setVisibility(View.VISIBLE);
               handler.sendEmptyMessageDelayed(handler_type_broadCaet, 2000);//滚动条
          }else{
               mAutoTextView.setVisibility(View.GONE);
          }

     }

     /**处理服务列表的数据*/
     private void serviceListSuccessInfo(List<HomeBean.ServiceList> serviceList){
          listview_home.stopRefresh();
          mServiceList.clear();
          mServiceList.addAll(serviceList);
          isRefreshing = false;
          if(mServiceList != null && mServiceList.size() != 0){
               listview_home.setVisibility(View.VISIBLE);
          }else{
               listview_home.setVisibility(View.GONE);
          }
          listview_home.stopLoadMore(true);
          homeAdapter.notifyDataSetChanged();
     }

     /**处理服务列表的数据 加载更多*/
     private void serviceListSuccessInfoLoad(List<HomeBean.ServiceList> serviceList){
          listview_home.stopRefresh();
          isLoadingMore = false;
          if (serviceList.size() == 0) {
               ifNoMoreData(serviceList.size());
               return;
          }
          mServiceList.addAll(serviceList);
          if(mServiceList != null && mServiceList.size() != 0){
               listview_home.setVisibility(View.VISIBLE);
          }else{
               listview_home.setVisibility(View.GONE);
          }
          homeAdapter.notifyDataSetChanged();
     }

     /**判断是否要在进行加载更多*/
     private void ifNoMoreData(int count){
          if(count < Constants.loadListSize){
               listview_home.stopLoadMore(true);
          }
          isLoadingMore = false;
     }

     /**获取失败时*/
     private void showFailureResult() {
     }

     @Override
     public void onClick(View v) {
          switch (v.getId()) {
               case R.id.tv_current_location:
                    Intent intent = new Intent(getActivity(), LocationActivity.class);
                    startActivity(intent);
                    break;
               case R.id.line_message:
                    break;
               case R.id.tv_ad:
                    Intent intent1 = new Intent(getActivity(), HomeWebviewActivity.class);
                    intent1.putExtra("Links", mBroadCastList.get(sCount).Links);
                    startActivity(intent1);
                    break;
               default:
                    break;
          }
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

     @Override
     public void onRefresh() {
          getHomeInfoData(false, Constants.Isupquery_Up,0);
     }

     @Override
     public void onLoadMore() {
          getHomeInfoData(true, Constants.Isupquery_Down,mServiceList.get(mServiceList.size() - 1).ServiceListId);
     }
}
