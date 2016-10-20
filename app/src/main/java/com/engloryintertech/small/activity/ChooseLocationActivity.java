package com.engloryintertech.small.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.engloryintertech.small.R;

/**
 * 选择服务地址
 */
public class ChooseLocationActivity extends BaseActivity {

    private ImageView ima_location_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location,false);
        initViews();
    }

    @Override
    protected void initViews() {
        ima_location_back = (ImageView) findViewById(R.id.ima_location_back);
        ima_location_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ima_location_back:
                finish();
                break;
        }
    }

}
