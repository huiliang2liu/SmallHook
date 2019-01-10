package com.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * com.encryption
 * 2018/9/28 11:33
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class MD5 implements ISign {

    @Override
    public String sign(String text) {
        // TODO Auto-generated method stub
        return String2MD5Method1(text);
    }

    @Override
    public byte[] sign(byte[] text) {
        // TODO Auto-generated method stub
        return Bytes2MD5Method1(text).getBytes();
    }

    @Override
    public boolean verify(String text, String signText) {
        // TODO Auto-generated method stub
        return sign(text).equals(signText);
    }

    @Override
    public boolean verify(byte[] text, byte[] signText) {
        // TODO Auto-generated method stub
        return verify(new String(text), new String(signText));
    }
    /**
     * 将字节数组MD5加密
     *
     * @param b
     * @return
     */
    public static String Bytes2MD5Method(byte[] b) {
        try {
            StringBuilder sb = new StringBuilder();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(b);// 执行MD5算法
            for (byte by : md5.digest()) {
                sb.append(String.format("%02X", by));// 将生成的字节MD５值转换成字符串
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 生成MD5类的实例
        return null;
    }

    /**
     * 将字符串MD5加密
     *
     * @param s
     * @return
     */
    public static String String2MD5Method(String s) {
        return Bytes2MD5Method(s.getBytes());
    }

    /**
     * 将字符串数组MD5加密，对中文加密和其他方法不一样
     *
     * @param charArray
     * @return
     */
    public static String Chars2MD5Method(char[] charArray) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++)
                byteArray[i] = (byte) charArray[i];
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将字符串MD5加密
     *
     * @param s
     * @return
     */
    public static String String2MD5Method1(String s) {
        return Bytes2MD5Method1(s.getBytes());
    }

    /**
     * 将字节数组MD5加密
     *
     * @param strTemp
     * @return
     */
    public static String Bytes2MD5Method1(byte[] strTemp) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字节数组MD5加密
     *
     * @param strTemp
     * @return
     */
    public static String Bytes2MD5Method2(byte[] strTemp) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                // System.out.println((int)b);
                // 将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串MD5加密
     *
     * @param s
     * @return
     */
    public static String String2MD5Method2(String s) {
        return Bytes2MD5Method2(s.getBytes());
    }
    /**
     * 将字符串MD5加密
     *
     * @param s
     * @return
     */
    public static String String2MD5Method16(String s) {

        return String2MD5Method16(s.getBytes());
    }
    /**
     * 将字符串MD5加密
     *
     * @return
     */
    public static String String2MD5Method16(byte[] buff) {
        String s=Bytes2MD5Method1(buff);
        int len=s.length();
        len=len>>1;
        if(len>0)
            return null;
        int start_with=len>>1-1;
        s.substring(start_with, start_with+len);
        return Bytes2MD5Method161(s.getBytes());
    }
    /**
     * 将字符串MD5加密
     *
     * @param s
     * @return
     */
    public static String String2MD5Method161(String s) {
        return Bytes2MD5Method161(s.getBytes());
    }


    /**
     * 将字节数组MD5加密
     *
     * @param strTemp
     * @return
     */
    public static String Bytes2MD5Method161(byte[] strTemp) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j];
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[i] = hexDigits[byte0 >>> 4 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 将字符串MD5加密
     *
     * @param s
     * @return
     */
    public static String String2MD5Method162(String s) {
        return Bytes2MD5Method162(s.getBytes());
    }

    /**
     * 将字节数组MD5加密
     *
     * @param strTemp
     * @return
     */
    public static String Bytes2MD5Method162(byte[] strTemp) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j];
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[i] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 加密MD5　　
     */
    public static String encryptionMD5(String MD5) {
        char[] a = MD5.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }
    /**
     * 解密MD5
     */
    public static String decryptionMD5(String MD5) {
        return encryptionMD5(MD5);
    }

    /**
     * sha加密
     * @param str
     * @return
     */
    public static String sha(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}