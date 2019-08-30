package com.zhangbo.zlibrary.widget;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhangbo.zlibrary.R;
import com.zhangbo.zlibrary.util.SizeUtils;
import com.zhangbo.zlibrary.view.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangbo
 * Data：2018/8/24
 * TODO:
 */
public class PictureHandleHelper {

    private RecyclerView mRecyclerView;
    private Activity mActivity;
    PhotoAdapter mAdapter;
    private int mMaxSize;
    private boolean previewEnable = true;
    private boolean delEnable = false;


    public PictureHandleHelper(RecyclerView mRecyclerView, Activity mActivity, int mMaxSize) {
        this.mRecyclerView = mRecyclerView;
        this.mActivity = mActivity;
        this.mMaxSize = mMaxSize;
        init();
    }

    private void init(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4);
        gridLayoutManager.offsetChildrenHorizontal(SizeUtils.dp2px(mActivity,8));
        gridLayoutManager.offsetChildrenVertical(SizeUtils.dp2px(mActivity,8));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.setAdapter(mAdapter = new PhotoAdapter(null,6));
    }


//    public void add(String path){
//        mImgList.add(path);
//        mAdapter.notifyDataSetChanged();
//    }
//    public void delete(String path){
//        mImgList.remove(path);
//        mAdapter.notifyDataSetChanged();
//    }
//    public void add(String[] paths){
//        for (String path:paths) {
//            mImgList.add(path);
//        }
//        mAdapter.notifyDataSetChanged();
//    }
//    public void setData(List<String> list){
//        mImgList.addAll(list);
//        mAdapter.notifyDataSetChanged();
//    }


//    public class CommonImgAdapter extends RecyclerView.Adapter {
//
//        public CommonImgAdapter() {
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_img,null));
//        }
//
//        @Override
//        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//            ViewHolder viewHolder = (ViewHolder) holder;
//            Glide.with(mActivity).load(mImgList.get(position)).into(viewHolder.img_content);
//
//            if(delEnable){
//                viewHolder.img_delete.setVisibility(View.VISIBLE);
//                viewHolder.img_delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mImgList.remove(position);
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//            if(previewEnable){
//                viewHolder.img_content.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        PhotoPickHelper.preview(mImgList,position,false,mActivity);
//                    }
//                });
//
//            }
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return mImgList==null?0:mImgList.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder{
//            ImageView img_content;
//            ImageView img_delete;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                img_content = (ImageView) itemView.findViewById(R.id.iv_content);
//                img_delete = (ImageView) itemView.findViewById(R.id.iv_del);
//            }
//        }
//    }

}
