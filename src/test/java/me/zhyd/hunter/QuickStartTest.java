package me.zhyd.hunter;

import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.processor.BlogHunterProcessor;
import me.zhyd.hunter.processor.HunterProcessor;
import me.zhyd.hunter.util.PlatformUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 快速开始-测试工具使用方法
 */
public class QuickStartTest {

    private void single(String url) {
        System.out.println(url + " | " + PlatformUtil.getDomain(url) + " | " + PlatformUtil.getHost(url));
        HunterProcessor hunter = new BlogHunterProcessor(url, true);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据: " + url);
        } else {
            this.check(list);
        }
    }

    private void check(CopyOnWriteArrayList<VirtualArticle> list) {
        for (VirtualArticle virtualArticle : list) {
            if (StringUtils.isEmpty(virtualArticle.getContent())) {
                System.err.println(">> 内容为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getAuthor())) {
                System.err.println(">> 作者为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getSource())) {
                System.err.println(">> 源站为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getDescription())) {
                System.err.println(">> Description为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getKeywords())) {
                System.err.println(">> Keywords内容为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getTitle())) {
                System.err.println(">> 标题为空");
            }
            if (null == virtualArticle.getReleaseDate()) {
                System.err.println(">> 发布日期为空");
            }
            if (CollectionUtils.isEmpty(virtualArticle.getTags())) {
                System.err.println(">> 标签为空");
            }
        }
    }

    /**
     * 测试抓取单篇文章
     */
    @Test
    public void singleTest() {
        this.single("https://www.imooc.com/article/259921");
        this.single("https://blog.csdn.net/u011197448/article/details/83901306");
        this.single("https://843977358.iteye.com/blog/2317810");
        this.single("https://www.cnblogs.com/zhangyadong/p/oneblog.html");
        this.single("https://juejin.im/post/5c75d34851882564965edb23");
        this.single("https://www.v2ex.com/t/519648");
    }
}
