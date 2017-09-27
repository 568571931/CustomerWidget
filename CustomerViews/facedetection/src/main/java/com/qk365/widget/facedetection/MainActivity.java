package com.qk365.widget.facedetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qk365.widget.facedetection.activity.TutorialOnFaceDetect;
import com.qk365.widget.facedetection.activity.TutorialOnFaceDetect1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(MainActivity.this, TutorialOnFaceDetect.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(MainActivity.this, TutorialOnFaceDetect1.class));
                break;
        }
    }
}
