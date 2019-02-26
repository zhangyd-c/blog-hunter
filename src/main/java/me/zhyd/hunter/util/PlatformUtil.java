package me.zhyd.hunter.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/1/21 13:28
 * @since 1.8
 */
public class PlatformUtil {

    public static String getHost(String url) {
        String res = getDomain(url);
        if (null == res) {
            return null;
        }
        return res.replace("https://", "").replace("http://", "");
    }

    public static boolean isImooc(String url) {
        String res = getDomain(url);
        if (null == res) {
            return false;
        }
        return res.contains("imooc.com");
    }

    public static boolean isCsdn(String url) {
        String res = getDomain(url);
        if (null == res) {
            return false;
        }
        return res.contains("csdn.net");
    }

    public static boolean isIteye(String url) {
        String res = getDomain(url);
        if (null == res) {
            return false;
        }
        return res.contains("iteye.com");
    }

    public static boolean isCnblogs(String url) {
        String res = getDomain(url);
        if (null == res) {
            return false;
        }
        return res.contains("cnblogs.com");
    }

    public static String getDomain(String url) {
        String regex = "(http|https)://(www.)?([\\w-_]+(\\.)?)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group() : null;
    }
}
