package me.zhyd.hunter.util;

import me.zhyd.hunter.config.platform.InnerPlatform;
import me.zhyd.hunter.config.platform.Platform;
import me.zhyd.hunter.exception.HunterException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
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

    public static InnerPlatform getPlarform(String url) {
        Platform platform = Platform.getPlatformByUrl(url);
        if (null == platform) {
            throw new HunterException("暂时不支持该平台：" + url);
        }
        return getPlarform(platform);
    }

    public static InnerPlatform getPlarform(Platform platform) {
        if (null == platform) {
            throw new HunterException("无效的博客平台");
        }
        Class clazz = platform.getClazz();
        try {
            return (InnerPlatform) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new HunterException(String.format("无法获取InnerPlatform实例，url: %s", platform.getHost()), e);
        }
    }

    public static String getDomain(String url) {
        String regex = "(http|https)://(www.)?([\\w-_]+(\\.)?)+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group() : null;
    }
}
