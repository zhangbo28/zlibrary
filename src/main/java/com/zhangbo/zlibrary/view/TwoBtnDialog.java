package com.zhangbo.zlibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhangbo.zlibrary.R;
import com.zhangbo.zlibrary.util.SizeUtils;


/**
 * 含有两个按钮的dialog
 *
 * @author licai
 */
public class TwoBtnDialog extends Dialog {

    private String title = "";
    private CharSequence content = "";
    //
    TextView titleView;
    TextView contentView;
    TextView positiveText;
    TextView negativeText;

    OnBtnClickLisenner listener1;
    OnBtnClickLisenner listener2;
    private String positiveContent = "确定";
    private String negativeContent = "取消";
    private boolean isCancleable = true;
    private boolean isCancleOutside = true;

    SpannableString string;

    private boolean isContentTxtCenter;

    public void setContentTxtCenter(boolean contentTxtCenter) {
        isContentTxtCenter = contentTxtCenter;
    }

    int contentColor;

    int titleColor;

    int titleDrawable;

    public TwoBtnDialog(Context context, boolean isCancleable, boolean isCancleOutside) {

        // TODO Auto-generated constructor stub
        this(context, R.style.TwoBtnDialog);
        this.isCancleable = isCancleable;
        this.isCancleOutside = isCancleOutside;
    }

    public TwoBtnDialog(Context context) {
        this(context, R.style.TwoBtnDialog);
    }

    public TwoBtnDialog(Context context, boolean isContentTxtCenter) {
        this(context, R.style.TwoBtnDialog);
        this.isContentTxtCenter = isContentTxtCenter;
    }

    @Deprecated
    public TwoBtnDialog(Context context, int theme) {
        super(context, theme);
    }

    protected TwoBtnDialog(Context context, boolean cancelable,
                           OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_two_btn);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = SizeUtils.getWidthPx(getContext()) * 3 / 4;
        window.setAttributes(params);

        setCancelable(isCancleable);
        setCanceledOnTouchOutside(isCancleOutside);

        titleView = (TextView) findViewById(R.id.title);
        contentView = (TextView) findViewById(R.id.content);
        positiveText = (TextView) findViewById(R.id.sureBtn);
        negativeText = (TextView) findViewById(R.id.cancelBtn);

        if (!TextUtils.isEmpty(title)) {
            titleView.setText(title);
        } else {
            titleView.setVisibility(View.GONE);
            contentView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        }

        if (titleColor != 0) {
            titleView.setTextColor(titleColor);
        }

        if (titleDrawable != 0) {
            Drawable drawable = getContext().getResources().getDrawable(titleDrawable);

            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());

            titleView.setCompoundDrawables(drawable, null, null, null);
        }
        if (contentColor != 0) {
            contentView.setTextColor(contentColor);
        }

        if (string != null && !string.toString().equals("")) {
            contentView.setText("");
            contentView.append(string);
        } else {
            contentView.setText(content);
        }

        if (isContentTxtCenter) {
            contentView.setGravity(Gravity.CENTER);
        }

        positiveText.setText(positiveContent);
        positiveText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (listener1 != null) {
                    listener1.OnBtnClick(TwoBtnDialog.this);
                }
                dismiss();
            }
        });

        negativeText.setText(negativeContent);
        negativeText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (listener2 != null) {
                    listener2.OnBtnClick(TwoBtnDialog.this);
                }
                dismiss();
            }
        });
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;

    }

    public void setTitle(String title, int titleColor) {
        this.title = title;
        this.titleColor = titleColor;
    }

    public void setTitle(String title, int titleColor, int titleDrawable) {
        this.title = title;
        this.titleColor = titleColor;
        this.titleDrawable = titleDrawable;
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setMessage(CharSequence content) {
        this.content = content;

    }

    public void setMessage(CharSequence content, int contentColor) {
        this.content = content;
        this.contentColor = contentColor;
    }


    public void setSpannableMessage(SpannableString string) {
        if (contentView != null) {
            contentView.setText("");
            contentView.append(string);
        } else {
            this.string = string;
        }
    }

    /**
     * 设置确定按钮
     *
     * @param positive
     * @param listener
     */
    public void setPositiveBtn(String positive, final OnBtnClickLisenner listener) {
        this.positiveContent = positive;
        listener1 = listener;
    }

    /**
     * 设置取消按钮
     *
     * @param negative
     * @param listener
     */
    public void setNegativeBtn(String negative, final OnBtnClickLisenner listener) {
        this.negativeContent = negative;
        listener2 = listener;
    }

    public static interface OnBtnClickLisenner {
        public void OnBtnClick(DialogInterface dialog);
    }

}
