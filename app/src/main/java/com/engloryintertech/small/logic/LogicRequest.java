package com.engloryintertech.small.logic;

import android.graphics.Bitmap;

import com.engloryintertech.small.application.BaseApplication;
import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.datas.UserDaoHelper;
import com.engloryintertech.small.model.bean.HomeBean;
import com.engloryintertech.small.model.bean.MallPayBean;
import com.engloryintertech.small.model.bean.MineLocationBean;
import com.engloryintertech.small.model.bean.StartPageBean;
import com.engloryintertech.small.model.bean.UserBean;
import com.engloryintertech.small.nets.callback.BitmapCallback;
import com.engloryintertech.small.nets.callback.FileCallBack;
import com.engloryintertech.small.nets.callback.StringCallback;
import com.engloryintertech.small.nets.logic.HttpRequestLogic;
import com.engloryintertech.small.nets.utils.ErrorMessageFactory;
import com.engloryintertech.small.tools.Tools;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 逻辑层
 * <p/>
 * 逻辑层发起请求调用网络层
 * 逻辑层处理CallBck结果 并发送通知给相应的请求发起者
 */
public class LogicRequest {

    private final String TAG = getClass().getName();
    public static LogicRequest instance = null;

    public synchronized static LogicRequest sharedInstance() {
        if (null == instance) {
            instance = new LogicRequest();
        }
        return instance;
    }

    /**
     * get 不传递参数
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void getRequestLogic(String TAG, String url, String requestTag,
                                BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicCallBack callBack = new LogicCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).getWebService(url,
                requestTag, callBack);
    }

    public void getRequestListLogicNoHeader(String TAG, String url, String requestTag,
                                    BaseApplication.DataType fail, BaseApplication.DataType success) {
        HttpRequestLogic.sharedInstance(TAG).getWebServiceNoheader(url, requestTag, new LogicListCallBack(fail, success));
    }

    public void getRequestListLogicNoHeader(String TAG, String url, String requestTag,Map<String, Object> params,
                                            BaseApplication.DataType fail, BaseApplication.DataType success) {
        HttpRequestLogic.sharedInstance(TAG).getWebServiceWithParams(url, params,
                requestTag, new LogicListCallBack(fail, success));
    }

    /**
     * get 不传递参数
     * List
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void getRequestListLogic(String TAG, String url, String requestTag,
                                    BaseApplication.DataType fail, BaseApplication.DataType success) {
        HttpRequestLogic.sharedInstance(TAG).getWebService(url, requestTag, new LogicListCallBack(fail, success));
    }

    /**
     * get 传递参数
     * String
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void getRequestStringLogic(String TAG, String url, Map<String, Object> params, String requestTag,
                                      BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicCallBack callBack = new LogicCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).getWebServiceWithParams(url, params,
                requestTag, callBack);
    }

    /**
     * get 传递参数
     * HashMapList
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void getRequestHashMapListLogic(String TAG, String url, Map<String, Object> params, String requestTag,
                                           BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicHashMapListCallBack callBack = new LogicHashMapListCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).getWebServiceWithParams(url, params,
                requestTag, callBack);
    }

    /**
     * get 传递参数
     * List
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void getRequestListLogic(String TAG, String url, Map<String, Object> params, String requestTag,
                                    BaseApplication.DataType fail, BaseApplication.DataType success) {
        HttpRequestLogic.sharedInstance(TAG).getWebServiceWithParams(url, params,
                requestTag, new LogicListCallBack(fail, success));
    }

    /**
     * post 不传递参数
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestLogic(String TAG, String url, String requestTag,
                                 BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicCallBack callBack = new LogicCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).postWebService(url,
                requestTag, callBack);
    }

    /**
     * post 单个传递参数
     * String
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestStringLogic(String TAG, String url, String ParamsKey, String ParamsValue, String requestTag,
                                       BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicCallBack callBack = new LogicCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParam(url, ParamsKey, ParamsValue,
                requestTag, callBack);
    }

    /**
     * post 单个传递参数
     * HashMapList
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestHashMapListLogic(String TAG, String url, String ParamsKey, String ParamsValue, String requestTag,
                                            BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicHashMapListCallBack callBack = new LogicHashMapListCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParam(url, ParamsKey, ParamsValue,
                requestTag, callBack);
    }

    /**
     * post 单个传递参数
     * List
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestListLogic(String TAG, String url, String ParamsKey, String ParamsValue, String requestTag,
                                     BaseApplication.DataType fail, BaseApplication.DataType success) {
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParam(url, ParamsKey, ParamsValue,
                requestTag, new LogicListCallBack(fail, success));
    }

    /**
     * post 多个传递参数
     * String
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestStringLogic(String TAG, String url, Map<String, Object> params, String requestTag,
                                       BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicCallBack callBack = new LogicCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParams(url, params,
                requestTag, callBack);
    }

    /**
     * post 多个传递参数
     * HashMapList
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestHashMapListLogic(String TAG, String url, Map<String, Object> params, String requestTag,
                                            BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicHashMapListCallBack callBack = new LogicHashMapListCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParams(url, params, requestTag, callBack);
    }

    /**
     * post 多个传递参数
     * List
     *
     * @param requestTag 请求TAG
     *                   LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestListLogic(String TAG, String url, Map<String, Object> params, String requestTag,
                                     BaseApplication.DataType fail, BaseApplication.DataType success) {
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParams(url, params,
                requestTag, new LogicListCallBack(fail, success));
    }

    /**
     * post 文件下载 不传递参数
     *
     * @param destFileDir、destFileName 文件地址、文件名
     *                                 requestTag   请求TAG
     *                                 LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestDownFile(String TAG, String url, String destFileDir, String destFileName, String requestTag) {
        HttpRequestLogic.sharedInstance(TAG).postWebService(url,
                requestTag, new LogicFileCallBack(destFileDir, destFileName));
    }

    /**
     * post 文件下载 传递参数
     *
     * @param destFileDir、destFileName 文件地址、文件名
     *                                 params       要传递的参数
     *                                 requestTag   请求TAG
     *                                 LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestDownFile(String TAG, String url, Map<String, Object> params, String destFileDir, String destFileName,
                                    String requestTag) {
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParams(url, params,
                requestTag, new LogicFileCallBack(destFileDir, destFileName));
    }

    /**
     * post 文件上传 不传递参数
     *
     * @param fileKey 在Constant中全局变量
     *                requestTag   请求TAG
     *                LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestUpFile(String TAG, String url, String fileKey, Map<String, File> files, String requestTag,
                                  BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicCallBack callBack = new LogicCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).upLoadFile(url,
                fileKey, files, requestTag, callBack);
    }

    /**
     * post 文件上传 传递参数
     *
     * @param fileKey 在Constant中全局变量
     *                requestTag   请求TAG
     *                LogTagHeader 打印头（方便查看Log日志）
     */
    public void postRequestUpFile(String TAG, String url, Map<String, Object> params, String fileKey, Map<String, File> files,
                                  String requestTag, BaseApplication.DataType fail, BaseApplication.DataType success, byte currentTag) {
        LogicCallBack callBack = new LogicCallBack(fail, success);
        callBack.setCurrentTag(currentTag);
        HttpRequestLogic.sharedInstance(TAG).upLoadFileWithParams(url, fileKey, files, params,
                requestTag, callBack);
    }

    public void postRequestUpFile(String TAG, String url, Map<String, Object> params, String fileKey, Map<String, File> files,
                                  String requestTag, BaseApplication.DataType fail, BaseApplication.DataType success) {
        LogicListCallBack callBack = new LogicListCallBack(fail, success);
        HttpRequestLogic.sharedInstance(TAG).upLoadFileWithParams(url, fileKey, files, params,
                requestTag, callBack);
    }

    public void postRequestBitmapCallBack(String TAG, String url, Map<String, Object> params, String requestTag) {
        HttpRequestLogic.sharedInstance(TAG).postWebServiceWithParams(url, params,
                requestTag, new LogicBitmapCallBack());
    }

    public class LogicCallBack extends StringCallback {
        private BaseApplication.DataType failType;
        private BaseApplication.DataType successType;
        private byte mCurrentTag;

        public void setCurrentTag(byte tag) {
            this.mCurrentTag = tag;
        }

        LogicCallBack(BaseApplication.DataType failType, BaseApplication.DataType successType) {
            this.failType = failType;
            this.successType = successType;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            BaseApplication.getApplication().notifyDataChanged(failType, ErrorMessageFactory.createMessage(e));
        }

        @Override
        public void onResponse(String response, int id) {
            analysisStringJsonOnResponse(failType, successType, response, mCurrentTag);
        }
    }

    /**
     * 解析Json {"Result":"Success","Message":""}
     */
    private void analysisStringJsonOnResponse(BaseApplication.DataType failType, BaseApplication.DataType successType, String response,
                                              byte mCurrentTag) {
        try {
            if (new JSONObject(response).has("Result") &&
                    new JSONObject(response).getString("Result").equals("Success")) {
                if (new JSONObject(response).has("UserInfo")) {
                    dealWithStringOnResponse(successType, new JSONObject(response).get("UserInfo"), mCurrentTag);
                } else {
                    BaseApplication.getApplication().notifyDataChanged(failType, "未知错误");
                }
            } else {
                if (new JSONObject(response).has("UserInfo")) {
                    BaseApplication.getApplication().notifyDataChanged(failType, new JSONObject(response).get("UserInfo"));
                } else {
                    BaseApplication.getApplication().notifyDataChanged(failType, "未知错误");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.debugger(TAG, "analysisStringJsonOnResponse e : " + e.getMessage().toString());
            BaseApplication.getApplication().notifyDataChanged(failType, "未知错误");
        }
    }

    /**
     * 在这里区分好，是哪个类型的请求，然后根据不同的请求，解析出不同的json并存储数据，
     * “Message” 数据的解析在所属类型的DaoHepler中
     * 然后通过notify 把解析出的对象给请求者
     */
    private void dealWithStringOnResponse(BaseApplication.DataType successType, Object data, byte mCurrentTag) {
        switch (successType) {
            case Success:
                BaseApplication.getApplication().notifyDataChanged(successType);
                break;
            case SaveUserSuccess:
                BaseApplication.getApplication().notifyDataChanged(successType, data);
                break;
        }
    }

    /**
     * 用于列表请求，请求结果需要判断HashMap
     */
    public class LogicHashMapListCallBack extends StringCallback {
        private BaseApplication.DataType failType;
        private BaseApplication.DataType successType;
        private byte mCurrentTag;

        public void setCurrentTag(byte tag) {
            this.mCurrentTag = tag;
        }

        LogicHashMapListCallBack(BaseApplication.DataType failType, BaseApplication.DataType successType) {
            this.failType = failType;
            this.successType = successType;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            HashMap<Byte, String> map = new HashMap<>();
            map.put(mCurrentTag, ErrorMessageFactory.createMessage(e));
            BaseApplication.getApplication().notifyDataChanged(failType, map);
        }

        @Override
        public void onResponse(String response, int id) {
            dealWithHashMapListOnResponse(successType, response, mCurrentTag);
        }
    }

    /**
     * 用于其他List请求，请求结果不需要判断HashMap
     */
    public class LogicListCallBack extends StringCallback {
        private BaseApplication.DataType failType;
        private BaseApplication.DataType successType;

        LogicListCallBack(BaseApplication.DataType failType, BaseApplication.DataType successType) {
            this.failType = failType;
            this.successType = successType;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Tools.error(TAG, e.toString() + "");
            BaseApplication.getApplication().notifyDataChanged(failType, ErrorMessageFactory.createMessage(e));

        }

        @Override
        public void onResponse(String response, int id) {
            dealWithListOnResponse(successType, response);
        }
    }

    /**
     * 解析Json     {"List":[]}
     * 判断HashMap
     * 区分好，是哪个类型的请求，然后根据不同的请求，解析出不同的json并存储数据，
     * 然后通过notify 把解析出的对象给请求者
     * 数据的解析在所属类型的DaoHepler中
     */
    private void dealWithHashMapListOnResponse(BaseApplication.DataType successType, Object data, byte mCurrentTag) {
        switch (successType) {
            case Success:
                HashMap<Byte, List<Object>> map = new HashMap<>();
                map.put(mCurrentTag, dealWithChannelRecommendData(data));
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.Success, map);
                break;
        }
    }

    /**
     * 解析Json  {"List":[]}
     * 区分好，是哪个类型的请求，然后根据不同的请求，解析出不同的json并存储数据，
     * 然后通过notify 把解析出的对象给请求者
     * 数据的解析在所属类型的DaoHepler中
     */
    private void dealWithListOnResponse(BaseApplication.DataType successType, Object data) {
        switch (successType) {
            case Success:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.Success, null);
                break;
            case RegisterSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.RegisterSuccess, data);
                break;
            case CheckveriSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.CheckveriSuccess, data);
                break;
            case LoginSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.LoginSuccess, data);
                break;
            case ResetSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.ResetSuccess, data);
                break;
            case GetUserInfoSuccess:
                dealWithGetUserInfo(data.toString());
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.GetUserInfoSuccess);
                break;
            case ApproveSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.ApproveSuccess, data);
                break;
            case HomeInfoSuccess:
                Tools.error("HomeInfoSuccess_______", data.toString());
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.HomeInfoSuccess, dealWithHomeData(data));
                break;
            case InitializeSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.InitializeSuccess, dealWithStartData(data));
                break;
            case AddLocationSuccess:
                Tools.error(TAG, data.toString());
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.AddLocationSuccess, data);
                break;
            case GetUserLocationSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.GetUserLocationSuccess, dealWithMineLocationData(data));
                break;
            case WXAuthSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.WXAuthSuccess,data);
                break;
            case WXGetUserInfoSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.WXGetUserInfoSuccess,data);
                break;
            case ThirdLoginSuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.ThirdLoginSuccess,data);
                break;
            case WXPaySuccess:
                BaseApplication.getApplication().notifyDataChanged(BaseApplication.DataType.WXPaySuccess,data);
                break;
            case WXPayOrderInforSuccess:
                StringJsonOnResponse(BaseApplication.DataType.WXPayOrderInforFail, BaseApplication.DataType.WXPayOrderInforSuccess,
                        data.toString());
                break;
            case AliPayOrderInforSuccess:
                StringJsonOnResponse(BaseApplication.DataType.AliPayOrderInforFail, BaseApplication.DataType.AliPayOrderInforSuccess,
                        data.toString());
                break;
            case UpLoadImageSuccess:
                StringJsonOnResponse(BaseApplication.DataType.UpLoadImageFail, BaseApplication.DataType.UpLoadImageSuccess,
                        data.toString());
                break;
        }
    }

    /**解析Json {"Result":"Success","Message":""}*/
    private void StringJsonOnResponse(BaseApplication.DataType failType,BaseApplication.DataType successType,String response){
        try {
            if(new JSONObject(response).has("Result")){
                if(new JSONObject(response).getString("Result").equals("Success")){
                    if(successType == BaseApplication.DataType.WXPayOrderInforSuccess){
                        dealWithWXPayInfo(response,successType);
                    }else if(successType == BaseApplication.DataType.AliPayOrderInforSuccess){
                        dealWithAliPayInfo(response,successType);
                    }else if(successType == BaseApplication.DataType.UpLoadImageSuccess){
                        dealWithImageInfo(response,successType);
                    }
                }else{
                    BaseApplication.getApplication().notifyDataChanged(failType,"未知错误");
                }
            }else{
                BaseApplication.getApplication().notifyDataChanged(failType,"未知错误");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.debugger(TAG, "analysisStringJsonOnResponse e : " + e.getMessage().toString());
            BaseApplication.getApplication().notifyDataChanged(failType, "未知错误");
        }
    }


    //我的地址
    private MineLocationBean dealWithMineLocationData(Object data) {
        Gson gson = new Gson();
        MineLocationBean mineLocationBean = gson.fromJson(data.toString(), MineLocationBean.class);
        return mineLocationBean;
    }

    private ArrayList<StartPageBean.ImageList> dealWithStartData(Object data) {
        Gson gson = new Gson();
        StartPageBean startPageBean = gson.fromJson(data.toString(), StartPageBean.class);
        ArrayList<StartPageBean.ImageList> imageList = startPageBean.ImageList;
        return imageList;
    }

    private HomeBean dealWithHomeData(Object data) {
        Gson gson = new Gson();
        HomeBean homeBean = gson.fromJson(data.toString(), HomeBean.class);
        return homeBean;
    }

    public class LogicFileCallBack extends FileCallBack {
        private String destFileDir;
        private String destFileName;

        public LogicFileCallBack(String destFileDir, String destFileName) {
            super(destFileDir, destFileName);
            this.destFileDir = destFileDir;
            this.destFileName = destFileName;
        }

        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void inProgress(float progress, long total, int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(File response, int id) {
        }
    }

    public class LogicBitmapCallBack extends BitmapCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(Bitmap response, int id) {
        }
    }

    private List<Object> dealWithChannelRecommendData(Object data) {
        java.lang.reflect.Type typetest = new com.google.gson.reflect.TypeToken<List<Objects>>() {
        }.getType();
        List<Object> hotlList = new Gson().fromJson(data.toString(), typetest);
        return hotlList;
    }

    /**解析用户信息*/
    private void dealWithGetUserInfo(String jsonStr){
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(jsonStr, UserBean.class);
        String result = userBean.Result;
        if (result.equals("Fail")) {
            String message = userBean.Message;
        } else {
            UserBean userList = userBean.UserList;
            userList.setLoginType(Constants.UserLoginType_Normal);
            UserDaoHelper.getInstance().saveUserBean(userList);  //注册、第三方登录成功后 都调用这个 存储用户数据
        }
    }

    /**解析微信订单信息*/
    private void dealWithWXPayInfo(String data,BaseApplication.DataType successType){
        Tools.debugger("dealWithWXPayInfo","jsonStr : " + data.toString());
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<MallPayBean>() {}.getType();
        MallPayBean wxPayBean = null;
        try {
            wxPayBean = new Gson().fromJson(new JSONObject(data).getJSONObject("WeChatData").toString(), type);
            wxPayBean.setAppId(Constants.WX_APP_ID);
            wxPayBean.setPartnerId(Constants.WX_PartnerId);
            wxPayBean.setPackageValue(Constants.WX_PackageValue);
            Tools.debugger("dealWithWXPayInfo", "wxPayBean : " + wxPayBean.getPrepayId() + ",appId : " + wxPayBean.getAppId());
            BaseApplication.getApplication().notifyDataChanged(successType,wxPayBean);
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.debugger("dealWithWXPayInfo", "e : " + e.getMessage().toString());
        }
    }

    /**解析支付宝订单信息*/
    private void dealWithAliPayInfo(String data,BaseApplication.DataType successType){
        Tools.debugger("dealWithAliPayInfo","jsonStr : " + data.toString());
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<MallPayBean>() {}.getType();
        MallPayBean wxPayBean = null;
        try {
            wxPayBean = new Gson().fromJson(new JSONObject(data).getJSONObject("AliPayData").toString(), type);
            Tools.debugger("dealWithAliPayInfo","AliPayData : " + wxPayBean.getSignAliPay());
            BaseApplication.getApplication().notifyDataChanged(successType,wxPayBean.getSignAliPay());
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.debugger("dealWithAliPayInfo", "e : " + e.getMessage().toString());
        }
    }

    /**上传图片*/
    private void dealWithImageInfo(String data,BaseApplication.DataType successType){
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<String>() {}.getType();
        try {
            Tools.debugger("dealWithImageInfo","imageInfo : " + new JSONObject(data).getString("ImageInfo").toString());
            String imageInfo = new JSONObject(data).getString("ImageInfo").toString();
            BaseApplication.getApplication().notifyDataChanged(successType,imageInfo);
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.debugger("dealWithImageInfo", "e : " + e.getMessage().toString());
        }
    }

}