package com.engloryintertech.small.model.bean;

/**
 * Created by Administrator on 2016/9/6.
 */
public class MineLocationBean {

    public String Name;
    public String Address;
    public String TellPhone;
    public String Email;
    public int IsDefault;
    public long longitude;
    public long latitude;

    public MineLocationBean() {
    }

    public MineLocationBean(String name, String address, String tellPhone, String email, int isDefault, long longitude, long latitude) {
        Name = name;
        Address = address;
        TellPhone = tellPhone;
        Email = email;
        IsDefault = isDefault;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "MineLocationBean{" +
                "Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", TellPhone='" + TellPhone + '\'' +
                ", Email='" + Email + '\'' +
                ", IsDefault=" + IsDefault +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
