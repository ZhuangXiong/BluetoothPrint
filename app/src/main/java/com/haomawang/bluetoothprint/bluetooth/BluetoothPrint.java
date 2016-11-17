package com.haomawang.bluetoothprint.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 类描述：蓝牙打印统一的入口
 * 创建人：Xemenes
 * 创建时间：2016/11/17
 */

public class BluetoothPrint {

    private static OutputStream outputStream;
    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothService mService;

    /**
     * 注册蓝牙服务
     * @param activity
     * @param handler
     */
    public static void registerBluetoothService(Activity activity,Handler handler){
        if(mService == null) {
            mService = new BluetoothService(activity, handler);
        }
    }

    /**
     * 连接打印设备
     * @param macAddress 物理地址
     */
    public static void connectedDevice(String macAddress){

        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(macAddress);
        mService.connect(device);
    }

    /**
     *   判断蓝牙是否打开，未连接则选择打开
     */
    public static boolean bluetoothIsEnable(Activity activity) {

        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }

        /**该设备不支持蓝牙**/
        if(mBluetoothAdapter == null){
            Toast.makeText(activity,"该设备不支持蓝牙",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            /** 打开蓝牙**/
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableIntent, BluetoothState.REQUEST_ENABLE_BT);
            return false;
        }
        return true;
    }


//    /**
//     * 发送打印指令
//     * @param command 打印指令
//     */
//    public static void postCommand(byte[] command){
//        try {
//            outputStream.write(command);
//            outputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 打印内容，不带换行符号
//     * @param text 文字内容
//     */
//    public static void printText(String text){
//        try {
//            byte[] data = text.getBytes("GB2312");
//            outputStream.write(data,0,data.length);
//            outputStream.flush();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 打印内容，带换行符号
//     * @param text 文字内容
//     */
//    public static void printTextNewLine(String text){
//        try {
//            byte[] data = (text+"\n").getBytes("GB2312");
//            outputStream.write(data,0,data.length);
//            outputStream.flush();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



//    /**
//     * 扫描搜索设备
//     * @param activity
//     * @param ScanCallback
//     */
//    public static void startFindBluetoothPrint(Activity activity,ScanCallback ScanCallback){
//
//    }

    /**
     * 获得已配对的设备
     * @param activity
     * @return
     */
    public static  Set<BluetoothDevice>  getBondDevice(Activity activity){

        Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();

        if(devices == null || devices.size() == 0){
            Toast.makeText(activity,"无配对设备",Toast.LENGTH_SHORT).show();
            return null;
        }
        return devices;
    }

    public static void print(int command,String text){

    }

}
