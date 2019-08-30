package com.zhangbo.zlibrary.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Author: zhangbo
 * Data：2018/8/14
 * TODO: 格式转换
 */
public class MoneyFormatUtil {

    private static double f1(double d) {
        BigDecimal bg = new BigDecimal(d);
        double result = bg.setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
        return result;
    }

    /**
     * DecimalFormat转换最简便
     */
    private static String f2(double d) {
        //DecimalFormat df = new DecimalFormat("#.00");      //"#.00"表示保留2位小数，不足2位则补0
        DecimalFormat df = new DecimalFormat("#.##");//"#.##"表示保留2位小数，不补0
        return df.format(d);
    }

    /**
     * DecimalFormat转换最简便
     */
    private static String f22(double d) {
        DecimalFormat df = new DecimalFormat("#.00");      //"#.00"表示保留2位小数，不足2位则补0
//        DecimalFormat df = new DecimalFormat("#.##");//"#.##"表示保留2位小数，不补0
        return df.format(d);
    }

    /**
     * String.format打印最简便
     */
//    private void m3() {
//        System.out.println(String.format("%.2f", f));
//    }

    private static String f3(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(d);
    }

    /**
     * 将给定金额字符保留2位有效数字，但不补0
     * @param target
     * @return
     */
    public static String format(String target) {
        if(TextUtils.isEmpty(target)){
            return "--";
        }

        try {
            double d = Double.parseDouble(target);
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(d);

        }catch (NumberFormatException e){
            LogUtils.e(e.getMessage());
        }

        return target;
    }

    public static String format(double target) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(target);
    }

    /**
     * 将给定金额字符保留2位有效数字，没有小数补0
     */
    private static String formatWith0(String target) {
        try {
            double d = Double.parseDouble(target);
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(d);

        }catch (NumberFormatException e){
        }

        return target;
    }

    /**
     * 格式化日期显示 去掉时分秒
     * @param date
     * @return
     */
    public static String formatDate(String date){
        if(TextUtils.isEmpty(date) || date.length()<10){
            return date;
        }

        return date.substring(0,10).replace('-','/');
    }
    public static String doubleToString(double num){
         String value = String.valueOf(num);
        int index = value.indexOf(".");
         if((value.length() -index) > 2){
             String sbStr = value.substring(0,index +3);
            return sbStr;
         }
        return value;
    }



//    public static String doubleToString(String num){
//        //使用0.00不足位补0，#.##仅保留有效位
//        if (TextUtils.isEmpty(num)){
//            return "0";
//        }
//        int index = num.indexOf(".");
//        if((num.length() -index) > 2){
//            String sbStr = num.substring(0,index +3);
//            return sbStr;
//        }
//        return num;
//    }

    public static double doubleToDouble(double num) {
        BigDecimal bg = new BigDecimal(num);
        double f1 = bg.setScale(2, BigDecimal.ROUND_FLOOR).doubleValue();
        return f1;
    }
    public static float floatTofloat(double num) {
        BigDecimal bg = new BigDecimal(num);
        float f1 = bg.setScale(2, BigDecimal.ROUND_FLOOR).floatValue();
        return f1;
    }

    public static String doubleToUnit(String num) {
        double result= 0;
        if(TextUtils.isEmpty(num)){
            return "0";
        }
        result = Double.parseDouble(num);
        if(result > 0 && result <= 1000){
            return format2Point(result);
        }else if(result >=10000 && result < 100000000){
            return getIngerValue(result/10000)+"万+";
        }else if(result >100000000){
            return getIngerValue(result/100000000)+"亿+";
        }
        return  getIngerValue(result)+"";
    }
    public static String doubleToUnit(double num) {
        String result  =  "0";
        if(num >=10000 && num < 100000000){
            return getIngerValue(num/10000) +"万+";
        }else if(num >100000000){
            return getIngerValue(num/100000000)+"亿+";
        }
        return  result ;
    }


    public static String format2Point(double number) {
        DecimalFormat formater = new DecimalFormat("#0.00");
        return formater.format(number);
    }

    public static String getTextThousands(String number) {
        if(TextUtils.isEmpty(number)){
            return "0.00";
        }
        DecimalFormat formater = new DecimalFormat("0.00");
        return formater.format(Double.parseDouble(number));
    }

//    public static void setContent(TextView txView,int txtSize,String unit, double value){
//        SpannableString s1 = new SpannableString(unit + doubleToUnit(value));
//        if(value >= 10000){
//            s1.setSpan(new AbsoluteSizeSpan(txtSize, true), s1.length()-2, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        }
//        txView.setText(s1);
//    }
    public static void setContent(TextView txView,int txtSize, String value){
        if(TextUtils.isEmpty(value))value=0+"";
        String format = formatWith0(value);

        int  index= format.indexOf(".");
        if(index > 0 && index<format.length()){
            SpannableString s1 = new SpannableString(format);
            s1.setSpan(new AbsoluteSizeSpan(txtSize, true), s1.length()-2, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            txView.setText(s1);
        }else{
            txView.setText(format);
        }
    }

    public static void setContent2(TextView txView,String target,int absoluteSize){
        SpannableString s1 = new SpannableString(target);
        int  index= target.indexOf(".");
        if(index > 0 && index<s1.length()){
            s1.setSpan(new AbsoluteSizeSpan(absoluteSize, true), index, s1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        txView.setText(s1);
    }

    public static String getIngerValue(double number) {
        DecimalFormat formater = new DecimalFormat("0");
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(number);
    }


    /**
     * 将每三个数字加上逗号处理,并且保留2位小数
     */
    public static String formatComma(double target) {
        if(target==0)return "0";

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(target);
    }

    public static String formatHalfUp(String target) {
        if(TextUtils.isEmpty(target)){
            return target;
        }
        BigDecimal bg3 = new BigDecimal(target);
        String result = bg3.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

        return result;
    }

}
