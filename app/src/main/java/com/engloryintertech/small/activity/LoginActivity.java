package com.engloryintertech.small.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.adapter.page.PagerAdapter_login;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.AccessTokenKeeper;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.fragment.FragmentLogin;
import com.engloryintertech.small.fragment.FragmentRegister;
import com.engloryintertech.small.logic.LogicRequest;
import com.engloryintertech.small.map.GetLocation;
import com.engloryintertech.small.notificationceter.NotifyDispatcher;
import com.engloryintertech.small.tools.Common;
import com.engloryintertech.small.tools.CustomImageLoader;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.Tools;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

import java.util.ArrayList;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = getClass().getName();
    private ViewPager viewpager_login;
    private ImageView ima_wx;
    private TextView tv_login;
    private TextView tv_register;
    private View view_login;
    private View view_register;
    private FragmentManager fragmentManager;//fragment管理者
    private FragmentLogin fragmentLogin = new FragmentLogin();
    private ArrayList<ImageView> list = new ArrayList<ImageView>();
    private int[] ima = new int[]{R.drawable.xiazai, R.drawable.xiazai, R.drawable.xiazai};
    private String[] ima_url = new String[2];
    private int lastIndex;
    private LinearLayout line_shape;
    private int current;
    private AuthInfo mAuthInfo;
    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    public static SsoHandler mSsoHandler;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    public static Oauth2AccessToken mAccessToken;
    private DataChangedListener mDataListener = new DataChangedListener();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    viewpager_login.setCurrentItem(viewpager_login.getCurrentItem() + 1);
                    handler.sendEmptyMessageDelayed(0, 2000);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login, false);
        Tools.error(TAG + "分辨率", getString(R.string.test) + "");
        registerDataListener();
        fragmentManager = getSupportFragmentManager();
        // 创建微博实例
        //mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        mAuthInfo = new AuthInfo(this, Constants.WB_APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(this, mAuthInfo);
        // 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
        // 第一次启动本应用，AccessToken 不可用
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        initViews();
        initData();
        getViewPager();
        initInterface();
        new GetLocation(getApplicationContext()).getLocation();//获取定位信息
        isStartMain();
    }

    /**判断是登录 还是 进入首页*/
    private void isStartMain(){
        if(UserDaoHelper.getInstance().isExistUser()){
            startActivity(new Intent(getActivity(), MainActivity.class));
            finish();
        }
    }

    //初始化接口
    private void initInterface() {
        LogicRequest.sharedInstance().getRequestListLogic(TAG, HttpConstants.Initialize_URL, "InitializeTag", BaseApplication.DataType.InitializeFail
                , BaseApplication.DataType.InitializeSuccess);
    }

    private void registerDataListener() {
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.InitializeFail, mDataListener);
        BaseApplication.getApplication().registerDataListener(BaseApplication.DataType.InitializeSuccess, mDataListener);
    }

    private void getViewPager() {
        viewpager_login.setAdapter(new PagerAdapter_login(this, list));
        current = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ima.length;
        viewpager_login.setCurrentItem(current);//设置当前页
        handler.sendEmptyMessageDelayed(0, 2000);//无限轮播
        viewpager_login.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int index = position % ima.length;
                line_shape.getChildAt(index).setEnabled(true);
                line_shape.getChildAt(lastIndex).setEnabled(false);
                lastIndex = index;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //获取事务
    private void getTransaction(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void initViews() {
        viewpager_login = (ViewPager) findViewById(R.id.viewpager_login);
        tv_login = (TextView) findViewById(R.id.tv_login);//登录
        tv_register = (TextView) findViewById(R.id.tv_register);//注册
        view_login = (View) findViewById(R.id.view_login);
        view_register = (View) findViewById(R.id.view_register);
        line_shape = (LinearLayout) findViewById(R.id.line_shape);
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        getTransaction(fragmentLogin);//初始化获取登录界面
    }

    @Override
    protected void initData() {
        for (int i = 0; i < ima.length; i++) {
            ImageView imageView = new ImageView(this);
//            imageView.setBackgroundResource(ima[i]);
            CustomImageLoader.getInstance(this).displayImage("http://192.168.199.30:27002/resources/20160902/80/1339441889A301A511.png_300A400A0A100.png", imageView);
            imageView.setImageResource(ima[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            list.add(imageView);
            //创建指示点
            ImageView image = new ImageView(this);
            image.setBackgroundResource(R.drawable.point_bg);
            image.setImageResource(R.drawable.point_bg);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 15;
            params.gravity = Gravity.CENTER_HORIZONTAL;
            image.setLayoutParams(params);
            line_shape.addView(image);
            if (i == 0) {
                image.setEnabled(true);
            } else {
                image.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                view_login.setVisibility(View.VISIBLE);
                view_register.setVisibility(View.GONE);
                getTransaction(fragmentLogin);
                break;
            case R.id.tv_register:
                view_login.setVisibility(View.GONE);
                view_register.setVisibility(View.VISIBLE);
                getTransaction(new FragmentRegister());
                break;
            default:
                CustomToast.showToast("无效按钮");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private class DataChangedListener implements NotifyDispatcher.IDataSourceListener<BaseApplication.DataType, Object> {
        @Override
        public void onChange(BaseApplication.DataType type, Object data) {
            // TODO Auto-generated method stub
            switch (type) {
                case InitializeFail:
                    break;
                case InitializeSuccess:
//                    ArrayList<StartPageBean.ImageList> imageLists = (ArrayList<StartPageBean.ImageList>) data;
//                    for (int i = 0; i < imageLists.size(); i++) {
//                        ima_url[i] = imageLists.get(i).ImageUrl;
//                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.getApplication().unRegisterDataListener(mDataListener);
    }
}