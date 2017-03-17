package com.suozhang.smarthome.base;

import android.app.Application;


import com.suozhang.smarthome.util.L;

import org.xutils.x;

/**
 * Created by LIJUWEN on 2016/11/15.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        L.setDebug(true);
        x.Ext.init(this);
        x.Ext.setDebug(true); // 开启debug会影响性能
    }
}
