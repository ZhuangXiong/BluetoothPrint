package com.haomawang.bluetoothprint.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * 类描述：
 * 创建人：Xemenes
 * 创建时间：2016/11/17
 */

public interface IBluetoothConnect {

//    public void connected();
//
//    public void connecting();
//
//    public void noneConnected();
//
//    public void listen();

    public void isConnected(boolean connected);

    public void connectedDevice(BluetoothDevice bluetoothDevice);

    public void noneConnected();
}
