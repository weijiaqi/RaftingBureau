package com.drifting.bureau.util;


import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * @description 勾选框
     */
    public static String checkProtocol(CheckBox checkBox) {
        String result = null;
        if (!checkBox.isChecked()) {
            result = "请阅读并勾选用户协议!";
        }
        return result;
    }

    /**
     * @description 手机号校验
     */
    public static String checkMobile(String mobile) {
        String result = null;
        if (isEmpty(mobile)) {
            result = "请输入手机号!";
        } else if (!VerifyUtil.verifyMobile(mobile)) {
            result = "请输入正确的手机号";
        }
        return result;
    }

    /**
     * 判断字符串非空判断
     *
     * @param str
     * @return true空 false非空
     */
    public static boolean isEmpty(String str) {
        if (str != null && str.length() != 0) {
            return false;
        }
        return true;
    }

    public static String formatNullString(String input) {
        if (TextUtils.isEmpty(input) || TextUtils.equals("null", input.toLowerCase())) {
            return "";
        }
        return input;
    }


    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long time) {
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CCT"));
            Date date = new Date(time * 1000);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return getCurrentTime();
        }
    }

    /**
     * 将时间戳转换为时间
     */
    public static long stampToLong(String time, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatter.parse(time);
            if (date == null) {
                return 0;
            } else {
                long currentTime = date.getTime();
                return currentTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    /**
     * MD5加密
     */
    public static String md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = new byte[0];
        try {
            byteArray = inStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 保留两位小数点
     * @param frontValue
     * @return
     */
    public static String frontValue(double frontValue) {
        java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
        String str = myformat.format(frontValue);
        return str;
    }
}
