package com.engloryintertech.small.model.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

import java.io.Serializable;

/**
 * Entity mapped to table USER_BEAN.
 */
public class UserBean implements Serializable{

    private Long id;
    private int UserId;
    /** Not-null value. */
    private String UserName;
    private String UserNickName;
    private Integer Sex;
    private Integer Age;
    private String Profession;
    private String Company;
    private String Email;
    private String Signature;
    private String Birthday;
    private String AvatarUrl;
    private String Address;
    private String TellPhone;
    private String UserToken;
    private String School;
    private Integer AuthenticationState;
    private Integer TellPhoneState;
    private Integer CertificationState;
    private String RecommendCode;
    private Float CreditValue;
    private Float MonetaryAssets;
    private Float RedAssets;
    private int LoginType;
    private String OpenId;//只用来作为参数传给服务器 本地不需要存储

    public String Result;
    public UserBean UserList;
    public String Message;

    public UserBean() {
    }

    public UserBean(Long id) {
        this.id = id;
    }

    public UserBean(String result, UserBean userList) {
        Result = result;
        UserList = userList;
    }

    public UserBean(Long id, int UserId, String UserName, String UserNickName, Integer Sex, Integer Age, String Profession, String Company, String Email, String Signature, String Birthday, String AvatarUrl, String Address, String TellPhone, String UserToken, String School, Integer AuthenticationState, Integer TellPhoneState, Integer CertificationState, String RecommendCode, Float CreditValue, Float MonetaryAssets, Float RedAssets, int LoginType) {
        this.id = id;
        this.UserId = UserId;
        this.UserName = UserName;
        this.UserNickName = UserNickName;
        this.Sex = Sex;
        this.Age = Age;
        this.Profession = Profession;
        this.Company = Company;
        this.Email = Email;
        this.Signature = Signature;
        this.Birthday = Birthday;
        this.AvatarUrl = AvatarUrl;
        this.Address = Address;
        this.TellPhone = TellPhone;
        this.UserToken = UserToken;
        this.School = School;
        this.AuthenticationState = AuthenticationState;
        this.TellPhoneState = TellPhoneState;
        this.CertificationState = CertificationState;
        this.RecommendCode = RecommendCode;
        this.CreditValue = CreditValue;
        this.MonetaryAssets = MonetaryAssets;
        this.RedAssets = RedAssets;
        this.LoginType = LoginType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    /** Not-null value. */
    public String getUserName() {
        return UserName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserNickName() {
        return UserNickName;
    }

    public void setUserNickName(String UserNickName) {
        this.UserNickName = UserNickName;
    }

    public Integer getSex() {
        return Sex;
    }

    public void setSex(Integer Sex) {
        this.Sex = Sex;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer Age) {
        this.Age = Age;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String Profession) {
        this.Profession = Profession;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String Birthday) {
        this.Birthday = Birthday;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String AvatarUrl) {
        this.AvatarUrl = AvatarUrl;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTellPhone() {
        return TellPhone;
    }

    public void setTellPhone(String TellPhone) {
        this.TellPhone = TellPhone;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String UserToken) {
        this.UserToken = UserToken;
    }

    public String getSchool() {
        return School;
    }

    public void setSchool(String School) {
        this.School = School;
    }

    public Integer getAuthenticationState() {
        return AuthenticationState;
    }

    public void setAuthenticationState(Integer AuthenticationState) {
        this.AuthenticationState = AuthenticationState;
    }

    public Integer getTellPhoneState() {
        return TellPhoneState;
    }

    public void setTellPhoneState(Integer TellPhoneState) {
        this.TellPhoneState = TellPhoneState;
    }

    public Integer getCertificationState() {
        return CertificationState;
    }

    public void setCertificationState(Integer CertificationState) {
        this.CertificationState = CertificationState;
    }

    public String getRecommendCode() {
        return RecommendCode;
    }

    public void setRecommendCode(String RecommendCode) {
        this.RecommendCode = RecommendCode;
    }

    public Float getCreditValue() {
        return CreditValue;
    }

    public void setCreditValue(Float CreditValue) {
        this.CreditValue = CreditValue;
    }

    public Float getMonetaryAssets() {
        return MonetaryAssets;
    }

    public void setMonetaryAssets(Float MonetaryAssets) {
        this.MonetaryAssets = MonetaryAssets;
    }

    public Float getRedAssets() {
        return RedAssets;
    }

    public void setRedAssets(Float RedAssets) {
        this.RedAssets = RedAssets;
    }

    public int getLoginType() {
        return LoginType;
    }

    public void setLoginType(int LoginType) {
        this.LoginType = LoginType;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getOpenId() {
        return OpenId;
    }

    /**第三方登录*/
    public UserBean getThirdPartyLoginUser(){
        return null;
    }
}
