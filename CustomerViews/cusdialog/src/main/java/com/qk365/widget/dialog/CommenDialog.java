package com.qk365.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class CommenDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private float scaleWidth;
    private float scaleHeight;

    private TextView tv_title;
    private TextView tv_tips;
    private Button btn_left;
    private Button btn_right;
    private  View layout;


    public CommenDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
        layout = getLayoutInflater().inflate(R.layout.commen_dialog, null);
        tv_tips = (TextView) layout.findViewById(R.id.tv_dialog_tips);
        tv_title = (TextView) layout.findViewById(R.id.tv_dialog_title);
        btn_left = (Button) layout.findViewById(R.id.btn_left);
        btn_right = (Button) layout.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        if(TextUtils.isEmpty(tv_title.getText().toString())){
            tv_title.setVisibility(View.GONE);
        }
    }

    public interface OnCommenDialogListener{
        void onClickLeft();

        void onClickRight();
    }

    private OnCommenDialogListener onCommenDialogListener;

    public void setOnCommenDialogListener(OnCommenDialogListener listener) {
        this.onCommenDialogListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindow();
        final int swidth = (int) (scaleWidth * 540);
        final int sheight = (int) (scaleHeight * 350);
        this.setContentView(layout, new LinearLayout.LayoutParams(swidth, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setCancelable(true);
    }


    private void setWindow() {
        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            scaleWidth = (float) dm.widthPixels / 720f;
            scaleHeight = (float) dm.heightPixels / 1280f;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

    public void setTips(String tips) {
        if (!TextUtils.isEmpty(tips)) {
            tv_tips.setText(tips);
        }
    }

    public void setButton(String leftText,String leftColor, String rightText,String rightColor) {
        if (!TextUtils.isEmpty(leftText)) {
            btn_left.setText(leftText);
        }
        if (!TextUtils.isEmpty(rightText)) {
            btn_right.setText(rightText);
        }
        try {
            if (!TextUtils.isEmpty(leftColor)) {
                btn_left.setTextColor(Color.parseColor(leftColor));
            }

            if (!TextUtils.isEmpty(rightColor)) {
                btn_right.setTextColor(Color.parseColor(rightColor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                if (onCommenDialogListener != null) {
                    onCommenDialogListener.onClickLeft();
                }else {
                    dismiss();
                }
                break;
            case R.id.btn_right:
                if (onCommenDialogListener != null) {
                    onCommenDialogListener.onClickRight();
                }else {
                    dismiss();
                }
                break;
        }
    }

}
