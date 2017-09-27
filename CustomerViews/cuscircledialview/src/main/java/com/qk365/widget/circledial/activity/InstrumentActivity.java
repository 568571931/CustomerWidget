package com.qk365.widget.circledial.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

import com.qk365.widget.circledial.R;
import com.qk365.widget.circledial.views.InstrumentView;

/**
 * Created by Administrator on 2017/9/4.
 */

public class InstrumentActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);

        final InstrumentView viewById = (InstrumentView) findViewById(R.id.instrumentView);
        SeekBar viewById1 = (SeekBar) findViewById(R.id.sb);

        viewById1.setMax(100);
        viewById1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewById.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
