package com.zhangbo.zlibrary.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.zhangbo.zlibrary.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbo on 2017/7/17.
 *
 * @todo android6.0+(target sdk 23+)权限管理
 *  API是支持多项权限一起申请，但是对于用户是逐个授权或拒绝
 *  该类处理逻辑为对于一起提交的一组权限申请,全部授权才认定为成功,否则做授权失败处理,但不会重复申请用户已授权的权限
 */
public class PermissionHandler {

    String[] mPermissionList = new String[]{
            //Manifest.permission.RECORD_AUDIO,
            //Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    static PermissionHandler mInstance;
    private int PERMISSION_REQ_CODE = 1025;
    private CallBack mCallBack;

    private PermissionHandler() {
    }

    public static PermissionHandler getInstance(){
        if(mInstance==null){
            synchronized (PermissionHandler.class){
                if(mInstance==null){
                    mInstance = new PermissionHandler();
                }

            }
        }
        return mInstance;
    }

    /**
     * 请求权限
     *
     * @param permissions 请求的权限
     */
    public void requestPermission(final Activity activity,final String[] permissions, @NonNull CallBack cb) {
        this.mCallBack = cb;

        if (checkPermissions(activity,permissions)) {
            LogUtils.d("权限授权通过=="+permissions);
            mCallBack.onSuccess();
        }
        //当已经申请过该权限被拒绝的情况下此函数返回true，用于向用户给出必要的解释。(如果用户拒绝的同时勾选不再提示按钮的话，此函数返回false)
//        else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
//                new android.app.AlertDialog.Builder(activity)
//                        .setTitle("提示")
//                        .setMessage("授权啊,再不授权打屎你~~")
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQ_CODE);
//                            }
//                        }).show();
//            }
            else{

            List<String> needPermissions = getDeniedPermissions(activity,permissions);
            ActivityCompat.requestPermissions(activity, needPermissions.toArray(new String[needPermissions.size()]), PERMISSION_REQ_CODE);
        }
    }

    /**
     * 检测所有的权限是否都已授权
     * @param permissions
     * @return
     */
    private boolean checkPermissions(Context context,String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { //6.0以下系统不走该权限流程
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                LogUtils.d(permission+"未授权");
                return false;
            }
        }
        return true;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    public List<String> getDeniedPermissions(Activity activity,String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) !=PackageManager.PERMISSION_GRANTED
                    ||ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                needRequestPermissionList.add(permission);
            }
        }
        return needRequestPermissionList;
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        PermissionHandler.getInstance().onRequestPermissionResult(requestCode,permissions,grantResults);
//    }

    /**
     * 具体申请了动态权限的Activity中用此函数处理用户权限授予结果，如上所示
     */
    public void onRequestPermissionResult(int requestCode,@NonNull String[] permissions, @NonNull int[] grantResults){
        if (requestCode == PERMISSION_REQ_CODE) {
            if (verifyPermissions(grantResults)) {  //所申请的权限列表全部同意后才认定为授权成功
                LogUtils.d("权限授权成功=="+PERMISSION_REQ_CODE);
                mCallBack.onSuccess();
            } else {
                LogUtils.d("权限授权失败=="+PERMISSION_REQ_CODE);
                mCallBack.onFail();
            }
        }
    }


    /**
     * 确认所有的权限是否都已授权
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    public void showTipsDialog(final Context context) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }

    public interface CallBack{
        void onSuccess();
        void onFail();
    }

}
