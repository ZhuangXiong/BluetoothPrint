package haomawang.printdemo.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class PrintTemplet {


	private PrintTemplet(){

	}
	// 代理商名称
	String shopName = "";
	// 订单时间
	String orderTime = "";
	// 放在中间的内容，换行直接用"\n"
	String content = "";
	// 总价
	String total = "";

	String newLinew = "\n";

	static Activity mActivity = null;

	public PrintTemplet shopName(String shopName) {
		this.shopName = shopName;
		return this;
	}

	public PrintTemplet orderTime(String orderTime) {
		this.orderTime = orderTime;
		return this;
	}

	public PrintTemplet content(String content) {
		this.content = content;
		return this;
	}

	public PrintTemplet total(String total) {
		this.total = total;
		return this;
	}

	public static PrintTemplet build(Activity activity) {
		mActivity = activity;
		return new PrintTemplet();
	}

	BluetoothService bluetoothService;
	/**
	 * 模板打印
	 * @param bluetoothService
	 * @return
	 */
	public boolean print(BluetoothService bluetoothService) {

		this.bluetoothService = bluetoothService;

		if (bluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
			Toast.makeText(bluetoothService.getContext(), "未连接设备",
					Toast.LENGTH_SHORT).show();
			return false;
		}


		try {

			//排版
			bluetoothService.printSize(2);
			bluetoothService.printCenter();
			writeTo(shopName);

			bluetoothService.printSize(1);
			bluetoothService.printLeft();
			writeTo(orderTime);

			bluetoothService.printSize(1);
			bluetoothService.printLeft();
			writeTo(createTime());

			bluetoothService.printSize(1);
			bluetoothService.printLeft();
			writeTo(content);

			bluetoothService.printSize(1);
			bluetoothService.printRight();
			writeTo(total);

			bluetoothService.printReset();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return true;


	}

	/**
	 * 订单创建时间
	 * @return
	 */
	private String createTime(){

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	/**
	 * 写入打印机
	 * @param printContent 打印的字段
	 * @throws UnsupportedEncodingException
	 */
	private void writeTo(String printContent ) throws UnsupportedEncodingException{

		bluetoothService.write((printContent+newLinew).getBytes("GB2312"));

	}


	BluetoothAdapter bluetoothAdapter;
	//判断蓝牙是否打开，未连接则选择打开
	public boolean bluetoothIsEnable(Activity activity) {

		if (bluetoothAdapter == null) {
			bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			return false;
		}

		if (!bluetoothAdapter.isEnabled()) {
			// 打开蓝牙
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			activity.startActivityForResult(enableIntent, BluetoothState.REQUEST_ENABLE_BT);
			return false;
		}
		return true;
	}

	BroadcastReceiver broadcastReceiver;
	/**
	 * 扫描设备
	 * @param broadcastReceiver 广播接收扫描情况
     */
	public void scannerDevice(BroadcastReceiver broadcastReceiver){
		this.broadcastReceiver = broadcastReceiver;

		if(bluetoothAdapter == null){
			return;
		}
		if (bluetoothAdapter.isDiscovering()) {
			bluetoothAdapter.cancelDiscovery();
		}
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		mActivity.registerReceiver(broadcastReceiver,filter);

		 filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		mActivity.registerReceiver(broadcastReceiver,filter);

		bluetoothAdapter.startDiscovery();
	}

	/**
	 * 连接打印设备
	 */
	public void connectDevice(BluetoothDevice bluetoothDevice){
		bluetoothService.connect(bluetoothDevice);
	}

	/**
	 * 注销蓝牙扫描
	 */
	public void cancelDiscovery(){
		if(bluetoothAdapter != null){
			bluetoothAdapter.cancelDiscovery();
		}
		if(broadcastReceiver == null) {
			mActivity.unregisterReceiver(broadcastReceiver);
		}
	}
}
