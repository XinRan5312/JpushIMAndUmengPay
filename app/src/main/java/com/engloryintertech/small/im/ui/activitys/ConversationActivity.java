package com.engloryintertech.small.im.ui.activitys;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.engloryintertech.small.R;
import com.engloryintertech.small.im.DemoContext;
import com.engloryintertech.small.im.XinRanApp;

import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 会话页面
 * 1，设置 ActionBar title
 * 2，加载会话页面
 * 3，push 和 通知 判断
 * Created by qixinh on 16/10/13.
 */
public class ConversationActivity extends BaseRongActivity {

    private TextView mTitle;
    private RelativeLayout mBack;

    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        Intent intent = getIntent();

        setActionBar();

        getIntentDate(intent);

        isReconnect(intent);
    }

    @Override
    protected void initViews() {

    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    @SuppressLint("LongLogTag")
    private void getIntentDate(Intent intent) {
        Log.e("com.capping.xinran.qrongim", mTargetId + "==" + mTargetId);
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("targetIds");
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        enterFragment(mConversationType, mTargetId);
        setActionBarTitle(mTargetId);
    }


    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    @SuppressLint("LongLogTag")
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId) {
        Log.e("com.capping.xinran.qrongim", "token3:");
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);
    }
    /**
     * 判断消息是否是 push 消息
     */
    @SuppressLint("LongLogTag")
    private void isReconnect(Intent intent) {


        String token = null;

        if (DemoContext.getInstance() != null) {
            Log.e("com.capping.xinran.qrongim", "token:" + token);
            token = DemoContext.getInstance().getSharedPreferences().getString("DEMO_TOKEN", "default");
        }

        //push或通知过来
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {

            //通过intent.getData().getQueryParameter("push") 为true，判断是否是push消息
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {
                Log.e("com.capping.xinran.qrongim", "token1:" + token);
                reconnect(token);
            } else {
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
                    Log.e("com.capping.xinran.qrongim", "token2:" + token);
                    reconnect(token);
                } else {
                    enterFragment(mConversationType, mTargetId);
                }
            }
        }
    }

    /**
     * 设置 actionbar 事件
     */
    private void setActionBar() {

        mTitle = (TextView) findViewById(R.id.txt1);
        mBack = (RelativeLayout) findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置 actionbar title
     */
    private void setActionBarTitle(String targetid) {

        mTitle.setText(targetid);
    }

    /**
     * 重连
     *
     * @param token
     */
    private void reconnect(String token) {

        if (getApplicationInfo().packageName.equals(XinRanApp.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {

                }

                @Override
                public void onSuccess(String userId) {

                    enterFragment(mConversationType, mTargetId);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                }
            });
        }
    }

    @Override
    public void onClick(View view) {

    }
}