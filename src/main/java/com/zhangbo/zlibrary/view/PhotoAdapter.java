package com.zhangbo.zlibrary.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhangbo.zlibrary.R;
import com.zhangbo.zlibrary.util.ListUtils;
import com.zhangbo.zlibrary.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangbo
 * Data：2019/3/11
 * TODO:
 */
public class PhotoAdapter extends RecyclerView.Adapter {

    int maxSize;  //<=0的情况下只作显示

    ArrayList<String> mPhotoList;
    OnItemClick mItemClickListener;

    public PhotoAdapter(ArrayList<String> photoList) {
        this.mPhotoList = photoList;
        if(mPhotoList==null)
            mPhotoList = new ArrayList<>();
    }

    public PhotoAdapter(ArrayList<String> photoList,int maxSize) {
        this.mPhotoList = photoList;
        this.maxSize = maxSize;
        if(mPhotoList==null)
            mPhotoList = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
//        SquaredImageView imageView = new SquaredImageView(context);
//        imageView.setSize(SizeUtils.dp2px(context,50));
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_common_img,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ImageView imageView = viewHolder.targetImage;
        ImageView imgDelete = viewHolder.imgDelete;
        //final boolean isAdd = mPhotoList!=null && mPhotoList.size()<maxSize && position==maxSize-1;
        final boolean isAdd = position>=getCurrentSize();
        Glide.with(imageView.getContext()).load(isAdd?R.drawable.photo_add:mPhotoList.get(position)).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener!=null)
                    mItemClickListener.onClick(position);
            }
        });

        onBindViewHolderWrap(viewHolder,position);
    }

    /**
     * 需要额外的操作时覆写此函数
     * @param holder
     * @param position
     */
    public void onBindViewHolderWrap(@NonNull ViewHolder holder, final int position){

    }

    @Override
    public int getItemCount() {
        return maxSize>0?getAdapterSize():mPhotoList.size();
    }

    private int getAdapterSize(){
        if(ListUtils.isEmpty(mPhotoList))return 1;

        if(mPhotoList.size()>=maxSize){
            return maxSize;
        }

        return mPhotoList.size()+1;

    }

    public int getCurrentSize(){
        if(mPhotoList==null)return 0;
        return mPhotoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView targetImage;
        public ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            targetImage = (ImageView) itemView.findViewById(R.id.iv_content);
            imgDelete = (ImageView) itemView.findViewById(R.id.iv_del);
        }
    }


    public interface OnItemClick{
        void onClick(int position);
    }


    public ArrayList<String> getPhotoList() {
        return mPhotoList;
    }

    public int getMaxSize() {
        return maxSize;
    }
    public int getEnableSize() {
        return maxSize-mPhotoList.size();
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void addAll(ArrayList<String> photos){
        if(ListUtils.isEmpty(photos))return;
        mPhotoList.addAll(photos);
        notifyDataSetChanged();
    }


    public void add(String photo){
        if(TextUtils.isEmpty(photo))return;
        mPhotoList.add(photo);
        notifyDataSetChanged();
    }

    public void remove(int index){
        if(mPhotoList.size()>index){
            mPhotoList.remove(index);
            notifyDataSetChanged();
        }

    }

    public PhotoAdapter setItemClickListener(OnItemClick mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        return this;
    }
}
