package com.engloryintertech.small.constant;

/**
 * 地址
 * 标注每个地址
 */
public class HttpConstants {

    /** API接口 线下*/
    public static final String BASE_API = "http://192.168.199.30:7021";
    /** API接口 线下 志富*/
//    public static final String BASE_API = "http://192.168.199.66:7021";
    /** 线下 朱长青*/
//    public static final String BASE_API = "http://192.168.199.143:7021";
    /** API接口 线上 */
//    public static final String BASE_API = "http://192.168.199.30:7021";

    /**js BASE*/
    public static final String BASE_JS_API = "http://192.168.199.138:8080" + "/small/web";

    /***********API 接口 start************************/
    /** 初始化接口*/
    public static final String Initialize_URL = BASE_API + "/start/ initialization";

    /*** 获取用户的信用值、资产*/
    public static final String Credit_URL = BASE_API + "/assets/getusercreditandassets";

    /** 用户资产及信用值变更明细*/
    public static final String CreditChange_URL = BASE_API + "/assets/getcreditandassetslog";

    /**用户资产变更*/
    public static final String AssetChange_URL = BASE_API + "/assets/userassetschange";

    /**获取用户信用构成*/
    public static final String UserCredit_URL = BASE_API + "/assets/getcreditconsist";

    /**好友添加*/
    public static final String AddFri_URL = BASE_API + "/friend/addfriends";

    /**同意/拒绝*/
    public static final String UpdateFri_URL = BASE_API + "/friend/updateFriendState";

    /**删除好友*/
    public static final String DeleteFri_URL = BASE_API + "/friend/delfriends";

    /**查找好友*/
    public static final String SelectFri_URL = BASE_API + "/friend/getfriends";

    /**用户注册接口*/
    public static final String Register_URL = BASE_API + "/user/userregister";

    /**用户登录接口*/
    public static final String Login_URL = BASE_API + "/user/userlogin";

    /**用户第三方注册/登陆接口*/
    public static final String Thirdlogin_URL = BASE_API + "/user/userthirdlogin";

    /**修改密码*/
    public static final String Updatepwd_URL = BASE_API + "/user/updatepwd";

    /**重置密码*/
    public static final String Resetpwd_URL = BASE_API + "/user/resetpwd";

    /**保存/更改用户信息*/
    public static final String Saveuser_URL = BASE_API + "/user/saveuser";

    /**获取短信验证码*/
    public static final String Verification_URL = BASE_API + "/messagecode/getverificationcode";

    /**校验短信验证码*/
    public static final String Checkverification_URL = BASE_API + "/messagecode/checkverificationcode";

    /**查询用户所有信息*/
    public static final String GetUserInfo_URL = BASE_API + "/user/getuserlist";

    /**查询用户信息的认证状态*/
    public static final String GetApprove_URL = BASE_API + "/user/getattestationstate";

    /**获取首页信息接口*/
    public static final String GetHoneInfo_URL = BASE_API + "/start/getfirstpage";

    /**添加收货地址*/
    public static final String AddLocation = BASE_API + "/address/addaddress";

    /**删除收货地址*/
    public static final String DeleteLocation = BASE_API + "/address/deleteaddress";

    /**获取用户所有地址*/
    public static final String GetUserAllLocation = BASE_API + "/address/getuseralladdress";

    /**更改收货地址*/
    public static final String UpdateLocation = BASE_API + "/address/updateaddress";

    /**图片长传*/
    public static final String upLoadImageUrl = BASE_API + "/upload/uploadimage";

    /**添加支付流水接口*/
    public static final String addPayDetail = BASE_API + "/assets/addpaydetail";

    /***********API 接口 start************************/


    /**通过code参数,AppID,AppSecret通过API换取access_token 和 openid*/
    public static String GetWeChatTokenPath(String code){
        return "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
                + Constants.WX_APP_ID + "&secret=" + Constants.WX_SECRET
                + "&code=" + code + "&grant_type=authorization_code";
    }
    /**通过access_token,openId进行接口调用，获取用户基本数据资源或帮助用户实现基本操作。*/
    public static String GetWeChatUserInfoPath(String openId, String accessToken){
        return "https://api.weixin.qq.com/sns/userinfo?access_token="
                + accessToken + "&openid=" + openId;
    }

    /***********JS 地址 start************************/

    /**获取我的订单列表*/
    public static final String orderListUrl = BASE_JS_API + "/order/orderList.html";

    /**获取我的服务列表*/
    public static final String myServiceUrl = BASE_JS_API + "/services/myService.html";

    /**获取我的账本*/
    public static final String myBookUrl = BASE_JS_API + "/user/myBook.html";

    /**服务详情*/
    public static final String serviceDetailUrl = BASE_JS_API + "/services/serviceDetail.html?servicelistid=";
    //http://192.168.199.135:8080/small/web/services/publishService.html?servicelistid=52&isWeb=false
    public static final String serviceDetailUrlParam = "&isWeb=false";

    /**服务类别二级菜单*/
    public static final String childTypeListUrl = BASE_JS_API + "/services/childTypeList.html?id=";
    ///services/childTypeList.html?id=8

    /**个人主页*/
    public static final String personalHomeUrl = BASE_JS_API + "/user/personalHome.html?userId=";

    /**支付*/
    public static final String payUrl = "http://192.168.199.119:8080/small/web/order/orderList.html";

    /**发布服务*/
    public static final String PublishServerUrl = "http://192.168.199.135:8080/small/web/services/publishService.html?servicelistid=52";

    /***********JS 地址 end************************/
}