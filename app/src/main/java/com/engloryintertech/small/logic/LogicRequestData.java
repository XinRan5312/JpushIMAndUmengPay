package com.engloryintertech.small.logic;

import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.model.bean.UserBean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 逻辑层 数据
 * <p/>
 * 数据逻辑层 编辑请求数据
 * 逻辑层处理CallBck结果 并发送通知给相应的请求发起者
 */
public class LogicRequestData {

    public final String TAG = getClass().getName();
    public static LogicRequestData instance = null;

    public synchronized static LogicRequestData sharedInstance() {
        if (null == instance) {
            instance = new LogicRequestData();
        }
        return instance;
    }

    /**
     * 获取推荐内容
     */
    public void getRecommendationList(int mainid, int type, String RequestTag, BaseApplication.DataType fail,
                                      BaseApplication.DataType success, byte current) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mainid", mainid);
        params.put("articletype", type);
        LogicRequest.sharedInstance().getRequestHashMapListLogic("getRecommendationList", HttpConstants.Initialize_URL,
                params, RequestTag, fail, success, current);
    }

    /**第三方登录*/
    public void ThirdPartyLogin(UserBean userBean){
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put("registertype",userBean.getLoginType());
        params.put("thirdname",userBean.getUserName());
        params.put("thirdtoken",userBean.getUserToken());
        params.put("thirduid", userBean.getOpenId());
        params.put("thirdheadimg",userBean.getAvatarUrl());
        LogicRequest.sharedInstance().postRequestListLogic("ThirdPartyLogin", HttpConstants.Thirdlogin_URL, params, Constants.Http_Request_Tag_WXLogin,
                BaseApplication.DataType.ThirdLoginFail, BaseApplication.DataType.ThirdLoginSuccess);
    }

    /**上传图片*/
    public void upLoadImage(int upLoadType,File file){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("imagetype",upLoadType);
        Map<String, File> files = new HashMap<String, File>();
        files.put("File", file);
        LogicRequest.sharedInstance().postRequestUpFile("upLoadImage", HttpConstants.upLoadImageUrl, params, "image",
                files, Constants.Http_Request_Tag_UpLoadImage, BaseApplication.DataType.UpLoadImageFail,
                BaseApplication.DataType.UpLoadImageSuccess);
    }

}

