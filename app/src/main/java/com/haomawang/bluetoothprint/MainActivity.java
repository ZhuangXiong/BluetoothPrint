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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haomawang.bluetoothprint.bluetooth.BluetoothBroadcastReceiver;
import com.haomawang.bluetoothprint.bluetooth.BluetoothPrint;
import com.haomawang.bluetoothprint.bluetooth.BluetoothService;
import com.haomawang.bluetoothprint.bluetooth.IBluetoothConnect;
import com.haomawang.bluetoothprint.bluetooth.PrintTemplet;

import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.DEVICE_NAME;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_DEVICE_NAME;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_READ;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_STATE_CHANGE;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_TOAST;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.MESSAGE_WRITE;
import static com.haomawang.bluetoothprint.bluetooth.BluetoothState.TOAST;

public class MainActivity extends Activity  {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);



    }

    public void print(View view){
        Log.d("print","connected........");
    }

    public void connect(View view){

    }



}
