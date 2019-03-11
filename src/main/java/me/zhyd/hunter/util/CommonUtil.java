package me.zhyd.hunter.util;

import me.zhyd.hunter.entity.ImageLink;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class CommonUtil {

    private static final Pattern PATTERN = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^'\"]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>|<img[^>]+data-original\\s*=\\s*['\"]([^'\"]+)['\"][^'\"]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>|<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");

    /**
     * 获取真实的网站介绍，最多只保留100个字符
     *
     * @param description 原博客的description
     * @param content     原博客的正文内容
     */
    public static String getRealDescription(String description, String content) {
        if (StringUtils.isNotEmpty(description)) {
            return description.replaceAll("\r\n| ", "");
        }
        if (StringUtils.isNotEmpty(content)) {
            content = Jsoup.clean(content.trim(), Whitelist.simpleText());
            return content.length() > 100 ? content.substring(0, 100) : content;
        }
        return null;
    }

    /**
     * 获取真实的无特殊标签的网站关键字
     *
     * @param keywords 原博客的keywords
     */
    public static String getRealKeywords(String keywords) {
        String keys = StringUtils.isNotEmpty(keywords) && !"null".equals(keywords) ? keywords.trim().replaceAll(" +|，", ",").replaceAll(",,", ",") : null;
        return StringUtils.isEmpty(keys) ? null : Jsoup.clean(keys, Whitelist.simpleText());
    }

    /**
     * 获取所有图片标签的src连接
     *
     * @param html 原博客内容
     */
    public static String formatHtml(String html) {
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        String lazyloadFormat = "<img src=\"%s\" title=\"%s\" alt=\"%s\">";

        Html pageHtml = getHtml(html);
        List<Selectable> imgSelectables = pageHtml.$("img").nodes();
        for (Selectable imgSelectable : imgSelectables) {
            String oldImg = imgSelectable.get();
            String newImg = String.format(lazyloadFormat, getRealImgUrl(imgSelectable), imgSelectable.xpath("//img/@title").get(), imgSelectable.xpath("//img/@alt").get());
            html = html.replace(oldImg, newImg);
        }
        return html;
    }

    private static String getRealImgUrl(Selectable selectable) {
        String realImgUrl = selectable.xpath("//img/@data-original").get();
        if (StringUtils.isEmpty(realImgUrl)) {
            realImgUrl = selectable.xpath("//img/@data-src").get();
            if (StringUtils.isEmpty(realImgUrl)) {
                realImgUrl = selectable.xpath("//img/@src").get();
            }
        }
        if (StringUtils.isNotEmpty(realImgUrl)) {
            if (realImgUrl.contains("?")) {
                realImgUrl = realImgUrl.substring(0, realImgUrl.indexOf("?"));
            }
        }
        return realImgUrl;
    }

    /**
     * 获取所有图片标签的src连接
     *
     * @param html 原博客内容
     */
    public static Set<ImageLink> getAllImageLink(String html) {
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        Set<ImageLink> imageLinks = new HashSet<>();
        ImageLink imageLink = null;

        Html pageHtml = getHtml(html);
        List<Selectable> imgSelectables = pageHtml.$("img").nodes();
        for (Selectable imgSelectable : imgSelectables) {
            String newImgSrc = getRealImgUrl(imgSelectable);
            imageLink = new ImageLink(newImgSrc);
            imageLinks.add(imageLink);
        }
        return imageLinks;
    }

    private static Html getHtml(String html) {
        Page page = new Page();
        page.setRequest(new Request(""));
        page.setRawText(html);
        return page.getHtml();
    }
}
