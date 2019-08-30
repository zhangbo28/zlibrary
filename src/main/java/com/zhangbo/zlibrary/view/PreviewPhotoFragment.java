package com.zhangbo.zlibrary.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.entity.SelectionSpec;
import com.zhihu.matisse.internal.ui.PreviewItemFragment;
import com.zhihu.matisse.internal.utils.PhotoMetadataUtils;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Author: zhangbo
 * Data：2019/8/27
 * TODO:
 */
public class PreviewPhotoFragment extends Fragment{

    private static final String ARGS_ITEM = "args_photo";

    public static PreviewPhotoFragment newInstance(String path) {
        PreviewPhotoFragment fragment = new PreviewPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_ITEM, path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.zhihu.matisse.R.layout.fragment_preview_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String path = getArguments().getString(ARGS_ITEM);
        if (TextUtils.isEmpty(path)) {
            return;
        }

        View videoPlayButton = view.findViewById(com.zhihu.matisse.R.id.video_play_button);
//        if (item.isVideo()) {
//            videoPlayButton.setVisibility(View.VISIBLE);
//            videoPlayButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(item.uri, "video/*");
//                    try {
//                        startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(getContext(), com.zhihu.matisse.R.string.error_no_video_activity, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        } else {
            videoPlayButton.setVisibility(View.GONE);
//        }
//        Point size = PhotoMetadataUtils.getBitmapSize(item.getContentUri(), getActivity());

        ImageViewTouch image = (ImageViewTouch) view.findViewById(com.zhihu.matisse.R.id.image_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        if (path.endsWith("gif")) {
            Glide.with(getContext())
                    .asGif()
                    .load(path)
                    .apply(new RequestOptions()
                            .priority(Priority.HIGH)
                            .fitCenter())
                    .into(image);
        } else {
            Glide.with(getContext())
                    .load(path)
                    .apply(new RequestOptions()
                            .priority(Priority.HIGH)
                            .fitCenter())
                    .into(image);
        }
    }

    public void resetView() {
        if (getView() != null) {
            ((ImageViewTouch) getView().findViewById(com.zhihu.matisse.R.id.image_view)).resetMatrix();
        }
    }
}
