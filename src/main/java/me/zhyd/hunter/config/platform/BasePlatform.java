package me.zhyd.hunter.config.platform;

import com.alibaba.fastjson.JSONObject;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.config.HunterConfigTemplate;
import me.zhyd.hunter.util.PlatformUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public abstract class BasePlatform implements InnerPlatform {
    String platform;

    public BasePlatform(String platform) {
        this.platform = platform;
    }

    protected final HunterConfig get(String url) {

        String host = PlatformUtil.getHost(url);
        String domain = PlatformUtil.getDomain(url);

        String platformConfig = HunterConfigTemplate.getConfig(platform);
        JSONObject platformObj = JSONObject.parseObject(platformConfig);
        String br = "\r\n", header = null;
        Set<Map.Entry<String, Object>> entries = platformObj.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if ("header".equals(entry.getKey())) {
                header = "Host=" + host + br + "Referer=" + domain;
                entry.setValue(header);
            } else if ("entryUrls".equals(entry.getKey())) {
                entry.setValue(Collections.singletonList(url));
            } else {
                if (platform.equals(Platform.ITEYE.getPlatform()) && "domain".equals(entry.getKey())) {
                    entry.setValue(host);
                }
            }
        }
        HunterConfig config = JSONObject.toJavaObject(platformObj, HunterConfig.class);
        config.setSingle(true);
        return config;
    }
}
