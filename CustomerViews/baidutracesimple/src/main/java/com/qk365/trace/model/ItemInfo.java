package com.qk365.trace.model;


import com.qk365.trace.BaseActivity;

/**
 * Created by baidu on 17/1/13.
 */

public class ItemInfo {
    public int titleIconId;
    public int titleId;
    public int descId;
    public Class<? extends BaseActivity> clazz;

    public ItemInfo(int titleIconId, int titleId, int descId, Class<? extends BaseActivity> clazz) {
        this.titleIconId = titleIconId;
        this.titleId = titleId;
        this.descId = descId;
        this.clazz = clazz;
    }
}
