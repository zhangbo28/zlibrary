package com.zhangbo.zlibrary.util;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangbo on 2018-05-07.
 * TO DO: 输入框限制
 * 默认适用限制输入2位小数和最大值输入限制
 */
public class XInputFilter implements InputFilter {

    Pattern mPattern;

    //输入的最大金额
    private int MAX_VALUE = Integer.MAX_VALUE;
    //小数点后的位数
    private static final int POINTER_LENGTH = 2;

    private static final String POINTER = ".";

    private static final String ZERO = "0";

    public XInputFilter() {
        this(Integer.MAX_VALUE);
    }
    public XInputFilter(int maxValue) {
        MAX_VALUE = maxValue;
        mPattern = Pattern.compile("([0-9]|\\.)*");
    }

    /**
     * @param source    新输入的字符串
     * @param start     新输入的字符串起始下标，一般为0
     * @param end       新输入的字符串终点下标，一般为source长度-1
     * @param dest      输入之前文本框内容
     * @param dstart    原内容起始坐标，一般为0
     * @param dend      原内容终点坐标，一般为dest长度-1
     * @return          输入de内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }
        Matcher matcher = mPattern.matcher(source);
        //已经输入小数点的情况下，只能输入数字
        if(destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source)) {  //只能输入一个小数点
                    return "";
                }
            }
            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = destText.length() - index;
            //如果长度大于2，并且新插入字符在小数点之后
            if (length > POINTER_LENGTH && index<dstart) {
                //超出2位返回null
                return "";
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点
            if (!matcher.matches()) {
                return "";
            } else {
                if ((POINTER.equals(source)) && TextUtils.isEmpty(destText)) {
                    return "";
                }
            }
        }
        //验证输入金额的大小
        double sumText = Double.parseDouble(destText + sourceText);
        if (sumText > MAX_VALUE) {
            return "";
        }
        return dest.subSequence(dstart, dend) + sourceText;
    }

    /**
     * 标准限制EditText只能输入2位小数
     * @param editText
     */
    public static void limit(EditText editText){
        editText.setFilters(new InputFilter[]{new XInputFilter()});
    }

    public static void limit(EditText editText,int maxValue){
        editText.setFilters(new InputFilter[]{new XInputFilter(maxValue)});
    }
    public static void limitLenth(EditText editText,int len){
        editText.setFilters(new InputFilter[] {/*new XInputFilter(), */new InputFilter.LengthFilter(len)});
    }
}