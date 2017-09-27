package com.qk365.widget.circledial.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.qk365.widget.circledial.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class Activity1 extends Activity implements View.OnClickListener {

    private Button btnStart;
    private Button btnReset;
    private GridView gridView;
    private List<String> stringList = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        btnStart = findViewById(R.id.btn_start_anim);
        btnReset = findViewById(R.id.btn_start_reset);
        gridView = findViewById(R.id.gv);

        btnStart.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        initDatas(8);
        myAdapter = new MyAdapter();
        gridView.setAdapter(myAdapter);
    }

    private void initDatas(int size) {
        stringList.clear();
        for (int i = 0; i < size; i++) {
            stringList.add("第" + (i + 1) + "项");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_anim:
                int size = (int) (Math.random() * 9);
                if (size < 1) {
                    size = 2;
                }
                initDatas(size);
                myAdapter.notifyDataSetChanged();
                anim(2);
                break;
            case R.id.btn_start_reset:
                initDatas(8);
                myAdapter.notifyDataSetChanged();
                anim(2);

                break;
        }
    }

    private void anim(int animIndex) {
        ObjectAnimator visToInvis = null;
        switch (animIndex) {
            case 1:
                //效果一(此处效果顺序与效果图一一对应)
                //final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(ingridView, "rotationX",-90f, 0f);
                visToInvis = ObjectAnimator.ofFloat(gridView, "rotation", 0, 90,0);

                break;
            case 2:
                //效果二
                visToInvis = ObjectAnimator.ofFloat(gridView, "rotationY", -90f, 0f);
//                visToInvis = ObjectAnimator.ofFloat(gridView, "rotationY", 0f, 90f,0f,50);
                break;
            default:
                visToInvis = ObjectAnimator.ofFloat(gridView, "rotationY", -90f, 0f);
                break;

        }
        visToInvis.setDuration(500);
        visToInvis.start();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int i) {
            return stringList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyHolder myHolder;
            if (view == null) {
                myHolder = new MyHolder();
                view = LayoutInflater.from(Activity1.this).inflate(R.layout.item_gv, null);
                myHolder = new MyHolder();
                myHolder.textView = view.findViewById(R.id.tv);
                view.setTag(myHolder);
            } else {
                myHolder = (MyHolder) view.getTag();
            }

            if (myHolder.textView != null) {
                myHolder.textView.setText(stringList.get(i));
            }
            return view;
        }

        class MyHolder {
            TextView textView;
        }
    }
}
