package me.zhyd.hunter.config.platform;

import me.zhyd.hunter.Hunter;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.exception.HunterException;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/27 21:29
 * @since 1.8
 */
public class InfoqPlatform extends BasePlatform {
    public InfoqPlatform() {
        super(Platform.INFOQ.getPlatform());
    }

    @Override
    public HunterConfig process(String url) {
        throw new HunterException("暂时不支持该平台：" + url);
    }
}
