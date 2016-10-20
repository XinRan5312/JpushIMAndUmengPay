package com.engloryintertech.small.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.fragment.CustomFragmentHome;
import com.engloryintertech.small.fragment.FragmentFind;
import com.engloryintertech.small.fragment.FragmentHome;
import com.engloryintertech.small.fragment.FragmentMine;
import com.engloryintertech.small.tools.CustomToast;

/**
 * 首屏
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = getClass().getName();
    private ImageView ima_shou;
    private ImageView ima_find;
    private ImageView ima_user;
    private TextView tv_shou;
    private TextView tv_find;
    private TextView tv_user;
    private FragmentHome fragmentHome = new FragmentHome();//首页
//    private CustomFragmentHome fragmentHome = new CustomFragmentHome();//首页
    private FragmentFind fragmentFind = new FragmentFind();//搜索发现
    private FragmentMine fragmentMine = new FragmentMine();//我的界面
    private FrameLayout frame_main;
    private FragmentManager fragmentManager;
    private Fragment mContent;
    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main,false);
        fragmentManager = getSupportFragmentManager();
        initViews();
    }

    @Override
    protected void initViews() {
        ima_shou = (ImageView) findViewById(R.id.ima_shou);
        ima_find = (ImageView) findViewById(R.id.ima_find);
        ima_user = (ImageView) findViewById(R.id.ima_user);
        tv_shou = (TextView) findViewById(R.id.tv_shou);
        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_user = (TextView) findViewById(R.id.tv_user);
        frame_main = (FrameLayout) findViewById(R.id.frame_main);//帧布局放置fragment
        ima_shou.setOnClickListener(this);
        ima_find.setOnClickListener(this);
        ima_user.setOnClickListener(this);
        tv_shou.setOnClickListener(this);
        tv_find.setOnClickListener(this);
        tv_user.setOnClickListener(this);
        initContent();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_shou:
                getShou();
                break;
            case R.id.ima_find:
                getFind();
                break;
            case R.id.ima_user:
                getUser();
                break;
            case R.id.tv_shou:
                getShou();
                break;
            case R.id.tv_find:
                getFind();
                break;
            case R.id.tv_user:
                getUser();
                break;
            default:
                break;
        }
    }

    private void getUser() {
        ima_shou.setImageResource(R.mipmap.home_l);
        ima_find.setImageResource(R.mipmap.find_l);
        ima_user.setImageResource(R.mipmap.user_h);
        tv_shou.setTextColor(Color.rgb(152, 161, 164));
        tv_find.setTextColor(Color.rgb(152, 161, 164));
        tv_user.setTextColor(Color.rgb(241, 184, 97));
        getTransaction(fragmentMine);
    }

    private void getFind() {
        ima_shou.setImageResource(R.mipmap.home_l);
        ima_find.setImageResource(R.mipmap.find_h);
        ima_user.setImageResource(R.mipmap.user_l);
        tv_shou.setTextColor(Color.rgb(152, 161, 164));
        tv_find.setTextColor(Color.rgb(241, 184, 97));
        tv_user.setTextColor(Color.rgb(152, 161, 164));
        getTransaction(fragmentFind);
    }

    private void getShou() {
        ima_shou.setImageResource(R.mipmap.home_h);
        ima_find.setImageResource(R.mipmap.find_l);
        ima_user.setImageResource(R.mipmap.user_l);
        tv_shou.setTextColor(Color.rgb(241, 184, 97));
        tv_find.setTextColor(Color.rgb(152, 161, 164));
        tv_user.setTextColor(Color.rgb(152, 161, 164));
        getTransaction(fragmentHome);
    }

    //获取事务
    private void getTransaction(Fragment fragment) {
        if (mContent != fragment) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!fragment.isAdded()) {
                transaction.hide(mContent).add(R.id.frame_main, fragment).commit();//隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(fragment).commit();
            }
            mContent = fragment;
        }
    }

    /**
     * 初始化显示内容
     **/
    private void initContent() {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.frame_main, fragmentHome);
        mContent = fragmentHome;
        transaction.commit();
    }

    /**
     * 显示进度条
     */
    @Override
    public void showDialog(Context context, String data) {
        super.showDialog(context, data);
    }

    /**
     * 关闭进度条
     */
    @Override
    public void dismissDialog() {
        super.dismissDialog();
    }

    @Override
    public void onBackPressed() {
        if (time <= 0) {
            CustomToast.showToast("再按一次退出");
            time = System.currentTimeMillis();
        } else {
            long currentTimeMillis2 = System.currentTimeMillis();
            if (currentTimeMillis2 - time < 1000) {
                finish();
                System.exit(0);
            } else {
                CustomToast.showToast("再按一次退出");
                time = currentTimeMillis2;
            }
        }
    }
}