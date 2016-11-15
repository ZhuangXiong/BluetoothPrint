package com.haomawang.bluetoothprint;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haomawang.bluetoothprint.bluetooth.BluetoothService;
import com.haomawang.bluetoothprint.bluetooth.PrintTemplet;

import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.DEVICE_NAME;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_DEVICE_NAME;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_READ;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_STATE_CHANGE;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_TOAST;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_WRITE;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.TOAST;

public class MainActivity extends Activity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void connect(View view){
        printTemplet.scannerDevice(mReceiver);
    }

    public void print(View view){
        printTemplet.shopName("shopName")
					.content("充值话费 50")
					.orderTime("2015-10-12")
					.print(bluetoothService);
    }

    PrintTemplet printTemplet;
    BluetoothService bluetoothService ;
    @Override
    protected void onStart() {
        super.onStart();
        printTemplet = PrintTemplet.build(this);
        printTemplet.bluetoothIsEnable(this);
        bluetoothService = new BluetoothService(this,mHandler);
    }


    String mConnectedDeviceName = "0";
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            textView.setText("已连接:");
                            textView.append(mConnectedDeviceName);
                            break;

                        case BluetoothService.STATE_CONNECTING:
                            textView.setText("正在连接...");
                            break;

                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            textView.setText("无连接");
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    //byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    //String writeMessage = new String(writeBuf);
                    break;
                case MESSAGE_READ:
                    //byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    //String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "连接至"
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    int count = 0;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                textView.setText(device.getName());
                printTemplet.connectDevice(device);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

                }

                count++;
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle("选择连接的设备");
                if (count == 0) {
                   textView.setText("无可连接设备");
                }
            }
        }
    };

}
