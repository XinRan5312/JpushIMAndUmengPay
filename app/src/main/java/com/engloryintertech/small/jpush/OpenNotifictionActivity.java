package com.engloryintertech.small.jpush;

import android.view.View;
import cn.jpush.android.api.JPushInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.engloryintertech.small.activity.BaseActivity;

/**
 * Created by qixinh on 16/10/19.
 */
public class OpenNotifictionActivity extends BaseActivity {
    @Override
    protected void initViews() {
        TextView tv = new TextView(this);
        tv.setText("用户自定义打开的Activity");
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bundle = getIntent().getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            tv.setText("Title : " + title + "  " + "Content : " + content);
        }
        addContentView(tv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

    @Override
    public void onClick(View v) {

    }
}
