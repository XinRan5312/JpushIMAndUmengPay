package com.engloryintertech.small.model.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/29.
 */
public class StartPageBean {
    public String Description;
    public ArrayList<ImageList> ImageList;
    public int LatestVersion;
    public String LatestVersionName;
    public int MinVersion;
    public String MinVersionName;
    public String Path;
    public long UserId;
    public String UserNickName;
    public String UserPic;

    public class ImageList {
        public String Description;
        public String ImageUrl;
    }

}