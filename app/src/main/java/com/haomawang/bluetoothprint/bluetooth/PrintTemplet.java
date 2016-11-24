package com.haomawang.bluetoothprint.bluetooth;

import android.app.Activity;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrintTemplet {
	/**
	 * 打印纸一行最大的字节
	 */
	private static final int LINE_BYTE_SIZE = 32;

	/**
	 * 打印三列时，中间一列的中心线距离打印纸左侧的距离
	 */
	private static final int LEFT_LENGTH = 16;

	/**
	 * 打印三列时，中间一列的中心线距离打印纸右侧的距离
	 */
	private static final int RIGHT_LENGTH = 16;

	/**
	 * 打印三列时，第一列汉字最多显示几个文字
	 */
	private static final int LEFT_TEXT_MAX_LENGTH = 5;

	/**
	 * 标题名称
	 */
	String title = "";

	/**
	 * 有序的Map,格式： 充值对象(key)   153592668xx**(value)
	 */
	static LinkedHashMap<String,String> printContens = new LinkedHashMap<>();

	BluetoothService bluetoothService;
	String newLinew = "\n";

	private PrintTemplet(){

	}

	public PrintTemplet title(String title) {
		this.title = title;
		return this;
	}

	public PrintTemplet content(LinkedHashMap<String,String> printContens) {
		this.printContens = printContens;
		return this;
	}



	public static PrintTemplet build() {
		printContens =  new LinkedHashMap<>();;
		return new PrintTemplet();
	}

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
			/**排版**/
			bluetoothService.printSize(1);
			bluetoothService.printCenter();
			writeTo(title);
			printDottedLine();
			if(printContens.size() != 0){
				Iterator iter = printContens.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					String val = (String) entry.getValue();
					printContent(key,val);
				}
			}
			printDottedLine();
			printContent("打印日期",createTime());
			writeTo("\n\n\n");
			bluetoothService.printReset();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void printContent(String key,String value){

		try {

			bluetoothService.printSize(3);
			bluetoothService.printLeft();
			writeTo(printTwoData(key,value));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打印两列
	 *
	 * @param leftText  左侧文字
	 * @param rightText 右侧文字
	 * @return
	 */
	private static String printTwoData(String leftText, String rightText) {

		StringBuilder sb = new StringBuilder();
		int leftTextLength = BytesUtil.getBytesLength(leftText);
		int rightTextLength = BytesUtil.getBytesLength(rightText);
		sb.append(leftText);

		// 计算两侧文字中间的空格
		int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - rightTextLength;

		for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
			sb.append(" ");
		}
		sb.append(rightText);
		return sb.toString();
	}


	/**横线**/
	private void printLine(){
		try {
			bluetoothService.printSize(3);
			bluetoothService.printCenter();
			writeTo("________________");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**虚线**/
	private void printDottedLine(){
		try {
			bluetoothService.printSize(3);
			bluetoothService.printCenter();
			writeTo("--------------------------------");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}




	/**
	 * 订单创建时间
	 * @return
	 */
	private String createTime(){

		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm ");
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






}
