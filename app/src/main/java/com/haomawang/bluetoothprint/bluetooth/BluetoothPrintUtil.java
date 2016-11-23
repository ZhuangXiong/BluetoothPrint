package com.haomawang.bluetoothprint.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 类描述：
 * 创建人：Xemenes
 * 创建时间：2016/11/21
 */

public class BluetoothPrintUtil {

    static BluetoothListener mBluetoothListener;
    static LinkedHashMap<String, String> mContext;
    static BluetoothService bluetoothService;
    static BluetoothAdapter bluetoothAdapter;
    static String printDeviceAddress ;
    static Set<BluetoothDevice> devices;

    public static void print(LinkedHashMap<String, String> content, BluetoothListener bluetoothListener) {

        mContext = content;
        mBluetoothListener = bluetoothListener;

        if (!isSupportBluetooth()) {
            mBluetoothListener.setOnBluetoothListener(BluetoothState.PRINT_BLUETOOTH_NONSUPPORT);
            return;
        }

        if (!isOpenBluetooth()) {
            mBluetoothListener.setOnBluetoothListener(BluetoothState.PRINT_BLUETOOTH_CLOSE);
            return;
        }

        if (!isBindPrintDevice()) {
            mBluetoothListener.setOnBluetoothListener(BluetoothState.PRINT_BLUETOOTH_UNBIND);
            return;
        }

        conectedDevice();
    }

    public static void registerBluetoothPrint(Context context) {

        if(bluetoothService == null) {
            bluetoothService = new BluetoothService(context, mHandler);
            bluetoothAdapter = bluetoothService.getmAdapter();
        }

    }

    /**
     * 是否支持蓝牙
     *
     * @return
     */
    public static boolean isSupportBluetooth() {

        return bluetoothAdapter == null?false:true;
    }

    /**
     * 是否打开蓝牙
     *
     * @return
     */
    public static boolean isOpenBluetooth() {

        return bluetoothAdapter.isEnabled()?true:false;
    }

    /**
     * 是否已绑定打印设备
     */
    public static boolean isBindPrintDevice() {
        devices = bluetoothAdapter.getBondedDevices();

        if(devices == null || devices.size() == 0){
            return false;
        }

        for (BluetoothDevice device : devices) {
           printDeviceAddress = device.getAddress();
        }

        return true;
    }

    /**
     * 连接打印设备
     */
    public static  void  conectedDevice() {

        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(printDeviceAddress);
        bluetoothService.connect(device);
    }

    /**
     * 打印内容
     */
    public static void printContent() {

    }

    /**
     * 设置选择打印设备的物理地址
     */
    public static void setDeviceAddress(String address){

        if(devices != null){
            printDeviceAddress = address;
        }
    }

    /**
     * 获得已配对的绑定设备号
     * @return
     */
    public static Set<BluetoothDevice> getDevices(){
        isBindPrintDevice();
        return devices;
    }

    private final static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if(mBluetoothListener ==null){
                return;
            }

            switch (msg.what) {
                case BluetoothState.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
//                            "已连接:"
                            mBluetoothListener.setOnBluetoothListener(BluetoothService.STATE_CONNECTED);
                            break;
                        case BluetoothService.STATE_CONNECTING:
//                           "正在连接..."
                            mBluetoothListener.setOnBluetoothListener(BluetoothService.STATE_CONNECTING);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
//                            "无连接"
                            mBluetoothListener.setOnBluetoothListener(BluetoothService.STATE_NONE);

                            break;
                    }
                    break;
                case BluetoothState.MESSAGE_WRITE:

                    break;
                case BluetoothState.MESSAGE_READ:

                    break;
                case BluetoothState.MESSAGE_DEVICE_NAME:


                    break;
                case BluetoothState.MESSAGE_TOAST:

                    break;
            }
        }
    };
}
