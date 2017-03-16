package com.example.administrator.chendanproject;

import android.app.Application;

import org.xutils.x;


/**
 * Created by Administrator on 2017/3/7.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
