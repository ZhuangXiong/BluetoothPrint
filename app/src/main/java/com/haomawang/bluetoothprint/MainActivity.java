package com.haomawang.bluetoothprint;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.haomawang.bluetoothprint.bluetooth.BluetoothListener;
import com.haomawang.bluetoothprint.bluetooth.BluetoothPrintUtil;
import com.haomawang.bluetoothprint.bluetooth.BluetoothState;

import java.util.LinkedHashMap;




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
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("充值项目","50元话费");
        linkedHashMap.put("充值对象","153445645");
        linkedHashMap.put("充值时间","2015/10/20");
        linkedHashMap.put("实付金额","50元");
        linkedHashMap.put("充值状态","已充值");

        BluetoothPrintUtil.print(linkedHashMap,"话费清单", new BluetoothListener() {
            @Override
            public void setOnBluetoothListener(int state) {
                switch (state){

                    /**设备不支持蓝牙**/
                    case BluetoothState.PRINT_BLUETOOTH_NONSUPPORT:

                        break;

                    /**无法连接设备，检查设备是否打开**/
                    case BluetoothState.PRINT_BLUETOOTH_CANNOT_CONNECTED:
                        break;

                    /**蓝牙关闭**/
                    case BluetoothState.PRINT_BLUETOOTH_CLOSE:
                        break;

                    /**无配对设备**/
                    case BluetoothState.PRINT_BLUETOOTH_UNBIND:

                        break;

                    /**正在连接**/
                    case BluetoothState.PRINT_BLUETOOTH_CONNECTING:

                        break;

                    /**无连接**/
                    case BluetoothState.PRINT_BLUETOOTH_CONNECTNONE:

                        break;

                    /**已连接**/
                    case BluetoothState.PRINT_BLUETOOTH_CONNECTED:

                        break;

                }
            }
        });
    }

}
