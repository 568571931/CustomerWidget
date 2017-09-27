package com.qk365.widget.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        btn1 = (Button) findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                showDialogBottom();
                break;
            case R.id.btn_2:
                showCommenDialog();
                break;
        }
    }

    private void showCommenDialog() {
        CommenDialog dialog = new CommenDialog(this);
        dialog.show();
    }

    private void showDialogBottom() {
        ActionSheet.createBuilder(this)
                .setCancelButtonTitle(
                        "取消")
                .setOtherButtonTitles(
                        "保密",
                        "男",
                        "女")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {

                    @Override
                    public void onOtherButtonClick(int index) {

                        switch (index) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }
}
