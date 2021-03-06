package cn.jungmedia.android.utils;


import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    private static String ChineseMobilePattern = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";

    /**
     * 判断邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        String regex = null;
        if (email != null) {
            regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*.\\w+([-.]\\w+)*";
            flag = checkStr(regex, email);
        }
        return flag;
    }
    /**
     * 判断用户名
     * @param str
     * @return
     */
//	 public   boolean checkUserName(String username) {
//		 boolean flag=false;
//		 String regex =null;
//		 int len=username.length();
//		 if(len<6||len>18){
//			 flag=false;
//		 }else{
//		  regex = "([a-z]|[A-Z]|[0-9])+";//只判定数字或者字母
//		  flag= checkStr(regex,username);
//		 }
//		 return flag;
//	}

    /**
     * 判断手机号码 可为null 并且满足号码格式
     *
     * @param mobile
     * @return
     */
    public static boolean checkTelPhone(String mobile) {
        boolean flag = false;
        if (mobile != null && !"".equals(mobile)) {
            flag = checkStr(ChineseMobilePattern, mobile);//判定电话号码
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        boolean b = false;
        if (!TextUtils.isEmpty(str)) {
            Pattern p1 = null, p2 = null;
            Matcher m = null;
            p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
            p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
            if (str.length() > 9) {
                m = p1.matcher(str);
                b = m.matches();
            } else {
                m = p2.matcher(str);
                b = m.matches();
            }
        }
        return b;
    }

    /**
     * 判断手机号码非空并且限制为11位
     *
     * @param mobile
     * @return
     */
    public static boolean checkTelPhone2(String mobile) {
        boolean flag = false;
        if (mobile != null && !"".equals(mobile)) {
            flag = checkStr(ChineseMobilePattern, mobile);//判定电话号码
        }
        return flag;
    }

    /**
     * 判断密码    字符串 字母或者数字
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        boolean flag = false;
        String regex = null;
        int len = password.length();
        if (len < 6 || len > 24) {
            flag = false;
        } else {
            regex = "([a-z]|[A-Z]|[0-9]|[_*])+";//判定数字或者字母或者下划线
            flag = checkStr(regex, password);
        }
        return flag;
    }

    /**
     * 判断执业医师证书编码 18位数字 可为null
     *
     * @param doctorLicence
     * @return
     */
    public static boolean checkDoctorLicenceStr(String doctorLicence) {
        boolean flag = false;
        String regex = null;
        if (doctorLicence != null && !"".equals(doctorLicence)) {
            regex = "^\\d{15}$";//判定执业医师证书编码
            flag = checkStr(regex, doctorLicence);
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 正则检查字符串
     *
     * @param regex
     * @param input
     * @return
     */
    private static boolean checkStr(String regex, String input) {

        boolean flag = false;
        if (regex != null) {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(input);
            flag = m.matches();
        }
        return flag;
    }

    /**
     * 判定数字或者字母或者中文
     *
     * @param str
     * @return
     */
    public static boolean checkAll(String str) {
        boolean flag = false;
        String regex = null;
        regex = "([a-z]|[A-Z]|[0-9]|[\u4e00-\u9fa5])+";//判定数字或者字母或者中文
        flag = checkStr(regex, str);
        return flag;
    }

    /**
     * 判断用户名
     *
     * @param str
     * @return
     */
    public static boolean checkContainChinese(String str) {
        boolean flag = false;
        String regex = null;
        regex = "([\u4e00-\u9fa5])+";//判定中文
        flag = checkStr(regex, str);
        return flag;
    }

    /**
     * 判断真实姓名 姓名为汉字，并且至少两位
     *
     * @param str
     * @return
     */
    public static boolean checkTrueName(String str) {
        boolean flag = false;
        String regex = null;
        regex = "([\u4e00-\u9fa5])+";//判定中文
        flag = checkStr(regex, str);
        if (flag) {
            return str.length() > 1 ? true : false;
        } else
            return flag;
    }

    /**
     * 过滤标点符号
     *
     * @param str
     * @return
     */
    public static String filterPunctuation(String str, String replaceStr) {
        if (str != null)
            str = str.replaceAll("\\pP|\\pS|\\s", replaceStr);
        return str;
    }

    /**
     * 过滤单个重复字符
     *
     * @param str
     * @return
     */
    public static String filterRepitChar(String str) {
        if (str != null) {
            str = str.replaceAll("(?s)(.)(?=.*\\1)", "");
        }
        return str;

    }

//	//查找以Java开头,任意结尾的字符串
//	  Pattern pattern = Pattern.compile("^Java.*");
//	  Matcher matcher = pattern.matcher("Java不是人");
//	  boolean b= matcher.matches();
//	  //当条件满足时，将返回true，否则返回false
//	  System.out.println(b);
//
//
//	  ◆以多条件分割字符串时
//	  Pattern pattern = Pattern.compile("[, |]+");
//	  String[] strs = pattern.split("Java Hello World Java,Hello,,World|Sun");
//	  for (int i=0;i<strs.length;i++) {
//	      System.out.println(strs[i]);
//	  }
//
//	  ◆文字替换（首次出现字符）
//	  Pattern pattern = Pattern.compile("正则表达式");
//	  Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World");
//	  //替换第一个符合正则的数据
//	  System.out.println(matcher.replaceFirst("Java"));
//
//	  ◆文字替换（全部）
//	  Pattern pattern = Pattern.compile("正则表达式");
//	  Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World");
//	  //替换第一个符合正则的数据
//	  System.out.println(matcher.replaceAll("Java"));
//
//
//	  ◆文字替换（置换字符）
//	  Pattern pattern = Pattern.compile("正则表达式");
//	  Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World ");
//	  StringBuffer sbr = new StringBuffer();
//	  while (matcher.find()) {
//	      matcher.appendReplacement(sbr, "Java");
//	  }
//	  matcher.appendTail(sbr);
//	  System.out.println(sbr.toString());
//
//	  ◆验证是否为邮箱地址
//
//	  String str="ceponline@yahoo.com.cn";
//	  Pattern pattern = Pattern.compile("[//w//.//-]+@([//w//-]+//.)+[//w//-]+",Pattern.CASE_INSENSITIVE);
//	  Matcher matcher = pattern.matcher(str);
//	  System.out.println(matcher.matches());
//
//	  ◆去除html标记
//	  Pattern pattern = Pattern.compile("<.+?>", Pattern.DOTALL);
//	  Matcher matcher = pattern.matcher("<a href=/"index.html/">主页</a>");
//	  String string = matcher.replaceAll("");
//	  System.out.println(string);
//
//	  ◆查找html中对应条件字符串
//	  Pattern pattern = Pattern.compile("href=/"(.+?)/"");
//	  Matcher matcher = pattern.matcher("<a href=/"index.html/">主页</a>");
//	  if(matcher.find())
//	  System.out.println(matcher.group(1));
//	  }
//
//	  /**
//	   * 截取http://地址
//	   *
//	   */
//	  //截取url
//	  Pattern pattern = Pattern.compile("(http://|https://){1}[//w//.//-/:]+");
//	  Matcher matcher = pattern.matcher("dsdsds<http://dsds//gfgffdfd>fdf");
//	  StringBuffer buffer = new StringBuffer();
//	  while(matcher.find()){
//	      buffer.append(matcher.group());
//	      buffer.append("/r/n");
//	  System.out.println(buffer.toString());
//	  }
//
//	/**
//	 * 替换指定{}中文字
//	 */
//	  String str = "Java目前的发展史是由{0}年-{1}年";
//	  String[][] object={new String[]{"//{0//}","1995"},new String[]{"//{1//}","2007"}};
//	  System.out.println(replace(str,object));
//
//	  public static String replace(final String sourceString,Object[] object) {
//	              String temp=sourceString;
//	              for(int i=0;i<object.length;i++){
//	                        String[] result=(String[])object[i];
//	                 Pattern    pattern = Pattern.compile(result[0]);
//	                 Matcher matcher = pattern.matcher(temp);
//	                 temp=matcher.replaceAll(result[1]);
//	              }
//	              return temp;
//	  }


    /**
     * 将img标签中的src进行二次包装
     *
     * @param content     内容
     * @param replaceHttp 需要在src中加入的域名
     * @return
     */
    public static String repairContent(String content, String replaceHttp) {

        Pattern p = Pattern.compile("<img\\s*([^>]*)\\s*([^>]*)>");
        Matcher m = p.matcher(content);
        while (m.find()) {
            String imgLable = m.group(0);
            String newImgLable = imgLable.replaceAll("<img", "<img width=100% height=100% ");

            String patternStr = "<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";
            Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(newImgLable);
            while (matcher.find()) {
                String src = matcher.group(2);
                Log.d("PatternUtil", "pattern string:" + src);
                String replaceSrc = "";
                if (src.lastIndexOf(".") > 0) {
                    replaceSrc = src.substring(0, src.lastIndexOf(".")) + src.substring(src.lastIndexOf("."));
                }
                if (!src.startsWith("http://") && !src.startsWith("https://")) {
                    replaceSrc = replaceHttp + replaceSrc;
                }
                newImgLable = newImgLable.replaceAll(src, replaceSrc);
            }
            content = content.replaceAll(imgLable, newImgLable);
        }

        return content;
    }
}
