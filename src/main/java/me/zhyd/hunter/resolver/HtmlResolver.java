package me.zhyd.hunter.resolver;

import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.config.HunterResolver;
import me.zhyd.hunter.config.HunterResolverConfig;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.RegexSelector;

import java.util.Arrays;
import java.util.Map;

/**
 * 解析处理普通的Html网页
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
public class HtmlResolver implements Resolver {

    @Override
    public void process(Page page, HunterConfig model) {
        Html pageHtml = page.getHtml();
        String title = StringUtils.trim(pageHtml.xpath(model.getTitleRegex()).get());
        String source = page.getRequest().getUrl();
        if (model.isSingle() || (!StringUtils.isEmpty(title) && (!"null".equals(title) && !model.getEntryUrls().contains(source)))) {
            page.putField("title", title);
            page.putField("source", source);
            this.put(page, pageHtml, "releaseDate", model.getReleaseDateRegex(), model);
            this.put(page, pageHtml, "author", model.getAuthorRegex(), model);
            this.put(page, pageHtml, "content", model.getContentRegex(), model);
            this.put(page, pageHtml, "tags", model.getTagRegex(), model);
            this.put(page, pageHtml, "description", model.getDescriptionRegex(), model);
            this.put(page, pageHtml, "keywords", model.getKeywordsRegex(), model);
        }
        if (!model.isSingle()) {
            if (StringUtils.isNotEmpty(model.getTargetLinksRegex())) {
                page.addTargetRequests(page.getHtml().links().regex(model.getTargetLinksRegex()).all());
            }
        }
    }

    private void put(Page page, Html pageHtml, String key, String regex, HunterConfig model) {
        if (StringUtils.isNotEmpty(regex)) {
            HunterResolverConfig resolverConfig = model.getResolver();
            Map<String, HunterResolver> resolverMap = resolverConfig.toMap();
            HunterResolver resolver = null;
            if (resolverMap.containsKey(key)) {
                resolver = resolverMap.get(key);
            }
            Object res = null;
            if (null != resolver && "regex".equals(resolver.getType())) {
                String text = new RegexSelector(regex).select(pageHtml.get());
                if (Arrays.asList("java.lang.Long", "java.lang.Integer", "java.lang.Float", "java.lang.Double").contains(resolver.getClazz())) {
                    Map<String, Object> operatorMap = resolver.getOperatorMap();
                    if (operatorMap == null || operatorMap.isEmpty()) {
                        res = text;
                    } else {
                        String operator = String.valueOf(operatorMap.get("operator"));
                        if (!StringUtils.isEmpty(operator)) {
                            long num = Long.parseLong(String.valueOf(operatorMap.get("num")));
                            switch (operator) {
                                case "+":
                                    res = Long.parseLong(text) + num;
                                    break;
                                case "-":
                                    res = Long.parseLong(text) - num;
                                    break;
                                case "*":
                                    res = Long.parseLong(text) * num;
                                    break;
                                case "/":
                                    res = Long.parseLong(text) / num;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            } else {
                if ("tags".equals(key)) {
                    res = pageHtml.xpath(regex).all();
                } else {
                    res = StringUtils.trim(pageHtml.xpath(regex).get());
                }
            }
            page.putField(key, res);
        }
    }
}
