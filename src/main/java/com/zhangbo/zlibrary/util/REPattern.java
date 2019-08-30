package com.zhangbo.zlibrary.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @ClassName: REPattern
 * @Description: TODO(正则表达式)
 */
public class REPattern {

    // 判断手机格式是否正确
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1\\d{10}$");  //改为以1开头11位即可
        Matcher m = p.matcher(mobiles);
        return m.matches();

//        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
    }

    //判断是否为数字或字母
    public static boolean isCharOrNum(String s){
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 判断身份证号是否符合规则
     *
     * @param code
     * @return
     */
    public static boolean isConfirmCode(String code) {

        Pattern p = Pattern.compile("^\\d{18}$|^\\d{17}(\\d|X|x)$");
        Matcher m = p.matcher(code);
        return m.matches();
    }

    // 验证电子邮件是否正确
    public static boolean isEmail(String mobiles) {

        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证价格
     *
     * @param price
     * @return boolean
     */
    public static boolean isPrice(String price) {

        // Pattern p = Pattern.compile("^(0|[1-9][0-9]{0,9})(+\\.[0-9]{1,2})");
        Pattern p = Pattern.compile("(\\d)+(.\\d{1,2})?");
        Matcher m = p.matcher(price);
        return m.matches();
    }

    /**
     * 新增2位小数内匹配规则   允许小数以"."结束
     */
    public static boolean is2Decimal(String text) {

        // Pattern p = Pattern.compile("^(0|[1-9][0-9]{0,9})(+\\.[0-9]{1,2})");
        Pattern p = Pattern.compile("(\\d)+(.\\d{1,2})?|(\\d)+(\\.)$");
        Matcher m = p.matcher(text);
        return m.matches();
    }

    public static boolean isOnePointDecimal(String price) {

        // Pattern p = Pattern.compile("^(0|[1-9][0-9]{0,9})(+\\.[0-9]{1,2})");
        Pattern p = Pattern.compile("^[0-9]+(.[0-9])?");
        Matcher m = p.matcher(price);
        return m.matches();
    }

    // 只能判断部分CJK字符（CJK统一汉字）
    public static boolean isChineseByREG(String str) {

        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str.trim()).find();
    }

    // 只能判断汉字和数字
    public static boolean isGBKOrNum(String str) {

        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+|\\d");
        return pattern.matcher(str.trim()).find();
    }


    // 只能判断汉字和英文
    public static boolean isGBKOrChar(String str) {

        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+|\\w");
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 限制用户昵称只能输入中文、英文和数字和下划线
     */
    public static boolean isNickName(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBFa-zA-Z0-9_]+");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证价格2
     *
     * @param price
     * @return boolean
     */
    public static boolean isPrices(String price) {

        Pattern p = Pattern.compile("(^[1-9]\\d*(\\.\\d{1,2})?$)|(^[0]{1}(\\.\\d{1,2})?$)");
        // Pattern p = Pattern.compile("(\\d)+(.\\d{1,2})?");
        Matcher m = p.matcher(price);
        return m.matches();
    }

    /**
     * @param mobiles
     * @return
     * @Description: TODO(验证密码)
     */
    public static boolean isPwd(String mobiles) {

        Pattern p = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$).{6,18}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    // 判断是否全是数字
    public static boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * @param mobiles
     * @return
     * @Description: TODO(省级)
     */
    public static boolean getProvince(String mobiles) {

        Pattern p = Pattern.compile("^\\d{2}0000$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * @param mobiles
     * @param pro
     * @return
     * @Description: TODO(市级)
     */
    public static boolean getCity(String mobiles, String pro) {

        Pattern p = Pattern.compile("^" + pro + "\\d{1}[1-9]00$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * @param mobiles
     * @param pro
     * @return
     * @Description: TODO(区县)
     */
    public static boolean getDistrict(String mobiles, String pro) {

        Pattern p = Pattern.compile("^" + pro + "[0][1-9]{1}|" + pro + "[1-9]\\d$");
        Matcher m = p.matcher(mobiles);
        return m.matches();

    }

}
