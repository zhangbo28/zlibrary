package com.zhangbo.zlibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangbo.zlibrary.R;


/**
 * 正在加载的dialog
 * @author licai
 *
 */
public class LoadingDialog extends Dialog{

	String message="";
//
	Animation animation2;

	ImageView loadImageView;
	TextView contentText;


	public LoadingDialog(Context context) {
		
		// TODO Auto-generated constructor stub
		this(context, R.style.LoadingDialog);
	}

	public LoadingDialog(Context context, int theme){
	        super(context, theme);
	}

	protected LoadingDialog(Context context, boolean cancelable,
                            OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
			// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);
		loadImageView=(ImageView)findViewById(R.id.loadImage);
		contentText=(TextView)findViewById(R.id.content);

		animation2= AnimationUtils.loadAnimation(getContext(), R.anim.progress_bar_rotate);
		loadImageView.startAnimation(animation2);
//
		if(!TextUtils.isEmpty(message)) {
			contentText.setText(message);
		}

		setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				if(animation2!=null){
					animation2.cancel();
				}
			}
		});

		setOnShowListener(new OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				if(animation2!=null){
					loadImageView.startAnimation(animation2);
				}else{
					animation2= AnimationUtils.loadAnimation(getContext(), R.anim.progress_bar_rotate);
					loadImageView.startAnimation(animation2);
				}
			}
		});
	}


	//初始设置message内容
	public void setMessage(String message){
		this.message=message;
		if(!TextUtils.isEmpty(message)&&contentText!=null){
			contentText.setText(message);
		}
	}

	@Override
	public void dismiss() {
		if(isShowing())
		super.dismiss();
	}
}
