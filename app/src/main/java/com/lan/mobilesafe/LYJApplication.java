package com.lan.mobilesafe;

import android.app.Application;

import org.xutils.x;

/**
 * Created by lan on 2016/5/27.
 */
public class LYJApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//Xutils初始化
    }
}
