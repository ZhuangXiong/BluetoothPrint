
[TOC]

# Android 蓝牙打印
> 入口和回调方便，目前只针对一个打印模板做了封装。 没有扫描设备功能，后期会加入。用户需要先去  设置---蓝牙---配对设备，配对完成后，可以获取手机上已配对的设备信息，从而进行连接打印。

## 使用方法

### 在Application的onCreate()方法里注册蓝牙打印
```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothPrintUtil.registerBluetoothPrint(this);
    }
}
```
### 调用打印

```
//这块是打印的中间内容
LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
linkedHashMap.put("充值项目","50元话费");
linkedHashMap.put("充值对象","153445645");
linkedHashMap.put("充值时间","2015/10/20");
linkedHashMap.put("实付金额","50元");
linkedHashMap.put("充值状态","已充值");

//话费清单是一个标题参数
BluetoothPrintUtil.print(linkedHashMap,"话费清单", new BluetoothListener() {
    @Override
    public void setOnBluetoothListener(int state) {
        switch (state){

            /**设备不支持蓝牙**/
            case BluetoothState.PRINT_BLUETOOTH_NONSUPPORT:
                //可填写业务逻辑
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
```

## 打印效果图

![打印效果](http://7xpu0p.com1.z0.glb.clouddn.com/QQ%E5%9B%BE%E7%89%8720161124172527.png)

## 类描述

### BluetoothPrintUtil.class
> 打印工具类，包含打印，检查设备是否支持蓝牙，蓝牙是否开启，是否有绑定（配对）设备，连接打印设备，设置打印设备地址，获得已配对的绑定设备......

重要方法  | 描述
------------- | -------------
setDeviceAddress()  | 更改打印设备的物理地址
getDevices()  | 获得已配对的打印设备

### PrintTemplet.class
> 打印模板类，用于排版打印的样式

重要方法  | 描述
------------- | -------------
print(BluetoothService bluetoothService)  | 打印，需要BluetoothService

### BluetoothService.class

> 修改自[android-BluetoothLeGatt](https://github.com/googlesamples/android-BluetoothLeGatt/blob/master/Application/src/main/java/com/example/android/bluetoothlegatt/BluetoothLeService.java)

### BluetoothState.class

> 状态标识
