package com.qk365.widget.circledial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.qk365.widget.circledial.R;
import com.qk365.widget.circledial.views.DialCircleView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class DialCircleActivity extends Activity implements View.OnClickListener {
    private int percent = 100;
    private DialCircleView dialCircleView;
    private Button btnStart;
    private Button btnSetZero;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_circle);
        init();
    }

    private void init() {
        btnSetZero = findViewById(R.id.btn_set_zero);
        btnStart = findViewById(R.id.btn_start_anim);
        dialCircleView = findViewById(R.id.dial_circle_view);
        btnStart.setOnClickListener(this);
        btnSetZero.setOnClickListener(this);
//        dialCircleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_anim:
                dialCircleView.change(100);
                break;
            case R.id.btn_set_zero:
                dialCircleView.change(0);
                break;
        }
    }
}
