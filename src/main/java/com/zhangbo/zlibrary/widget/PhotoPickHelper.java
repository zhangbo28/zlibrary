package com.zhangbo.zlibrary.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.zhangbo.zlibrary.R;
import com.zhangbo.zlibrary.engine.Glide4Engine;
import com.zhangbo.zlibrary.util.LogUtils;
import com.zhangbo.zlibrary.view.PhotoPreviewActivity;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.internal.ui.AlbumPreviewActivity;
import com.zhihu.matisse.internal.ui.BasePreviewActivity;
import com.zhihu.matisse.internal.ui.SelectedPreviewActivity;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;
//import com.zhihu.matisse.Matisse;
//import com.zhihu.matisse.MimeType;
//import com.zhihu.matisse.engine.impl.GlideEngine;
//import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import me.iwf.photopicker.PhotoPicker;
//import me.iwf.photopicker.PhotoPreview;

/**
 * Author: zhangbo
 * Data：2018/12/14
 * TODO: 图片选择/预览操作统一入口
 */
public class PhotoPickHelper {

    public static final int REQUEST_CODE = 1111;
    public static final String KEY_SELECTED_PHOTOS = "photopick_select";
//    public static final int REQUEST_CODE = PhotoPicker.REQUEST_CODE;
//    public static final String KEY_SELECTED_PHOTOS = PhotoPicker.KEY_SELECTED_PHOTOS;

    public static void preview(String linkedPhototUrl,Activity activity){
        preview(linkedPhototUrl,0,false,activity);
    }

    public static void preview(String linkedPhototUrl,int position,boolean showDelete,Activity activity){
        ArrayList<String> list = new ArrayList<>();
        if(!TextUtils.isEmpty(linkedPhototUrl)){
            list.addAll(Arrays.asList(linkedPhototUrl.split(",")));
        }
        preview(list,position,showDelete,activity);
    }

    public static void preview(ArrayList<String> photoList,Activity activity){
        preview(photoList,0,false,activity);
    }

    public static void preview(ArrayList<String> photoList, int position, boolean showDeleteBtn, Activity activity){
//        PhotoPreview.builder()
//                .setPhotos(photoList)
//                .setCurrentItem(position)
//                .setShowDeleteButton(showDeleteBtn)
//                .start(activity);

//        Intent intent = new Intent(activity, SelectedPreviewActivity.class);
//        intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE, mSelectedCollection.getDataWithBundle());
//        intent.putExtra(BasePreviewActivity.EXTRA_RESULT_ORIGINAL_ENABLE, mOriginalEnable);
//        activity.startActivity(intent);

//        Intent intent = new Intent(activity, AlbumPreviewActivity.class);
//        intent.putExtra(AlbumPreviewActivity.EXTRA_ALBUM, album);
//        intent.putExtra(AlbumPreviewActivity.EXTRA_ITEM, item);
//        intent.putExtra(BasePreviewActivity.EXTRA_DEFAULT_BUNDLE, mSelectedCollection.getDataWithBundle());
//        intent.putExtra(BasePreviewActivity.EXTRA_RESULT_ORIGINAL_ENABLE, mOriginalEnable);
//        activity.startActivity(intent);

        PhotoPreviewActivity.start(activity,photoList,position);


    }

    public static void pick(final int count,final boolean showCamera, final Activity activity){
//        PhotoPicker.builder()
//                .setPhotoCount(count)
//                .setShowCamera(showCamera)
//                .setShowGif(showGif)
//                .setPreviewEnabled(previewEnable)
//                .start(activity, PhotoPicker.REQUEST_CODE);

        String[] permissionList = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        PermissionHandler.getInstance().requestPermission(activity, permissionList, new PermissionHandler.CallBack() {
            @Override
            public void onSuccess() {
                Matisse.from(activity)
                        .choose(MimeType.ofImage())
                        .theme(R.style.Matisse_Dracula)
                        .countable(true)
                        .capture(showCamera)
                        .captureStrategy(new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                        .maxSelectable(count)
                        //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        //.gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(REQUEST_CODE);
            }

            @Override
            public void onFail() {
                PermissionHandler.getInstance().showTipsDialog(activity);
            }
        });





//        Matisse.from(activity)
//                .choose(MimeType.ofImage(), false)
//                .countable(true)
//                .capture(true)
//                .captureStrategy(
//                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
//                .maxSelectable(9)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(
//                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//                .thumbnailScale(0.85f)
////                                            .imageEngine(new GlideEngine())  // for glide-V3
//                .imageEngine(new Glide4Engine())    // for glide-V4
//                .setOnSelectedListener(new OnSelectedListener() {
//                    @Override
//                    public void onSelected(
//                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
//                        // DO SOMETHING IMMEDIATELY HERE
//                        LogUtils.d( "onSelected: pathList=" + pathList);
//
//                    }
//                })
//                .showSingleMediaType(true)
//                .originalEnable(true)
//                .maxOriginalSize(10)
//                .autoHideToolbarOnSingleTap(true)
//                .setOnCheckedListener(new OnCheckedListener() {
//                    @Override
//                    public void onCheck(boolean isChecked) {
//                        // DO SOMETHING IMMEDIATELY HERE
//                        LogUtils.d("onCheck: isChecked=" + isChecked);
//                    }
//                })
//                .forResult(REQUEST_CODE_CHOOSE);

    }

    public static List<String> obtainResult(Intent data){
        //return Matisse.obtainResult(data);
        return Matisse.obtainPathResult(data);
    }

}
