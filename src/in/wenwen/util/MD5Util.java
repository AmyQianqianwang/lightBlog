/*
 * Copyright (c) jiucheng.org
 */
package in.wenwen.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {


    public static String getMd5ByBytes(byte[] bytes) {
        String strMD5 = null;
        MessageDigest md = null;
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] bt = md.digest();
            int j = bt.length;
            char strt[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = bt[i];
                strt[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strt[k++] = hexDigits[byte0 & 0xf];
            }
            strMD5 = new String(strt);
            // BASE64Encoder encoder = new BASE64Encoder();
            // strMD5 = encoder.encode(bt);
            // return strMD5;
        } catch (NoSuchAlgorithmException e) {
        }
        return strMD5;
    }

    public static String getMd5(String str){
        String md5 = null;
        try {
            md5 = getMd5ByBytes(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5;
    }

    // public static String getSha(String str) throws NoSuchAlgorithmException{
    // String strSHA = null;
    // MessageDigest md = MessageDigest.getInstance("SHA");
    // byte[] bt = md.digest(str.getBytes());
    // BASE64Encoder encoder = new BASE64Encoder();
    // strSHA = encoder.encode(bt);
    // return strSHA;
    // }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(getMd5("demo"));//e10adc3949ba59abbe56e057f20f883e
    }
}
