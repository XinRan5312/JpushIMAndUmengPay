package com.engloryintertech.small.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.engloryintertech.small.R;
import com.engloryintertech.small.activity.MainActivity;
import com.engloryintertech.small.activity.PersonalSettingActivity;
import com.engloryintertech.small.activity.TestGoToWebActivity;
import com.engloryintertech.small.adapter.GridAdapter_mine;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.model.bean.MineBean;
import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.tools.CustomImageLoader;
import com.engloryintertech.small.tools.ImageLoaderOptions;
import com.engloryintertech.small.ui.CircleImageView;
import com.engloryintertech.small.ui.CustomGridView;
import com.engloryintertech.small.webview.WebActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/17.
 */
public class FragmentMine extends Fragment implements View.OnClickListener {

    private View view;
    private CustomGridView gv_mine;
    private ArrayList<MineBean> list = new ArrayList<MineBean>();
    private int[] str = new int[]{R.string.mine_zhangben, R.string.mine_dingdan, R.string.mine_fuwu, R.string.mine_dongtai, R.string.mine_friends, R.string.mine_tuiguang};
    private int[] ima = new int[]{R.mipmap.mine_zhangben, R.mipmap.mine_dingdan, R.mipmap.mine_fuwu
            , R.mipmap.mine_feeds, R.mipmap.mine_friend, R.mipmap.mine_tuiguang};
    private MainActivity mainActivity;
    private GridAdapter_mine gridAdapter_mine;
    private RelativeLayout line_mine_money, line_mine_sport, line_mine_setting, line_mine_question,
            line_mine_about;
    private LinearLayout line_mine_head;
    private View mUserHeaderView;
    private CircleImageView userHeaderImage;
    private TextView my_header_number_textview,my_header_local_textview,my_header_personalsetting;
    private ImageView my_header_approve_personal_img,my_header_authority_img,my_header_numberapprove_img;
    private UserBean mUserBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initData();//初始化数据
        initView();//初始化控件
        setUserHeaderData();
        return view;
    }

    private void initData() {
        for (int i = 0; i < str.length; i++) {
            list.add(new MineBean(ima[i], str[i]));
        }
        mUserBean = UserDaoHelper.getInstance().getUserInfo();
    }

    private void initView() {
        my_header_personalsetting = (TextView)view.findViewById(R.id.my_header_personalsetting);
        if(mUserBean.getLoginType() != Constants.UserLoginType_Normal){
            my_header_personalsetting.setVisibility(View.GONE);
        }
        gv_mine = (CustomGridView) view.findViewById(R.id.gv_mine);
        line_mine_money = (RelativeLayout) view.findViewById(R.id.line_mine_money);
        line_mine_sport = (RelativeLayout) view.findViewById(R.id.line_mine_sport);
        line_mine_setting = (RelativeLayout) view.findViewById(R.id.line_mine_setting);
        line_mine_question = (RelativeLayout) view.findViewById(R.id.line_mine_question);
        line_mine_about = (RelativeLayout) view.findViewById(R.id.line_mine_about);
        line_mine_head = (LinearLayout) view.findViewById(R.id.line_mine_head);
        addUserHeaderView();
        my_header_personalsetting.setOnClickListener(this);
        line_mine_money.setOnClickListener(this);
        line_mine_sport.setOnClickListener(this);
        line_mine_setting.setOnClickListener(this);
        line_mine_question.setOnClickListener(this);
        line_mine_about.setOnClickListener(this);
        mainActivity = (MainActivity) getActivity();
    }

    /** 添加用户头部View*/
    private void addUserHeaderView() {
        mUserHeaderView = View.inflate(getActivity(), R.layout.my_header_layout, null);
        userHeaderImage = (CircleImageView)mUserHeaderView.findViewById(R.id.my_header_img);
        my_header_number_textview = (TextView)mUserHeaderView.findViewById(R.id.my_header_number_textview);
        my_header_local_textview = (TextView)mUserHeaderView.findViewById(R.id.my_header_local_textview);
        my_header_approve_personal_img = (ImageView)mUserHeaderView.findViewById(R.id.my_header_approve_personal_img);
        my_header_authority_img = (ImageView)mUserHeaderView.findViewById(R.id.my_header_authority_img);
        my_header_numberapprove_img = (ImageView)mUserHeaderView.findViewById(R.id.my_header_numberapprove_img);
        line_mine_head.addView(mUserHeaderView);
    }

    /** 添加用户头部数据*/
    private void setUserHeaderData() {
        CustomImageLoader.getInstance(getActivity()).displayImage(mUserBean.getAvatarUrl(), userHeaderImage,
                ImageLoaderOptions.User_Pic_Option());
        my_header_number_textview.setText(mUserBean.getTellPhone());
        my_header_local_textview.setText(mUserBean.getAddress());
        setApproveState(my_header_approve_personal_img, mUserBean.getAuthenticationState(), Constants.ApproveType_Personal);
        setApproveState(my_header_authority_img, mUserBean.getCertificationState(), Constants.ApproveType_Authority);
        setApproveState(my_header_numberapprove_img, mUserBean.getTellPhoneState(),Constants.ApproveType_Number);
    }

    /**认证状态*/
    private void setApproveState(ImageView approveImge,int approveState,int approveType){
        if(approveState == Constants.ApproveType_Yes){
            if(approveType == Constants.ApproveType_Personal){
                approveImge.setImageResource(R.mipmap.card_h);
            }else if(approveType == Constants.ApproveType_Authority){
                approveImge.setImageResource(R.mipmap.jiangbei_h);
            }else if(approveType == Constants.ApproveType_Number){
                approveImge.setImageResource(R.mipmap.phone_h);
            }
        }else{
            if(approveType == Constants.ApproveType_Personal){
                approveImge.setImageResource(R.mipmap.card_l);
            }else if(approveType == Constants.ApproveType_Authority){
                approveImge.setImageResource(R.mipmap.jiangbei_l);
            }else if(approveType == Constants.ApproveType_Number){
                approveImge.setImageResource(R.mipmap.phone_l);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        gridAdapter_mine = new GridAdapter_mine(getActivity(), list);
        gv_mine.setAdapter(gridAdapter_mine);
        gv_mine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startWebViewContent(HttpConstants.myBookUrl,WebActivity.OPEN_TYPE_GET);
                        break;
                    case 1:
                        startWebViewContent(HttpConstants.orderListUrl,WebActivity.OPEN_TYPE_GET);
                        break;
                    case 2:
                        startWebViewContent(HttpConstants.myServiceUrl,WebActivity.OPEN_TYPE_GET);
                        break;
                    case 3:
                        startWebViewContent("动态地址",WebActivity.OPEN_TYPE_GET);
                        break;
                    case 4:
                        startWebViewContent("好友地址",WebActivity.OPEN_TYPE_GET);
                        break;
                    case 5:
                        startWebViewContent("推广地址",WebActivity.OPEN_TYPE_GET);
                        break;
                    default:
                        break;
                }


            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    /**打开webview*/
    private void startWebViewContent(String url,int OPEN_TYPE){
        WebActivity.startActivity(getActivity(),url, "", OPEN_TYPE, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_header_personalsetting:
                mainActivity.startActivity(new Intent(mainActivity, PersonalSettingActivity.class));
                break;
            case R.id.line_mine_money:
                break;
            case R.id.line_mine_sport:
                break;
            case R.id.line_mine_setting:
                break;
            case R.id.line_mine_question:
                break;
            case R.id.line_mine_about:
                break;
            default:
                break;
        }
    }
}