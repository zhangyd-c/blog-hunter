package me.zhyd.hunter.config;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.hunter.exception.HunterException;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/26 15:51
 * @since 1.8
 */
public class HunterConfigTemplate {

    public static JSONObject configTemplate;

    static {
        HunterConfigTemplate configTemplate = new HunterConfigTemplate();
        configTemplate.init();
    }

    public static JSONObject getConfig(String platform) {
        if (configTemplate.containsKey(platform)) {
            return configTemplate.getJSONObject(platform);
        }
        throw new HunterException("[hunter] 暂不支持该平台[" + platform + "]");
    }

    private void init() {
        String configFileName = "HunterConfig.json";
        URL url = this.getClass().getClassLoader().getResource(configFileName);
        if (null == url) {
            throw new HunterException("[hunter] 请检查`src/main/resources`下是否存在" + configFileName);
        }
        File configFile = new File(url.getPath());
        configTemplate = JSONObject.parseObject(FileReader.create(configFile, Charset.forName("UTF-8")).readString());
    }

}
