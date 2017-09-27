package com.qk365.widget.circledial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qk365.widget.circledial.R;
import com.qk365.widget.circledial.views.ShowPercentView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class ShowPercentActivity extends Activity implements View.OnClickListener {

    private ShowPercentView myShowPercentView;
    private Button set_percent_40;
    private Button set_percent_80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_percent_layout);
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        myShowPercentView = (ShowPercentView) findViewById(R.id.myShowPercentView);
        set_percent_40 = (Button) findViewById(R.id.set_percent_40);
        set_percent_40.setOnClickListener(this);
        set_percent_80 = (Button) findViewById(R.id.set_percent_80);
        set_percent_80.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        if (view == set_percent_40) {
            myShowPercentView.setPercent(40);
        } else if (view == set_percent_80) {
            myShowPercentView.setPercent(80);
        }
    }


}
