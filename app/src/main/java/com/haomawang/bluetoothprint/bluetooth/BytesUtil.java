package com.haomawang.bluetoothprint.bluetooth;

import java.nio.charset.Charset;

/**
 * 类描述：
 * 创建人：Xemenes
 * 创建时间：2016/11/18
 */

public class BytesUtil {

    /**
     * 获取数据长度
     *
     * @param msg
     * @return
     */
    public static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }
}
