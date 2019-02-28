package me.zhyd.hunter.util;

import me.zhyd.hunter.entity.ImageLink;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2018/9/27 17:40
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
    public static Set<ImageLink> getAllImageLink(String html) {
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        String base64Prefix = "data:image";
        Matcher m = PATTERN.matcher(html);
        Set<ImageLink> imageLinks = new HashSet<>();
        ImageLink imageLink = null;
        while (m.find()) {
            String imgUrl1 = m.group(1), imgUrl2 = m.group(2);// 如果不为空，则表示img标签格式为 <img src="xx" data-original="xx">,下同， 一般为添加了懒加载的img
            String imgUrl3 = m.group(3), imgUrl4 = m.group(4);// 如果不为空，则表示img标签格式为 <img data-original="xx" src="xx">,下同， 一般为添加了懒加载的img
            String imgUrl5 = m.group(5);// 如果不为空，则表示img标签格式为 <img src="xx">, 正常的标签
            imageLink = new ImageLink();
            if (!StringUtils.isEmpty(imgUrl1)) {
                if (imgUrl1.contains(base64Prefix)) {
                    imageLink.setSrcLink(imgUrl1);
                    imageLink.setOriginalLink(imgUrl2);
                } else {
                    imageLink.setSrcLink(imgUrl2);
                    imageLink.setOriginalLink(imgUrl1);
                }
            } else if (!StringUtils.isEmpty(imgUrl3)) {
                if (imgUrl3.contains(base64Prefix)) {
                    imageLink.setSrcLink(imgUrl3);
                    imageLink.setOriginalLink(imgUrl4);
                } else {
                    imageLink.setSrcLink(imgUrl4);
                    imageLink.setOriginalLink(imgUrl3);
                }
            } else if (!StringUtils.isEmpty(imgUrl5)) {
                imageLink.setSrcLink(imgUrl5);
                imageLink.setOriginalLink(imgUrl5);
            }
            imageLinks.add(imageLink);
        }
        return imageLinks;
    }
}
