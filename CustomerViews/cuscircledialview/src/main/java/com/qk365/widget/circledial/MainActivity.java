package com.qk365.widget.circledial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qk365.widget.circledial.activity.Activity1;
import com.qk365.widget.circledial.activity.DialCircleActivity;
import com.qk365.widget.circledial.activity.InstrumentActivity;
import com.qk365.widget.circledial.activity.QKDegreeActivity;
import com.qk365.widget.circledial.activity.QKStepActivity;
import com.qk365.widget.circledial.activity.ShowPercentActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_1:
                intent.setClass(MainActivity.this, InstrumentActivity.class);
                break;
            case R.id.btn_2:
                intent.setClass(MainActivity.this, ShowPercentActivity.class);
                break;
            case R.id.btn_3:
                intent.setClass(MainActivity.this, DialCircleActivity.class);
                break;
            case R.id.btn_4:
                intent.setClass(MainActivity.this, QKDegreeActivity.class);
                break;
            case R.id.btn_5:
                intent.setClass(MainActivity.this, QKStepActivity.class);
                break;
            case R.id.btn_6:
                intent.setClass(MainActivity.this, Activity1.class);
                break;
        }
        startActivity(intent);
    }
}
