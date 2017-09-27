package com.qk365.widget.circledial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qk365.widget.circledial.R;
import com.qk365.widget.circledial.views.QKDegreeView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class QKDegreeActivity extends Activity implements View.OnClickListener {

    private Button btnChange;
    private QKDegreeView qkDegreeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qk_degree);
        btnChange = findViewById(R.id.btn_set_progress);
        qkDegreeView = findViewById(R.id.qk_degree_view);
        btnChange.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == btnChange) {
            qkDegreeView.change((float) (Math.random()*100));
        }
    }
}
