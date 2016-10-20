package com.engloryintertech.small.tools;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerificationUtil {

    /**
     * 验证手机格式  可以不输入0
     */
    public static boolean isMobileNO(String mobiles) {
        // 081 10--12位
        // 082--089 11--12位
//         Pattern p = Pattern.compile("[0][8][1]\\d{7,9}|[0][8][2-9]\\d{8,9}|[8][1]\\d{7,9}|[8][2-9]\\d{8,9}");
//         Matcher m = p.matcher(mobiles);
//         return m.matches();
        /*	手机正则
            移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
            联通：130、131、132、152、155、156、185、186
            电信：133、153、180、189、（1349卫通）
            总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        return mobiles.matches(telRegex);
    }

    /**
     * 验证密码
     */
    public static boolean isPassword(String password) {
        Pattern pawC = Pattern.compile("[^\u4e00-\u9fa5]*");
        Matcher mawC = pawC.matcher(password);
        if (mawC.matches()) {
            Pattern paw = Pattern.compile("^([A-Za-z0-9_~!@#$%^&*-=\\]\\[(_+/]|[\"';:.,><?])|.{6,12}+$");
            Matcher maw = paw.matcher(password);
            return maw.matches();
        } else {
            return mawC.matches();
        }
    }

    /**
     * 验证用户名
     */
    public static boolean isUserName(String userName) {
        Pattern p = Pattern.compile("^[a-zA-Z][0-9a-zA-Z]{1,12}$");
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    /**
     * 验证邮箱
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
