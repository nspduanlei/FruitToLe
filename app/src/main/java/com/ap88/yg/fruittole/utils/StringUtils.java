package com.ap88.yg.fruittole.utils;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Some utility methods related with the String class.
 *
 * @author duanlei
 */
public class StringUtils {

  private static final String EMPTY_STRING = "";

  private StringUtils() {
    //Empty
  }

  public static boolean isNullOrEmpty(final String string) {
    return string == null || EMPTY_STRING.equals(string.trim());
  }

  /**
   * aes加密
   *
   * @return
   * @throws Exception
   */
  public static String desEncrypt() throws Exception {
    try {
      String data = "2fbwW9+8vPId2/foafZq6Q==";
      String key = "1234567812345678";
      String iv = "1234567812345678";

      //byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);

      byte[] encrypted1 = Base64.decode(data, 0);

      Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
      SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
      IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

      cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

      byte[] original = cipher.doFinal(encrypted1);
      String originalString = new String(original);
      return originalString;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 验证邮箱输入是否合法
   *
   * @param strEmail
   * @return
   */
  public static String checkEmail(String strEmail) {
    if (isNullOrEmpty(strEmail)) {
      return "邮箱不能为空";
    }
    String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    Pattern p = Pattern.compile(check);
    Matcher m = p.matcher(strEmail);
    if (!m.matches()) {
      return "邮箱格式不正确";
    }
    return "";
  }

  /**
   * 验证省份证号是否合法
   * @return
   */
  public static String checkIdCard(String idCard) {
    if (isNullOrEmpty(idCard)) {
      return "身份证号不能为空";
    }
    String check = "^\\d{17}[\\d|x|X]$";
    Pattern p = Pattern.compile(check);
    Matcher m = p.matcher(idCard);

    if (!m.matches()) {
      return "身份证号格式不正确";
    }
    return "";
  }

  /**
   * 验证是否是手机号码
   *
   * @param str
   * @return
   */
  public static String checkMobile(String str) {
    if (isNullOrEmpty(str)) {
      return "手机号不能为空";
    }
    Pattern pattern = Pattern.compile("^1[0-9]{10}$");
    Matcher matcher = pattern.matcher(str);
    if (!matcher.matches()) {
      return "手机号格式不正确";
    }
    return "";
  }

  private static final int MIN_COUNT = 6;
  private static final int MAX_COUNT = 16;

  /**
   * 验证注册的密码是否合法
   *
   * @param password
   * @return
   */
  public static String checkPassword(String password) {
    if (password.length() < MIN_COUNT || password.length() > MAX_COUNT) {
      return "密码" + MIN_COUNT + "到" + MAX_COUNT + "个字符喔!";
    }
    String strPattern = "[0-9A-Za-z]*";
    Pattern p = Pattern.compile(strPattern);
    Matcher m = p.matcher(password);
    if (!m.matches()) {
      return "密码只能由数字和字母组成喔!";
    }
    return "";
  }

  /**
   * MD5加密
   *
   * @param secret_key
   * @return
   */
  public static String createSign(String secret_key) {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.reset();
      messageDigest.update(secret_key.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException e) {
      System.exit(-1);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    byte[] byteArray = messageDigest.digest();

    StringBuffer md5StrBuff = new StringBuffer();

    for (byte aByteArray : byteArray) {
      if (Integer.toHexString(0xFF & aByteArray).length() == 1)
        md5StrBuff.append("0").append(
            Integer.toHexString(0xFF & aByteArray));
      else
        md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
    }
    return md5StrBuff.toString();
  }

  private static final int NICKNAME_LEN = 14;

  //nickname填充
  public static void setNickname(TextView textView, String nickname) {
    if (nickname.length() > NICKNAME_LEN) {
      textView.setText(nickname.substring(0, NICKNAME_LEN));
    } else {
      textView.setText(nickname);
    }
  }

  public static String checkUserName(String username) {
    if (username.length() < 1) {
      return "用户名长度不能为空";
    }

    if (username.length() > NICKNAME_LEN) {
      return "用户名长度不能超过7位";
    }

    return "";
  }

  public static String checkShopName(String shopName) {
    if (shopName.length() < 1) {
      return "店铺名长度不能为空";
    }

    if (shopName.length() > NICKNAME_LEN) {
      return "店铺名长度不能超过7位";
    }
    return "";
  }

  /**
   * Helper function for making null strings safe for comparisons, etc.
   *
   * @return (s == null) ? "" : s;
   */
  public static String makeSafe(String s) {
    return (s == null) ? "" : s;
  }

  /**
   * @param str
   * @return
   */
  public static String trim(String str) {
    return str == null ? EMPTY_STRING : str.trim();
  }

  /**
   * 将秒数转化成指定格式的时间字符串
   * MM月dd日 HH:mm
   * yyyy-MM-dd
   *
   * @param time      毫秒
   * @param formatStr
   * @return
   */
  @SuppressLint("SimpleDateFormat")
  public static String getDateFormatStr(long time, String formatStr) {
    Date d = new Date(time);
    return getDateFormatStrWithDate(d, formatStr);
  }

  public static String getDateFormatStrWithDate(Date date, String formatStr) {
    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    return sdf.format(date);
  }

  public static String getCurrDateFormatStr(String formatStr) {
    Date d = new Date();
    return getDateFormatStrWithDate(d, formatStr);
  }

  public static String getDateString(int i, int i1, int i2) {

    String month = i1 < 10 ? "0"+i1 : ""+i1;
    String day = i2 < 10 ? "0"+i2 : ""+i2;

    return i + "-" + month + "-" + day;
  }


  /**
   * SHA加密
   *
   * @param strSrc
   *            明文
   * @return 加密之后的密文
   */
  public static String shaEncrypt(String strSrc) {
    MessageDigest md = null;
    String strDes = null;
    byte[] bt = strSrc.getBytes();
    try {
      md = MessageDigest.getInstance("SHA-1");// 将此换成SHA-1、SHA-512、SHA-384等参数
      md.update(bt);
      strDes = bytes2Hex(md.digest()); // to HexString
    } catch (NoSuchAlgorithmException e) {
      return null;
    }
    return strDes;
  }

  /**
   * byte数组转换为16进制字符串
   *
   * @param bts
   *            数据源
   * @return 16进制字符串
   */
  public static String bytes2Hex(byte[] bts) {
    String des = "";
    String tmp = null;
    for (int i = 0; i < bts.length; i++) {
      tmp = (Integer.toHexString(bts[i] & 0xFF));
      if (tmp.length() == 1) {
        des += "0";
      }
      des += tmp;
    }
    return des;
  }

  public static String getShowPhone(String userPhone) {
    userPhone = userPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    return userPhone;
  }



  public static String hideBankNo(String originalNo) {
    return String.format("%s*** **** **** %s",
        originalNo.substring(0, 1),
        originalNo.substring(originalNo.length() - 4, originalNo.length()));
  }

  public static String hidePhoneNo(String originalNo) {
    return String.format("%s****%s",
        originalNo.substring(0, 3),
        originalNo.substring(originalNo.length() - 4, originalNo.length()));
  }

  public static String hideIdCardNo(String originalNo) {
    return String.format("****************%s",
        originalNo.substring(originalNo.length() - 4, originalNo.length()));
  }

  /**
   * 字符串转换为16进制字符串
   *
   * @param s
   * @return
   */
  public static String stringToHexString(String s) {
    String str = "";
    for (int i = 0; i < s.length(); i++) {
      int ch = (int) s.charAt(i);
      String s4 = Integer.toHexString(ch);
      str = str + s4;
    }
    return str;
  }

  /**
   * 16进制字符串转换为字符串
   *
   * @param s
   * @return
   */
  public static String hexStringToString(String s) {
    if (s == null || s.equals("")) {
      return null;
    }
    s = s.replace(" ", "");
    byte[] baKeyword = new byte[s.length() / 2];
    for (int i = 0; i < baKeyword.length; i++) {
      try {
        baKeyword[i] = (byte) (0xff & Integer.parseInt(
            s.substring(i * 2, i * 2 + 2), 16));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    try {
      s = new String(baKeyword, "gbk");
      new String();
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return s;
  }

}
