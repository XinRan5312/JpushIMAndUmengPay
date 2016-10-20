package com.engloryintertech.small.model.bean;

/**
 * Created by Administrator on 2016/8/24.
 */
public class UsersBean {
    public String Result;
    public Users UserList;
    public String Message;

    public UsersBean() {
    }

    public UsersBean(String result, Users userList) {
        Result = result;
        UserList = userList;
    }

    public class Users {
        public String Address;
        public String Age;
        public String AvatarUrl;
        public String Birthday;
        public String Company;
        public String Email;
        public String Profession;
        public String Sex;
        public String Signature;
        public String TellPhone;
        public String UserId;
        public String UserName;
        public String UserNickName;

        public Users() {
        }

        public Users(String address, String age, String avatarUrl, String birthday, String company, String email, String profession, String sex, String signature, String tellPhone, String userId, String userName, String userNickName) {
            Address = address;
            Age = age;
            AvatarUrl = avatarUrl;
            Birthday = birthday;
            Company = company;
            Email = email;
            Profession = profession;
            Sex = sex;
            Signature = signature;
            TellPhone = tellPhone;
            UserId = userId;
            UserName = userName;
            UserNickName = userNickName;
        }

        @Override
        public String toString() {
            return "Users{" +
                    "Address='" + Address + '\'' +
                    ", Age='" + Age + '\'' +
                    ", AvatarUrl='" + AvatarUrl + '\'' +
                    ", Birthday='" + Birthday + '\'' +
                    ", Company='" + Company + '\'' +
                    ", Email='" + Email + '\'' +
                    ", Profession='" + Profession + '\'' +
                    ", Sex='" + Sex + '\'' +
                    ", Signature='" + Signature + '\'' +
                    ", TellPhone='" + TellPhone + '\'' +
                    ", UserId='" + UserId + '\'' +
                    ", UserName='" + UserName + '\'' +
                    ", UserNickName='" + UserNickName + '\'' +
                    '}';
        }
    }
}