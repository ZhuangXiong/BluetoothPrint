package com.haomawang.bluetoothprint;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.haomawang.bluetoothprint.bluetooth.BluetoothListener;
import com.haomawang.bluetoothprint.bluetooth.BluetoothPrintUtil;

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
                }
            }
        });
    }



}
