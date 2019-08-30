package com.zhangbo.zlibrary.widget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zhangbo.zlibrary.util.Toaster;

import java.util.Calendar;
import java.util.Date;

/**
 * Author: zhangbo
 * Data：2019/3/7
 * TODO: 基于系统组件封装的日期时间选择器
 * 可使用第三方框架PickView代替：https://github.com/Bigkoo/Android-PickerView
 */
public class DateTimePickerMgr {

    public static void showDateDialog(final Context context, final TextView tv, final Calendar calendar){

        new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
//                        calendar.set(year,monthOfYear,dayOfMonth);
//                        tv.setTag(calendar);
                        tv.setText(getDateFormat(year,monthOfYear+1,dayOfMonth));
                    }
        }
                    // 设置初始日期
                    , calendar.get(Calendar.YEAR)
                    ,calendar.get(Calendar.MONTH)
                    ,calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public static void showTimeDialog(final Context context, final TextView tv, final Calendar calendar) {

        new TimePickerDialog(context,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,int hourOfDay, int minute) {
//                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                        calendar.set(Calendar.MINUTE,minute);
//                        tv.setTag(calendar);
                        tv.setText(getTimeFormat(hourOfDay,minute));
                    }
                }
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                ,true).show();
    }

    public static void showTwice(final Context context,final TextView tv, final Calendar calendar,final boolean futureTime){


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                        calendar.set(year,monthOfYear,dayOfMonth);

                        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                                // 绑定监听器
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view,int hourOfDay, int minute) {
                                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                        calendar.set(Calendar.MINUTE,minute);
                                        if(futureTime && calendar.getTimeInMillis()<=new Date().getTime()){
                                            Toaster.show(context,"所选时间不得小于当前时间");
                                            return;
                                        }
                                        tv.setTag(calendar);
                                        tv.setText(getFormat(calendar));
                                    }
                                }
                                // 设置初始时间
                                , calendar.get(Calendar.HOUR_OF_DAY)
                                , calendar.get(Calendar.MINUTE)
                                // true表示采用24小时制
                                ,true);
                        timePickerDialog.show();
                    }
                }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH));

        if(futureTime)datePickerDialog.getDatePicker().setMinDate(new Date().getTime());

        datePickerDialog.show();
    }

    public static String getFormat(Calendar calendar){
        String date = getDateFormat(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
        String time = getTimeFormat(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));

        return date + " " +time;
    }

    private static String getDateFormat(int year, int month, int day){
        String monthStr;
        String dayStr;
        if(month<10){
            monthStr = "0" + month;
        }else{
            monthStr = "" + month;
        }
        if(day<10){
            dayStr = "0" + day;
        }else{
            dayStr = "" + day;
        }

        return year + "-"+ monthStr +"-"+dayStr;

    }

    private static String getTimeFormat(int hour, int minute){
        String hourStr;
        String minuteStr;
        if(hour<10){
            hourStr = "0" + hour;
        }else{
            hourStr = "" + hour;
        }
        if(minute<10){
            minuteStr = "0" + minute;
        }else{
            minuteStr = "" + minute;
        }

        return hourStr + ":"+ minuteStr +":00";

    }

}
