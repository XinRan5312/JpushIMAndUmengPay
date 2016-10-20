package com.engloryintertech.small.interfaces;

import com.tencent.mm.sdk.modelbase.BaseResp;

public class Interfaces {

    public static Interfaces instance;
    public static Interfaces sharedInstance(){
        if(null == instance)
            instance = new Interfaces();
        return instance;
    }

    public LoginNotify notify;
    public void setLoginNotify(LoginNotify notify) {
        this.notify = notify;
    }
    public void startLoginSuccessNotify(BaseResp resp){
        if(null != notify)
            notify.LoginSuccessNotify(resp);
    }
    public void startLoginFailNotify(String message){
        if(null != notify)
            notify.LoginFfailNotify(message);
    }
    public interface LoginNotify{
        public void LoginSuccessNotify(BaseResp resp);
        public void LoginFfailNotify(String message);
    }
    public void dissLoginFailNotify(){
        if(null != notify)
            notify = null;
    }

    public ShareIsStopNotify shareIsStopNotify;
    public void setShareIsStopNotify(ShareIsStopNotify shareIsStopNotify) {
        this.shareIsStopNotify = shareIsStopNotify;
    }
    public void startLoginSuccessNotify(){
        if(null != shareIsStopNotify)
            shareIsStopNotify.ShareIsStopNotifys();
    }
    public interface ShareIsStopNotify{
        public void ShareIsStopNotifys();
    }
}
