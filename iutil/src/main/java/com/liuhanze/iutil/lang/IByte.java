package com.liuhanze.iutil.lang;

import java.util.Locale;

public final class IByte {

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private IByte(){

    }

    public static String byte2HexString(byte byte1) {
        return bytes2HexString(new byte[]{byte1});
    }

    /**
     * byte数组转16进制String
     * <p>
     * 例如：
     * </p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字节数组
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /***
     * byte[] 转16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static boolean isEmpty(byte[] data){
        return data == null || data.length == 0;
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param data 要转换的字节数组
     * @return 转换后的结果
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        if (IString.isEmpty(hexString)) {
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return d;
    }

    /**
     * hexString转byteArr
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (IString.isEmpty(hexString)) {
            return null;
        }
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 一位byte转int【无符号】
     *
     * @param b
     * @return 【0 ~ 255】
     */
    public static int byteToIntUnSigned(byte b) {
        return b & 0xFF;
    }

    /**
     * 一位byte转int【有符号】
     *
     * @param b
     * @return 【-128 ~ 127】
     */
    public static int byteToIntSigned(byte b) {
        return b;
    }

    /**
     * int转Byte 【仅对0~255的整型有效】
     *
     * @param value 【0~255】
     * @return
     */
    public static byte intToByte(int value) {
        return (byte) value;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(【小端】低地址放地位，高地址放高位)的顺序。 和
     * {@link #bytesToIntLittleEndian(byte[], int)}配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytesLittleEndian(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) (value & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[3] = (byte) ((value >> 24) & 0xFF);
        return src;
    }

    /**
     * 将int数值填充至byte数组的指定位置，本方法适用于(【小端】低地址放低位，高地址放高位)的顺序
     *
     * @param value  填充的int值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillIntToBytesLittleEndian(int value, byte[] src, int offset) {
        src[offset] = (byte) (value & 0xFF);
        src[offset + 1] = (byte) ((value >> 8) & 0xFF);
        src[offset + 2] = (byte) ((value >> 16) & 0xFF);
        src[offset + 3] = (byte) ((value >> 24) & 0xFF);
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(【大端】低地址放高位，高地址放低位)的顺序。 和
     * {@link #bytesToIntBigEndian(byte[], int)}配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytesBigEndian(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值填充至byte数组的指定位置，本方法适用于(【大端】低地址放高位，高地址放低位)的顺序
     *
     * @param value  填充的int值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillIntToBytesBigEndian(int value, byte[] src, int offset) {
        src[offset] = (byte) ((value >> 24) & 0xFF);
        src[offset + 1] = (byte) ((value >> 16) & 0xFF);
        src[offset + 2] = (byte) ((value >> 8) & 0xFF);
        src[offset + 3] = (byte) (value & 0xFF);
    }

    /**
     * byte数组中取int数值，本方法适用于(【小端】低地址放低位，高地址放高位)的顺序，和
     * {@link #intToBytesLittleEndian(int)}配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToIntLittleEndian(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16) | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(【大端】低地址放高位，高地址放低位)的顺序。和{@link #intToBytesBigEndian(int)}
     * 配套使用
     */
    public static int bytesToIntBigEndian(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16) | ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
        return value;
    }



}
