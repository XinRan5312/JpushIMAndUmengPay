package com.engloryintertech.small.model.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/29.
 */
public class HomeBean {

    public ArrayList<ImageList> ImageList;
    public ArrayList<ServiceList> ServiceList;
    public ArrayList<SortList> SortList;
    public ArrayList<BroadCast> BroadCast;

    public class BroadCast {
        public long id;
        public String Contents;
        public String Links;
        public String CreateTime;
    }

    public class ImageList {
        public String Description;
        public String ImageUrl;
    }

    public class ServiceList {
        public long ServiceListId;
        public String SortListTitle;
        public String SortListImage;
        public long UserId;
        public String UserName;
        public String UserNickName;
        public int UserStar;
        public String Company;
        public String CreditValue;
        public String Price;
        public long AddressId;
        public String Mark;
        public String AuditingStatus;
        public int AuthenticationState;
        public int CertificationStatus;
        public int TellPhoneState;
        public int OrderCount;
        public String CreateTime;
        public ArrayList<String> ImageUrlLst;
    }

    public class SortList {
        public String Image;
        public String KeyWords;
        public int Sort;
        public long SortListId;
        public String Title;
    }
}