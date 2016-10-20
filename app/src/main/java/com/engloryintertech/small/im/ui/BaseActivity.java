package com.engloryintertech.small.im.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by qixinh on 16/10/13.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    protected abstract void initViews();

    protected <V extends View> V $(@IdRes int id){
        return (V) findViewById(id);
    }
}
