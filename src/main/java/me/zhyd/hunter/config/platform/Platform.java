package me.zhyd.hunter.config.platform;

import me.zhyd.hunter.util.PlatformUtil;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/27 13:22
 * @since 1.8
 */
public enum Platform {
    CNBLOGS("cnblogs", "cnblogs.com", CnblogsPlatform.class),
    CSDN("csdn", "csdn.net", CsdnPlatform.class),
    IMOOC("imooc", "imooc.com", ImoocPlatform.class),
    ITEYE("iteye", "iteye.com", IteyePlatform.class),
    JUEJIN("juejin", "juejin.im", JuejinPlatform.class),
    V2EX("v2ex", "v2ex.com", V2exPlatform.class),
    INFOQ("infoq", "infoq.cn", InfoqPlatform.class),
    ;

    private String platform;
    private String host;
    private Class clazz;

    Platform(String platform, String host, Class clazz) {
        this.platform = platform;
        this.host = host;
        this.clazz = clazz;
    }

    public static Platform getPlatformByUrl(String url) {
        if (null == url) {
            return null;
        }
        String host = PlatformUtil.getHost(url);
        if (host == null) {
            return null;
        }
        for (Platform value : Platform.values()) {
            if (host.contains(value.getHost())) {
                return value;
            }
        }
        return null;
    }

    public String getPlatform() {
        return platform;
    }

    public String getHost() {
        return host;
    }

    public Class getClazz() {
        return clazz;
    }

}
