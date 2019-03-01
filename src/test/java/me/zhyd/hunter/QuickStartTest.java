package me.zhyd.hunter;

import lombok.extern.slf4j.Slf4j;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.config.HunterConfigContext;
import me.zhyd.hunter.config.platform.Platform;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.enums.ExitWayEnum;
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
@Slf4j
public class QuickStartTest {

    private void single(String url) {
        System.out.println(url + " | " + PlatformUtil.getDomain(url) + " | " + PlatformUtil.getHost(url));
        HunterProcessor hunter = new BlogHunterProcessor(url, true);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            log.info("没获取到数据: {}", url);
        } else {
            this.check(list);
        }
    }

    private void check(CopyOnWriteArrayList<VirtualArticle> list) {

        for (int i = 0, size = list.size(); i < size; i++) {
            VirtualArticle virtualArticle = list.get(i);
            log.info("[Hunter] " + (i + 1) + ". " + virtualArticle.getTitle() + " | " + virtualArticle.getAuthor());
            if (StringUtils.isEmpty(virtualArticle.getContent())) {
                log.error("    ERROR | 内容为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getAuthor())) {
                log.error("    ERROR | 作者为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getSource())) {
                log.error("    ERROR | 源站为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getDescription())) {
                log.error("    ERROR | Description为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getKeywords())) {
                log.error("    ERROR | Keywords内容为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getTitle())) {
                log.error("    ERROR | 标题为空");
            }
            if (null == virtualArticle.getReleaseDate()) {
                log.error("    ERROR | 发布日期为空");
            }
            if (CollectionUtils.isEmpty(virtualArticle.getTags())) {
                log.error("    ERROR | 标签为空");
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

    /**
     * 测试抓取imooc的文章列表。按照抓取的文章条数控制程序停止，并且手动指定待抓取的连接条数
     */
    @Test
    public void imoocTest() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.IMOOC);
        // 设置用户的id
        config.setUid("1175248")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.URL_COUNT)
                // 根据ExitWay设置，当ExitWay = URL_COUNT时， count表示待抓取的链接个数；当ExitWay = DURATION时， count表示爬虫运行的时间，理想状态时1s抓取一条，受实际网速影响；当ExitWay = default时，程序不做限制，抓取所有匹配到的文章，“慎用”
                // 如果不手动设置该值， 则取ExitWayEnum中默认的数量，URL_COUNT(10)，DURATION(60)
                .setCount(2);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }

    /**
     * 测试抓取csdn的文章列表。按照程序运行的时间（s）控制程序停止，并且手动指定程序运行的时间
     */
    @Test
    public void csdnTest() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.CSDN);
        // 设置用户的id
        config.setUid("u011197448")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.DURATION)
                // 根据ExitWay设置，当ExitWay = URL_COUNT时， count表示待抓取的链接个数；当ExitWay = DURATION时， count表示爬虫运行的时间，理想状态时1s抓取一条，受实际网速影响；当ExitWay = default时，程序不做限制，抓取所有匹配到的文章，“慎用”
                // 如果不手动设置该值， 则取ExitWayEnum中默认的数量，URL_COUNT(10)，DURATION(60)
                .setCount(10);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }

    /**
     * 测试抓取iteye的文章列表。按照抓取的文章条数控制程序停止，并使用默认的条数（10条）
     */
    @Test
    public void iteyeTest() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.ITEYE);
        // 设置用户的id
        config.setUid("843977358")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.URL_COUNT);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }

    /**
     * 测试抓取cnblogs的文章列表。按照程序运行的时间（s）控制程序停止，并使用默认的时间（60s）
     */
    @Test
    public void cnblogsTest() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.CNBLOGS);
        // 设置用户的id
        config.setUid("zhangyadong")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.DURATION);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }

    /**
     * 测试抓取掘金的文章列表
     */
    @Test
    public void juejinTest() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.JUEJIN);
        // 设置用户的id
        config.setUid("5b90662de51d450e8b1370f6")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.URL_COUNT)
                .setCount(5);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }

    /**
     * 测试抓取v2ex的文章列表
     */
    @Test
    public void v2exTest() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.V2EX);
        // 设置用户的id
        config.setUid("AlibabaSS")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.DURATION)
                // 设定抓取120秒， 如果所有文章都被抓取过了，则会提前停止
                .setCount(120);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }

    /**
     * 测试抓取v2ex的文章列表，自定义抓取规则
     */
    @Test
    public void v2exTest2() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.V2EX);
        config.setEntryUrls("https://www.v2ex.com/member/Evernote")
                .addEntryUrl("https://www.v2ex.com/member/ityouknow")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.DURATION)
                // 设定抓取120秒， 如果所有文章都被抓取过了，则会提前停止
                .setCount(120);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }

    /**
     * 高级使用
     */
    @Test
    public void v2exTest3() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.IMOOC);
        // set会重置，add会追加
        config.setEntryUrls("https://www.imooc.com/u/1175248/articles")
                .addEntryUrl("https://www.imooc.com/u/479481/articles")
                .addEntryUrl("https://www.imooc.com/u/6321116/articles")
                .addEntryUrl("https://www.imooc.com/u/1879927/articles")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.DURATION)
                // 设定抓取120秒， 如果所有文章都被抓取过了，则会提前停止
                .setCount(120)
                // 每次抓取间隔的时间
                .setSleepTime(100)
                // 失败重试次数
                .setRetryTimes(3)
                // 针对抓取失败的链接 循环重试次数
                .setCycleRetryTimes(3)
                // 开启的线程数
                .setThreadCount(10);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }
}
