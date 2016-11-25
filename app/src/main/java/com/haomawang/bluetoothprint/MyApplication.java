package com.haomawang.bluetoothprint;

import android.app.Application;

import haomawang.bluetoothlib.BluetoothPrintUtil;


/**
 * 类描述：
 * 创建人：Xemenes
 * 创建时间：2016/11/24
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothPrintUtil.registerBluetoothPrint(this);
    }

}
