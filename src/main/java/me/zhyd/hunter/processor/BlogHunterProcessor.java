package me.zhyd.hunter.processor;

import me.zhyd.hunter.Hunter;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.scheduler.BlockingQueueScheduler;
import me.zhyd.hunter.util.ResponseWriterUtil;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 爬虫入口
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2018/7/23 10:38
 */
public class BlogHunterProcessor extends HunterProcessor {

    private BlogHunterProcessor() {
        super();
    }

    public BlogHunterProcessor(String url, boolean convertImage) {
        super(url, convertImage);
    }

    public BlogHunterProcessor(HunterConfig config) {
        super(config);
    }

    public BlogHunterProcessor(HunterConfig config, String uuid) {
        super(config, uuid);
    }

    /**
     * @param config Hunter Config
     * @param writer
     * @param uuid
     */
    public BlogHunterProcessor(HunterConfig config, ResponseWriterUtil writer, String uuid) {
        super(config, writer, uuid);
    }

    /**
     * 运行爬虫并返回结果
     *
     * @return
     */
    @Override
    public CopyOnWriteArrayList<VirtualArticle> execute() {
        List<String> errors = this.validateModel(config);
        if (CollectionUtils.isNotEmpty(errors)) {
            writer.print("[hunter] 校验不通过！请依据下方提示，检查输入参数是否正确......");
            for (String error : errors) {
                writer.print(">> " + error);
            }
            return null;
        }

        CopyOnWriteArrayList<VirtualArticle> virtualArticles = new CopyOnWriteArrayList<>();
        Hunter spider = Hunter.create(this, config, uuid);

        spider.addUrl(config.getEntryUrls())
                .setScheduler(new BlockingQueueScheduler(config))
                .addPipeline((resultItems, task) -> this.process(resultItems, virtualArticles, spider))
                .thread(config.getThreadCount());

        //设置抓取代理IP
        if (!CollectionUtils.isEmpty(config.getProxyList())) {
            HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
            SimpleProxyProvider provider = SimpleProxyProvider.from(config.getProxyList().toArray(new Proxy[0]));
            httpClientDownloader.setProxyProvider(provider);
            spider.setDownloader(httpClientDownloader);
        }
        // 测试代理
        /*HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        SimpleProxyProvider provider = SimpleProxyProvider.from(
                new Proxy("61.135.217.7", 80)
        );
        httpClientDownloader.setProxyProvider(provider);
        spider.setDownloader(httpClientDownloader);*/

        // 启动爬虫
        spider.run();
        return virtualArticles;
    }


}
