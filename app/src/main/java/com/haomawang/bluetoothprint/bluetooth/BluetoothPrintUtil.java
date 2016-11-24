package com.haomawang.bluetoothprint.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.Set;

import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.DEVICE_NAME;

/**
 * 类描述：
 * 创建人：Xemenes
 * 创建时间：2016/11/21
 */

public class BluetoothPrintUtil {

    static BluetoothListener mBluetoothListener;
    static LinkedHashMap<String, String> mContent;
    static BluetoothService bluetoothService;
    static BluetoothAdapter bluetoothAdapter;
    static String printDeviceAddress ;
    static Set<BluetoothDevice> devices;
    static String mTitle = "";


    public static void print(LinkedHashMap<String, String> content,String title, BluetoothListener bluetoothListener) {
        mTitle = title;
        mContent = content;
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
            Log.d("isBindPrintDevice","无绑定设备");
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
                            mBluetoothListener.setOnBluetoothListener(BluetoothState.PRINT_BLUETOOTH_CONNECTED);

                            PrintTemplet.build()
                                    .title(mTitle)
                                    .content(mContent)
                                    .print(bluetoothService);
                            Log.d("Handler","已连接");
                            break;
                        case BluetoothService.STATE_CONNECTING:
//                           "正在连接..."
                            mBluetoothListener.setOnBluetoothListener(BluetoothState.PRINT_BLUETOOTH_CONNECTING);
                            Log.d("Handler","正在连接");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
//                            "无连接"
                            mBluetoothListener.setOnBluetoothListener(BluetoothState.PRINT_BLUETOOTH_CONNECTNONE);
                            Log.d("Handler","无连接");
                            break;
                    }
                    break;
                case BluetoothState.MESSAGE_WRITE:
                    Log.d("Handler","MESSAGE_WRITE");
                    break;
                case BluetoothState.MESSAGE_READ:
                    Log.d("Handler","MESSAGE_READ");
                    break;
                case BluetoothState.MESSAGE_DEVICE_NAME:
                    Log.d("Handler", msg.getData().getString(DEVICE_NAME));

                    break;
                case BluetoothState.MESSAGE_TOAST:
                    Log.d("Handler",msg.toString());
                    break;
            }
        }
    };
}
