package me.zhyd.hunter.resolver;

import me.zhyd.hunter.config.HunterConfig;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * 解析处理Ajax渲染的页面(待完善)
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
public class JsonResolver implements Resolver {

    @Override
    public void process(Page page, HunterConfig model) {
        String rawText = page.getRawText();
        String title = new JsonPathSelector(model.getTitleRegex()).select(rawText);
        if (!StringUtils.isEmpty(title) && !"null".equals(title)) {
            page.putField("title", title);
            page.putField("releaseDate", new JsonPathSelector(model.getReleaseDateRegex()).select(rawText));
            page.putField("author", new JsonPathSelector(model.getAuthorRegex()).select(rawText));
            page.putField("content", new JsonPathSelector(model.getContentRegex()).select(rawText));
            page.putField("source", page.getRequest().getUrl());
        }
        page.addTargetRequests(page.getHtml().links().regex(model.getTargetLinksRegex()).all());
    }
}
