package com.qk365.widget.circledial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.qk365.widget.circledial.R;
import com.qk365.widget.circledial.views.QKStepView;

/**
 * Created by Administrator on 2017/9/12.
 */

public class QKStepActivity extends Activity {

    private Button btnProgress;
    private Button btnReset;
    private QKStepView stepView;
    private String[] strs = new String[]{
            "2015年05月01日 22:10;联系人:张思型，电话:13671789088",
            "2015年05月01日 22:10;维修人:王师傅，电话:13671789088",
            "2015年05月01日 22:10;维修人:王师傅，电话:13671789088",
            "2015年05月01日 22:10",
    };
    private int step = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qk_step);
        btnProgress = findViewById(R.id.btn_add_progress);
        btnReset = findViewById(R.id.btn_add_reset);
        stepView = findViewById(R.id.qk_degree_view);
        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int step = (int) (Math.random() * 4);
                if (step == 0) {
                    stepView.setStep(step, strs[0]);
                }
                if (step == 1) {
                    stepView.setStep(step, strs[0], strs[1]);
                }
                if (step == 2) {
                    stepView.setStep(step, strs[0], strs[1], strs[2]);
                }
                if (step == 3) {
                    stepView.setStep(step, strs[0], strs[1], strs[2], strs[3]);
                }
                step++;
                if (step > 3) {
                    step = 0;
                }

            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
