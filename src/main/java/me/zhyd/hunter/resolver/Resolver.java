package me.zhyd.hunter.resolver;

import me.zhyd.hunter.config.HunterConfig;
import us.codecraft.webmagic.Page;

/**
 * 页面解析器
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
public interface Resolver {
    void process(Page page, HunterConfig model);
}
