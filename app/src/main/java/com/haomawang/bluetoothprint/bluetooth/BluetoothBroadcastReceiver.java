package com.haomawang.bluetoothprint.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 类描述：
 * 创建人：Xemenes
 * 创建时间：2016/11/17
 */

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    IBluetoothConnect iBluetoothConnect;

    public BluetoothBroadcastReceiver(IBluetoothConnect iBluetoothConnect) {
        this.iBluetoothConnect = iBluetoothConnect;

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            iBluetoothConnect.connectedDevice(device);

        } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            iBluetoothConnect.noneConnected();
            Toast.makeText(context,"请检查蓝牙设备是否已匹配",Toast.LENGTH_SHORT);
        }
    }
}
