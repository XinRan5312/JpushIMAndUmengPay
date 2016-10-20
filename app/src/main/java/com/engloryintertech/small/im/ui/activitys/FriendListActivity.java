package com.engloryintertech.small.im.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.engloryintertech.small.R;

import com.engloryintertech.small.im.db.DBManager;
import com.engloryintertech.small.im.db.Friend;
import com.engloryintertech.small.im.ui.BaseActivity;
import com.engloryintertech.small.im.ui.adapters.FriendListAdapter;
import com.engloryintertech.small.im.utils.ConversationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qixinh on 16/10/18.
 *  * userid=6
 RongCloudToken=tlHrtOP22d4VVU7sbA+4sIr9U3R+cIhwHG1UcaMNBmzDeTfTt1Y3PpkwZRCuPH4nNEdzj1t3iJmUY7LdXdZ6fA==
 ___________
 userid=8
 RongCloudToken=pb3IX/wPjmAKI7h1LCc662BztzuEHMyJOwq9AUaNAR1KWyMvfzJyv+YIb+CG1T+9K5bKwgpN0iQqGPSdWGIENg==
 ___________
 userid=10
 RongCloudToken=XeZZvVWsB6ULHT5b6AQJjGBztzuEHMyJOwq9AUaNAR1KWyMvfzJyv8pWpOJYUhNFaeWY+J0ri+EqGPSdWGIENg==
 ___________
 userid=26
 RongCloudToken=4Br/1VBmnsUYd1IcXL2COWBztzuEHMyJOwq9AUaNAR1KWyMvfzJyv6rObMFECx4G0Si2x4bXJbEqGPSdWGIENg==
 ___________
 userid=27
 RongCloudToken=B9pVwr84KuanpxeIXIBkjzeoU/S93+FrloCp3t3UuRm60QvuzvHQ2SCHAuOzXLeWxYUqNBY8pEI=
 */
public class FriendListActivity extends BaseRongActivity {

    private ListView mListView;
    private List<Friend> mListData;
    private FriendListAdapter mAdapter;
    /**
     * 好友列表的 mFriendListAdapter
     */
    private FriendListAdapter mFriendListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        //调用的时候要异步 mListData= DBManager.getInstance(this).getDaoSession().getFriendDao().loadAll();
        if(mListData==null||mListData.size()==0){
             mListData=getDataFromNet();
        }
        mAdapter=new FriendListAdapter(this,mListData);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_list_friend);
        mListView= (ListView) findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    startFriendConversation(mListData.get(position));

            }
        });
    }

    private void startFriendConversation(Friend friend) {
        if(friend!=null)
        ConversationUtils.startOneConversation(this,friend.getUserId(),friend.getName());
    }

    @Override
    public void onClick(View v) {

    }

    public List<Friend> getDataFromNet() {
        List<Friend> list=new ArrayList<>();
        Friend friend=new Friend();
        friend.setUserId("8");
        friend.setName("Xin");
        friend.setPortraitUri("http://img5q.duitang.com/uploads/item/201408/18/20140818113623_JwLPs.thumb.224_0.jpeg");
        list.add(friend);

        Friend friend1=new Friend();
        friend1.setUserId("10");
        friend1.setName("Ran");
        friend1.setPortraitUri("http://img5q.duitang.com/uploads/item/201408/18/20140818113623_JwLPs.thumb.224_0.jpeg");
        list.add(friend1);

        Friend friend2=new Friend();
        friend2.setUserId("26");
        friend2.setName("Join");
        friend2.setPortraitUri("http://img5q.duitang.com/uploads/item/201408/18/20140818113623_JwLPs.thumb.224_0.jpeg");
        list.add(friend2);
        if(list.size()>0){
            return list;
        }
// 调用的时候要异步  下载完成后保存到数据库    DBManager.getInstance(this).getDaoSession().getFriendDao().insert()
        return Collections.emptyList();
    }
}
