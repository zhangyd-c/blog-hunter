package me.zhyd.hunter.enums;

import cn.hutool.core.util.RandomUtil;

/**
 * 更多UA请参考：http://www.useragentstring.com/pages/useragentstring.php
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public enum UserAgentEnum {

    PC("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36"),
    PC_WIN10("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3573.0 Safari/537.36"),
    PC_FIREFOX64_WIN("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:64.0) Gecko/20100101 Firefox/64.0"),
    PC_FIREFOX64_LINUX("Mozilla/5.0 (X11; Linux i686; rv:64.0) Gecko/20100101 Firefox/64.0");

    private String ua;

    UserAgentEnum(String ua) {
        this.ua = ua;
    }

    public static String getRandomUa() {
        UserAgentEnum[] uas = UserAgentEnum.values();
        return uas[RandomUtil.randomInt(0, uas.length)].getUa();
    }

    public String getUa() {
        return ua;
    }
}
