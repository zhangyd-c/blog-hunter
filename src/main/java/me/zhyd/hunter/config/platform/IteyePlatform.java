package me.zhyd.hunter.config.platform;

import me.zhyd.hunter.config.HunterConfig;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/27 13:27
 * @since 1.8
 */
public class IteyePlatform extends BasePlatform {

    public IteyePlatform() {
        super(Platform.ITEYE.getPlatform());
    }

    @Override
    public HunterConfig process(String url) {
        return this.get(url);
    }
}
