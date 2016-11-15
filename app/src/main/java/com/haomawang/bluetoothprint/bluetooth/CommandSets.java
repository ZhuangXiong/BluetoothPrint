package com.haomawang.bluetoothprint.bluetooth;


public class CommandSets {

    /**
     *  复位打印机设置
     */
    public final static byte[] RESET = {0x1b, 0x40};

    /**
     *  标准ASCII字体
     */
    public final static byte[] ASCII = { 0x1b, 0x4d, 0x00 };

    /**
     *  压缩ASCII字体
     */
    public final static byte[] ZIPASCII = {0x1b, 0x4d, 0x01};

    /**
     *  普通字体大小
     */
    public final static byte[] NORMAL_SIZE = {0x1d, 0x21, 0x00};

    /**
     *  字体中等大小
     */
    public final static byte[] MEDIUM_SIZE = { 0x1d, 0x21, 0x02};

    /**
     *  字体大小再加倍
     */
    public final static byte[] BIG_SIZE = {0x1d, 0x21, 0x11};

    /**
     *  字体加粗
     */
    public final static byte[] BOLD  = {0x1b, 0x45, 0x01};

    /**
     *  字体加粗取消
     */
    public final static byte[] BOLD_CANCEL  = { 0x1b, 0x45, 0x00};

    /**
     *  倒置打印
     */
    public final static byte[] INVERSION  = { 0x1b, 0x7b, 0x01};

    /**
     *  倒置打印取消
     */
    public final static byte[] INVERSION_CANCEL  = {  0x1b, 0x7b, 0x00};

    /**
     *  左对齐
     */
    public final static byte[] 	LEFT_JUSTIFYING  = { 0x1b, 0x61, 0x30};

    /**
     * 右对齐
     */
    public final static byte[] RIGHT_JUSTIFYING  = { 0x1b, 0x61, 0x32};

    /**
     * 居中对齐
     */
    public final static byte[] CENTER_JUSTIFYING  = { 0x1b, 0x61, 0x31};
}
