package me.zhyd.hunter.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.hunter.config.platform.InnerPlatform;
import me.zhyd.hunter.config.platform.Platform;
import me.zhyd.hunter.util.PlatformUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class HunterConfigContext {

    /**
     * 抓取单个文章时可用；
     *
     * @param url 待抓取的文章连接
     * @return HunterConfig
     */
    public static HunterConfig getHunterConfig(String url) {
        InnerPlatform platform = PlatformUtil.getPlarform(url);
        return platform.process(url);
    }

    /**
     * 抓取单个文章时可用；
     *
     * @param platform 博客平台
     * @return HunterConfig
     */
    public static HunterConfig getHunterConfig(Platform platform) {
        String platformConfig = HunterConfigTemplate.getConfig(platform.getPlatform());
        JSONObject platformObj = JSONObject.parseObject(platformConfig);
        String br = "\r\n";
        Set<Map.Entry<String, Object>> entries = platformObj.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            if ("header".equals(entry.getKey())) {
                List<String> headers = JSONArray.parseArray(String.valueOf(entry.getValue()), String.class);
                entry.setValue(String.join(br, headers));
            }
        }
        return JSONObject.toJavaObject(platformObj, HunterConfig.class);
    }

    /**
     * 重新解析配置模板， 将用户id替换为真实的id
     *
     * @param config config
     * @return config
     */
    public static HunterConfig parseConfig(HunterConfig config) {
        if (null == config) {
            return null;
        }
        String uid = config.getUid();
        if (StringUtils.isEmpty(uid)) {
            return config;
        }
        String domain = config.getDomain();
        if (StringUtils.isNotEmpty(domain)) {
            config.setDomain(domain.replace("{uid}", uid));
        }
        String targetLinksRegex = config.getTargetLinksRegex();
        if (StringUtils.isNotEmpty(targetLinksRegex)) {
            config.setTargetLinksRegex(targetLinksRegex.replace("{uid}", uid));
        }
        List<String> entryUrls = config.getEntryUrls();
        if (CollectionUtils.isNotEmpty(entryUrls)) {
            List<String> newEntryUrls = new ArrayList<>();
            for (String entryUrl : entryUrls) {
                newEntryUrls.add(entryUrl.replace("{uid}", uid));
            }
            config.setEntryUrls(newEntryUrls);
        }
        Map<String, String> header = config.getHeaders();
        if (MapUtils.isNotEmpty(header)) {
            Set<Map.Entry<String, String>> entries = header.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                header.put(key, value.replace("{uid}", uid));
            }
        }
        return config;
    }
}
