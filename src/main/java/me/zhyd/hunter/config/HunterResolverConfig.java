package me.zhyd.hunter.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 解析器配置，针对每个字段，都可以配置单独的解析器，参考{@link HunterResolver}
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Setter
public class HunterResolverConfig {
    private HunterResolver title;
    private HunterResolver content;
    private HunterResolver releaseDate;
    private HunterResolver author;
    private HunterResolver targetLinks;
    private HunterResolver tag;
    private HunterResolver keywords;
    private HunterResolver description;

    /**
     * 将HunterResolverConfig转换为map，方便根据字段名进行操作
     *
     * @return map
     */
    public Map<String, HunterResolver> toMap() {
        Map<String, JSONObject> map = JSON.parseObject(JSON.toJSONString(this), Map.class);
        Set<Map.Entry<String, JSONObject>> entries = map.entrySet();
        Map<String, HunterResolver> res = new HashMap<>();
        HunterResolver resolver = null;
        for (Map.Entry<String, JSONObject> entry : entries) {
            if (null != entry.getValue()) {
                resolver = entry.getValue().toJavaObject(HunterResolver.class);
            }
            res.put(entry.getKey(), resolver);
        }
        return res;
    }
}
