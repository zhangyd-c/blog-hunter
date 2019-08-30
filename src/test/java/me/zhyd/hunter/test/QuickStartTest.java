package me.zhyd.hunter.test;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.config.HunterConfigContext;
import me.zhyd.hunter.config.platform.Platform;
import me.zhyd.hunter.consts.HunterConsts;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.enums.ExitWayEnum;
import me.zhyd.hunter.processor.BlogHunterProcessor;
import me.zhyd.hunter.processor.HunterProcessor;
import me.zhyd.hunter.util.PlatformUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 快速开始-测试工具使用方法
 */
@Slf4j
public class QuickStartTest {

    /**
     * 抓取单个文章
     *
     * @param url          文件地址
     * @param convertImage 是否转存图片，当选择true时会在结果中返回该文中的所有图片链接
     */
    private void single(String url, boolean convertImage) {
        log.info(HunterConsts.LOG_PREFIX + url + " | " + PlatformUtil.getDomain(url) + " | " + PlatformUtil.getHost(url));
        HunterProcessor hunter = new BlogHunterProcessor(url, convertImage);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            log.info("没获取到数据: {}", url);
        } else {
            this.check(list);
        }
    }

    private void check(CopyOnWriteArrayList<VirtualArticle> list) {
        for (VirtualArticle virtualArticle : list) {
            log.info(HunterConsts.LOG_PREFIX + JSONArray.toJSONString(virtualArticle.getImageLinks()));
            if (StringUtils.isEmpty(virtualArticle.getContent())) {
                log.error(HunterConsts.LOG_PREFIX + "内容为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getAuthor())) {
                log.error(HunterConsts.LOG_PREFIX + "作者为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getSource())) {
                log.error(HunterConsts.LOG_PREFIX + "源站为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getDescription())) {
                log.error(HunterConsts.LOG_PREFIX + "Description为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getKeywords())) {
                log.error(HunterConsts.LOG_PREFIX + "Keywords内容为空");
            }
            if (StringUtils.isEmpty(virtualArticle.getTitle())) {
                log.error(HunterConsts.LOG_PREFIX + "标题为空");
            }
            if (null == virtualArticle.getReleaseDate()) {
                log.error(HunterConsts.LOG_PREFIX + "发布日期为空");
            }
            if (CollectionUtils.isEmpty(virtualArticle.getTags())) {
                log.error(HunterConsts.LOG_PREFIX + "标签为空");
            }
        }
    }

    /**
     * 测试抓取单篇文章
     */
    @Test
    public void singleTest() {
        this.single("https://www.imooc.com/article/259921", true);
        this.single("https://blog.csdn.net/u011197448/article/details/83901306", true);
        this.single("https://843977358.iteye.com/blog/2317810", true);
        this.single("https://www.cnblogs.com/zhangyadong/p/oneblog.html", true);
        this.single("https://juejin.im/post/5c75d34851882564965edb23", true);
        this.single("https://www.v2ex.com/t/519648", true);
        this.single("https://my.oschina.net/u/4007037/blog/3075219", true);
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
        System.out.println("程序开始执行：" + new Date());
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        System.out.println("程序执行完毕：" + new Date());
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
     * 测试抓取oschina的文章列表
     */
    @Test
    public void oschinaTest() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.OSCHINA);
        config.setUid("haitaohu")
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
     * 测试抓取oschina的文章列表，自定义抓取规则
     */
    @Test
    public void oschinaTest2() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.V2EX);
        config.setEntryUrls("https://my.oschina.net/haitaohu")
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
    public void other() {
        HunterConfig config = HunterConfigContext.getHunterConfig(Platform.IMOOC);
        // set会重置，add会追加
        config.setEntryUrls("https://www.imooc.com/u/1175248/articles")
                .addEntryUrl("https://www.imooc.com/u/4321686/articles")
                // 设置程序退出的方式
                .setExitWay(ExitWayEnum.URL_COUNT)
                // 设定抓取120秒， 如果所有文章都被抓取过了，则会提前停止
                .setCount(20)
                // 每次抓取间隔的时间
                .setSleepTime(100)
                // 失败重试次数
                .setRetryTimes(3)
                // 针对抓取失败的链接 循环重试次数
                .setCycleRetryTimes(3)
                // 开启的线程数
                .setThreadCount(5)
                // 开启图片转存
                .setConvertImg(true);
        HunterProcessor hunter = new BlogHunterProcessor(config);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (null == list || list.isEmpty()) {
            System.out.println("没获取到数据");
        } else {
            this.check(list);
        }
    }
}
