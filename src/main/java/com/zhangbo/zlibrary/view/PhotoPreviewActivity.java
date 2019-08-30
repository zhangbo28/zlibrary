package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhangbo.zlibrary.R;
import com.zhangbo.zlibrary.util.ListUtils;
import com.zhangbo.zlibrary.widget.FragPagerAdaper;
import com.zhihu.matisse.internal.utils.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangbo
 * Data：2019/8/27
 * TODO: 纯图片预览
 */
public class PhotoPreviewActivity extends AppCompatActivity{

    protected ViewPager mPager;

    protected FragPagerAdaper mAdapter;

    protected TextView mButtonBack;

    public static final String PHOTO_ARGS = "photo_paths";


    public static void start(Context context,ArrayList<String> paths){
        Intent intent = new Intent(context,PhotoPreviewActivity.class);
        intent.putStringArrayListExtra(PHOTO_ARGS,paths);
        context.startActivity(intent);
    }
    public static void start(Context context,ArrayList<String> paths,int pos){
        Intent intent = new Intent(context,PhotoPreviewActivity.class);
        intent.putStringArrayListExtra(PHOTO_ARGS,paths);
        intent.putExtra("index",pos);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        if (Platform.hasKitKat()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        initViews();
    }

    protected void initViews(){
        List<String> mPhotoList = getIntent().getStringArrayListExtra(PHOTO_ARGS);
        if(ListUtils.isEmpty(mPhotoList))return;

        int index = getIntent().getIntExtra("index",0);
        if(index>=mPhotoList.size())index=0;

        mButtonBack = (TextView) findViewById(R.id.button_back);
        mPager = (ViewPager) findViewById(com.zhihu.matisse.R.id.pager);

        List<Fragment> fragmentList = new ArrayList<>();
        for (String path:mPhotoList) {
            fragmentList.add(PreviewPhotoFragment.newInstance(path));
        }
        mAdapter = new FragPagerAdaper(getSupportFragmentManager(),fragmentList);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(index);

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
