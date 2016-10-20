package com.engloryintertech.small.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.engloryintertech.small.constant.Constants;
import com.engloryintertech.small.constant.HttpConstants;
import com.engloryintertech.small.model.bean.ResourcesBean;
import com.engloryintertech.small.model.bean.UserBean;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppLoginAndShareManage {

    private final String TAG = "AppLoginAndShareManage";
    public static AppLoginAndShareManage instance;
    public static AppLoginAndShareManage sharedInstance(){
        if(null == instance)
            instance = new AppLoginAndShareManage();
        return instance;
    }

    /**处理获取token、openId*/
    public String conductTokenResult(String resultStr){
        String requestUserUrl = null;
        try {
            JSONObject jSONObject = new JSONObject(resultStr);
            Tools.debugger(TAG, "conductTokenResult : " + jSONObject.toString());
            String openid = jSONObject.getString("openid").toString().trim();
            String access_token = jSONObject.getString("access_token").toString().trim();
            Tools.debugger(TAG, "conductTokenResult openid = " + openid);
            Tools.debugger(TAG, "conductTokenResult access_token = " + access_token);
            requestUserUrl = HttpConstants.GetWeChatUserInfoPath(openid, access_token);
            Tools.debugger(TAG,"requestUserUrl : " + requestUserUrl);
        } catch (JSONException e) {
            e.printStackTrace();
            Tools.debugger(TAG,"conductTokenResult : " + e.getMessage().toString());
        }
        return requestUserUrl;
    }

    /**处理获取获取用户信息后的json*/
    public JSONObject conductUserInfoResult(String resultSystemStr,String resultUserStr){
        try {
            final JSONObject systemResultJson = new JSONObject(resultSystemStr);
            JSONObject userResultJson = new JSONObject(resultUserStr);
            String openid = systemResultJson.getString("openid").toString();
            String image = userResultJson.getString("headimgurl").toString();
            String nickname = userResultJson.getString("nickname").toString();

            JSONObject newJsonSystem = new JSONObject();
            JSONObject newJsonUser = new JSONObject();
            newJsonSystem.put("access_token", systemResultJson.getString("access_token").toString());
            newJsonSystem.put("expires_in", systemResultJson.getInt("expires_in") + "");
            newJsonSystem.put("refresh_token", systemResultJson.getString("refresh_token").toString());
            newJsonSystem.put("openid", systemResultJson.getString("openid").toString());
            newJsonSystem.put("scope", systemResultJson.getString("scope").toString());
            newJsonUser.put("nickname", userResultJson.getString("nickname").toString());
            Tools.debugger(TAG, "sex : " + userResultJson.getInt("sex"));
            if(userResultJson.getInt("sex") == 1){ //Male
                newJsonUser.put("sex", "Male");
            }else{//Female
                newJsonUser.put("sex", "Female");
            }
            newJsonUser.put("headimgurl", userResultJson.getString("headimgurl").toString());
            newJsonUser.put("province", userResultJson.getString("province").toString());
            newJsonUser.put("city", userResultJson.getString("city").toString());
            newJsonUser.put("country", userResultJson.getString("country").toString());
            newJsonUser.put("unionid", userResultJson.getString("unionid").toString());
            newJsonSystem.put("profile", newJsonUser);
            Tools.debugger(TAG, "jsonNews : " + newJsonSystem.toString());
            return newJsonSystem;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserBean setUserInfo(String resultSystemStr,String resultUserStr){
        UserBean userBean = null;
        try {
            final JSONObject systemResultJson = new JSONObject(resultSystemStr);
            JSONObject userResultJson = new JSONObject(resultUserStr);
            String token = systemResultJson.getString("access_token").toString();
            String openid = systemResultJson.getString("openid").toString();
            String image = userResultJson.getString("headimgurl").toString();
            String nickname = userResultJson.getString("nickname").toString();
            userBean = new UserBean();
            userBean.setLoginType(Constants.UserLoginType_wx);
            userBean.setUserToken(token);
            userBean.setOpenId(openid);//取值赋值 只是作为参数传给服务器
            userBean.setAvatarUrl(image);
            userBean.setUserName(nickname);
            return userBean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**生成分享时用的bitmap*/
    public Bitmap sharenNeedBitmap(ResourcesBean messaegBean) throws IOException {
        Bitmap bitmap;
        URL url = new URL(messaegBean.getThumbnails());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }

    /**微信分享时需要的数据*/
    public com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req getWXMediaMessage(ResourcesBean messaegBean,Bitmap bitmap){
        WXMediaMessage wXMediaMessage = new WXMediaMessage();
        wXMediaMessage.description = messaegBean.getShort_description();
        wXMediaMessage.title = messaegBean.getName();

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = messaegBean.getLink();

        if(bitmap != null){
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap,150,150, true);
            bitmap.recycle();
            wXMediaMessage.thumbData = bmpToByteArray(thumbBmp, true);  //设置缩略图
        }
        wXMediaMessage.mediaObject = webpage;
        com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req req = new com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req();
        req.transaction = "videoname";//唯一区分到底是哪一个请求
        req.message = wXMediaMessage;
        req.scene = com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
        return req;
    }

    private byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
