package me.zhyd.hunter.test;

import me.zhyd.hunter.util.CommonUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/3/11 9:56
 * @since 1.8
 */
public class CommonUtilTest {

    @Test
    public void formatHtmlTest() {
        List<String> htmls = Arrays.asList(
                "<a href=\"/\" class=\"logo\" data-v-0e3b8d69><img src=\"https://b-gold-cdn.xitu.io/v3/static/img/logo.a7995ad.svg\" alt=\"掘金\" title=\"掘金\" class=\"logo-img\" data-v-0e3b8d69></a>",
                "<p><img class=\"lazyload\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC\" data-original=\"//img.mukewang.com/5be4017d0001393a06050391.png\" alt=\"图片描述\"></p> ",
                "<p><img src=\"https://img-blog.csdnimg.cn/20181109174256782.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTExOTc0NDg=,size_16,color_FFFFFF,t_70\" alt=\"freemarker语法大全\"></p> "
        );

        for (String html : htmls) {
            System.out.println(html = CommonUtil.formatHtml(html));
            System.out.println(CommonUtil.getAllImageLink(html));
            System.out.println();
        }
    }
}
