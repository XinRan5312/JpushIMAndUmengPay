package com.engloryintertech.small.constant;

/**
 * 常量
 */
public class Constants {

    /** 请求头*/
    public static String Market_SMall = "SMall";
    public static String Product_Name = "SMall";
    public static String RANDOMUUID = "RANDOMUUID";

    /**第三方常量 微博*/
    public static final String WB_APP_KEY = "1626353465";
    public static final String REDIRECT_URL = "http://paycallback.lj361.com:7024/Oauth/WeiboLogonSuccess.ashx";//回调地址 与微博中的配置同步
    /**Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。*/
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    /**第三方常量 微信登录 微信支付*/
    public static final String WX_APP_ID = "wx6c62592c603d724a";
    public static final String WX_PartnerId = "1392315202";
    public static final String WX_PackageValue = "Sign=WXPay";
    public static final String WX_SECRET = "5aa5f317f6862a2cd7ae0cf20b82b363";
    public static final String WEIXIN_SCOPE = "snsapi_userinfo";// 必须  应用授权作用域，如获取用户个人信息则填写snsapi_userinfo
    public static final String WEIXIN_STATE = "login_state"; // 自定义 用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验

    /**QQ登录的Json解析*/
    public static final String QQ_Login_Json_Token = "access_token";
    public static final String QQ_Login_Json_OpenId = "openid";
    public static final String QQ_Login_Json_Expires = "expires_in";
    public static final String QQ_APP_ID = "1105598907";

    /** 网络状态*/
    public static final String NetworkState_Wifi = "Wifi";
    public static final String NetworkState_SecendG = "SecendG";
    public static final String NetworkState_ThirdG = "ThirdG";
    public static final String NetworkState_FouthG = "FouthG";

    /**Http请求的Tag*/
    public static final String Http_Request_Tag_Initialize = "Http_Request_Tag_Initialize";
    public static final String Http_Request_Tag_Verification = "Http_Request_Tag_Verification";
    public static final String Http_Request_Tag_Login = "Http_Request_Tag_Login";
    public static final String Http_Request_Tag_Reset = "Http_Request_Tag_Reset";
    public static final String Http_Request_Tag_SaveUser = "Http_Request_Tag_SaveUser";//保存
    public static final String Http_Request_Tag_ThirdLogin = "Http_Request_Tag_ThirdLogin";//第三方登录注册
    public static final String Http_Request_Tag_PersonalSetting = "Http_Request_Tag_PersonalSetting";//个人设置
    public static final String Http_Request_Tag_WXLogin = "Http_Request_Tag_WXLogin";//微信登录
    public static final String Http_Request_Tag_UpLoadImage = "Http_Request_Tag_UpLoadImage";//图片上传

    /**用户登录类型*/
    public static final int UserLoginType_Normal = 1;
    public static final int UserLoginType_wx = 2;
    public static final int UserLoginType_sina = 4;
    public static final int UserLoginType_qq =8;

    /**认证类型  通过 未通过*/
    public static final int ApproveType_Yes = 1;
    public static final int ApproveType_No = 0;
    /**认证类型*/
    public static final int ApproveType_Personal = 0;
    public static final int ApproveType_Authority = 1;
    public static final int ApproveType_Number = 2;

    /**详情请求通知*/
    public static byte mCurrent = 0;

    //支付类型
    public static int Pay_WX = 1;                //微信支付
    public static int Pay_AliPay = 2;            //支付宝支付
    public static int Pay_PaymentOfBalance = 4;  //余额支付

    public static final int SELECT_PIC_KITKAT = 1;
    public static final int SELECT_PIC = 2;

    /**相册 拍照*/
    public static final int Request_read_camera_permission = 229;
    public static final int Request_Camera = 100;
    public static final int Request_IMAGE = 1;

    /**上传图片*/
    public static final int upLoadImage_Type_Header = 1;  //头像
    public static final int upLoadImage_Type_Back = 2;  //背景
    public static final int upLoadImage_Type_Resource = 4;  //资源

    /**请求数据的条数*/
    public static final int loadListSize = 20;
    /**下拉刷新 获取数据列表*/
    public static final int Isupquery_Up = 1;       //1：向上查询
    public static final int Isupquery_Down  = 0;    //0：向下查询
}