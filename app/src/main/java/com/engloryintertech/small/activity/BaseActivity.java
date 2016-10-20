package com.engloryintertech.small.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.engloryintertech.small.R;
import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.global.findview.InjectFindView;
import com.engloryintertech.small.tools.CustomToast;
import com.engloryintertech.small.tools.FileUtils;
import com.engloryintertech.small.ui.TakePhotoPopWin;

import java.io.File;

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    public static Activity rootActivity;
    protected Bundle myBundle;
    private static TakePhotoPopWin takePhotoPopWin;
    protected static File mTmpFile = null;
    public static int mIndexJs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        myBundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (myBundle == null) {
            myBundle = new Bundle();
        }
        rootActivity = this;
    }

    public void setContentView(View view, boolean autoInject) {
        super.setContentView(view);
        if (autoInject) {
            InjectFindView.inject(this);
        }
    }

    public void setContentView(int layoutResID, boolean autoInject) {
        Log.e("Frangky", "2-" + layoutResID);
        final View content = getLayoutInflater().inflate(layoutResID, null);
        setContentView(content, autoInject);
    }

    public void setContentViewStandard(int layoutResID) {
        super.setContentView(layoutResID);
        InjectFindView.inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, true);
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, true);
    }

    public static Activity getActivity() {
        return rootActivity;
    }

    /**
     * 有关activity需要的基础方法
     * （initViews，是必须的，只要继承就实现）
     * 3个基础方法 initViews、initData、initEvents 父类方法中的onCreateInstance已经调用，子类只负责重写
     *
     * @return
     * @method initViews    初始化View(findViewById等)
     * initData     初始化数据 (setText，getIntent Bundled，初始化点击事件 等)
     * initEvents   初始化事件
     * getDataFromWeb,getDataFromWeb 获取数据(从网络获取数据，从本地获取数据)
     */
    protected abstract void initViews();

    protected void initEvents() {
    }

    protected void initData() {
    }

    protected void getDataFromWeb() {

    }

    protected void getDataFromDBHelper() {

    }

    /**
     * @param data 需要传值传，不需要时 传null
     *             intentKey  Intent Key(在Constans中定义全局变量)
     * @method 启动某个Actiivty
     */
    protected void startActivityWithData(Bundle data, Class<?> clas, int enterAnim, int exitAnim) {
        Intent intent = new Intent(this, clas);
        if (null != data) {
            intent.putExtras(data);
        }
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * startActivityForResult
     *
     * @param data 需要传值传，不需要时 传null
     *             intentKey  Intent Key(在Constans中定义全局变量)
     *             requestCode  请求码 (在Constans中定义全局变量)
     */
    protected void startActivityForResultWithData(Bundle data, Class<?> clas, int enterAnim,
                                                  int exitAnim, int requestCode) {
        Intent intent = new Intent(this, clas);
        if (null != data) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(enterAnim, exitAnim);
    }

    public void showDialog(Context context, String data) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setOnCancelListener(progressDialogCancerlListner);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(true);
        }
        if (data.equals("登录")) {
            progressDialog.setMessage(BaseApplication.getApplication().getString(R.string.logining));
        } else {
            progressDialog.setMessage(BaseApplication.getApplication().getString(R.string.loading));
        }
        progressDialog.show();
    }

    private DialogInterface.OnCancelListener progressDialogCancerlListner = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            progressDialog.dismiss();
        }
    };

    public void dismissDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 返回
     */
    public void BackBtnClickListen(View view) {
    }

    /**
     * 只要是点击事件 就重写该方法 在xml中onClick定义 不需要在Activity中setListen
     * 在这个位置判断是否是快速点击
     */
    public void AppBtnClickListen(View view) {
//		if(Common.isClickDoubleShort())return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    /**
     * 1.若页面是直接由Activity实现的 直接统计时间、页面跳转
     * 2.若页面是使用FragmentActivity + Fragment实现的，
     * 在 FragmentActivity 中统计时长
     * 在其包含的 Fragment 中统计页面跳转
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (myBundle != null) {
            outState.putAll(myBundle);
        }
        super.onSaveInstanceState(outState);
    }

    /**选择图片 或 拍照*/
    public static void showPopFormBottom(int index,int viewId) {
        mIndexJs = index;
        takePhotoPopWin = new TakePhotoPopWin(getActivity(), onClickListener);
        takePhotoPopWin.showAtLocation(getActivity().findViewById(viewId), Gravity.CENTER, 0, 0);
    }

    private static View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_take_photo:
                    takePhotoPopWin.dismiss();
                    takePicture();
                    break;
                case R.id.pop_pick_photo:
                    takePhotoPopWin.dismiss();
                    ChooseAlbum();
                    break;
            }
        }
    };

    protected static void takePicture() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            isShowCamera();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    Constants.Request_read_camera_permission);
        }
    }

    /**是否显示调用相机拍照*/
    protected static void isShowCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            mTmpFile = FileUtils.createTmpFile(getActivity());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
            cameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            getActivity().startActivityForResult(cameraIntent, Constants.Request_Camera);
        }else{
            CustomToast.showToast("No system camera found");
        }
    }
    /**选择相册*/
    protected static void ChooseAlbum() {
        mTmpFile = FileUtils.createTmpFile(getActivity());
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(intent, Constants.Request_IMAGE);
    }
}

