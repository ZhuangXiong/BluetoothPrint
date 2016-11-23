package haomawang.printdemo.bluetooth;

public class BluetoothState {

	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	public static final int REQUEST_CONNECT_DEVICE = 1;
	public static final int REQUEST_ENABLE_BT = 2;

	/**设备不支持蓝牙**/
	public static final int PRINT_BLUETOOTH_NONSUPPORT = 0x10;
	/**蓝牙关闭**/
	public static final int PRINT_BLUETOOTH_CLOSE = 0x11;
	/**无配对设备**/
	public static final int PRINT_BLUETOOTH_UNBIND = 0x12;
	/**无法连接设备，检查设备是否打开**/
	public static final int PRINT_BLUETOOTH_CANNOT_CONNECTED = 0x13;

	/**已连接**/
	public static final int PRINT_BLUETOOTH_CONNECTED = 0x21;
	/**正在连接**/
	public static final int PRINT_BLUETOOTH_CONNECTING = 0x22;
	/**无连接**/
	public static final int PRINT_BLUETOOTH_CONNECTNONE = 0x23;

}
