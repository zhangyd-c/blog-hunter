package me.zhyd.hunter.config;

import lombok.Data;
import me.zhyd.hunter.entity.Cookie;
import me.zhyd.hunter.enums.ExitWayEnum;
import me.zhyd.hunter.enums.UserAgentEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.proxy.Proxy;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
@Data
public class HunterConfig {
    /**
     * 是否抓取的单个文章
     */
    public boolean single;
    @NotNull(message = "必须指定标题抓取规则(xpath)")
    private String titleRegex;
    @NotNull(message = "必须指定内容抓取规则(xpath)")
    private String contentRegex;
    @NotNull(message = "必须指定发布日期抓取规则(xpath)")
    private String releaseDateRegex;
    @NotNull(message = "必须指定作者抓取规则(xpath)")
    private String authorRegex;
    @NotNull(message = "必须指定待抓取的url抓取规则(regex)")
    private String targetLinksRegex;
    private String tagRegex;
    private String keywordsRegex = "//meta[@name=keywords]/@content";
    private String descriptionRegex = "//meta[@name=description]/@content";
    @NotNull(message = "必须指定网站根域名")
    private String domain;
    private String charset = "utf8";
    /**
     * 每次爬取页面时的等待时间
     */
    @Max(value = 10000, message = "线程等待时间不可大于10000毫秒")
    @Min(value = 100, message = "线程等待时间不可小于100毫秒")
    private int sleepTime = 1000;
    /**
     * 抓取失败时重试的次数
     */
    @Max(value = 5, message = "抓取失败时最多只能重试5次")
    @Min(value = 1, message = "抓取失败时最少只能重试1次")
    private int retryTimes = 2;
    /**
     * 抓取失败时重试的次数用完后依然未抓取成功时，循环重试
     */
    @Max(value = 5, message = "最多支持5次失败循环重试")
    @Min(value = 1, message = "最少支持1次失败循环重试")
    private int cycleRetryTimes = 2;
    /**
     * 线程个数
     */
    @Max(value = 10, message = "最多只能开启10个线程（请谨慎使用）")
    @Min(value = 1, message = "至少要开启1个线程")
    private int threadCount = 1;
    /**
     * 抓取入口地址
     */
//    @NotNull(message = "必须指定待抓取的网址")
    private List<String> entryUrls;
    /**
     * 退出方式{DURATION:爬虫持续的时间,URL_COUNT:抓取到的url数量}
     */
    private String exitWay = ExitWayEnum.URL_COUNT.toString();
    /**
     * 对应退出方式，当exitWay = URL_COUNT时，该值表示url数量，当exitWay = DURATION时，该值表示爬虫持续的时间
     */
    private int count;
    private List<Cookie> cookies = new ArrayList<>();
    private Map<String, String> headers = new HashMap<>();
    private String ua = UserAgentEnum.getRandomUa();
    private String uid;
    private boolean onlyThisAuthor;
    /**
     * 保留字段，针对ajax渲染的页面，暂时不支持
     */
    private Boolean ajaxRequest = false;
    /**
     * 是否转存图片，当选择true时会自动过滤原文中的img链接，调用端可选择将图片下载后替换掉原来的图片
     */
    private boolean convertImg = false;
    private List<Proxy> proxyList = new ArrayList<>();
    /**
     * 是否开启自动代理，开启时将会自动获取代理ip
     */
    private ProxyType proxyType = ProxyType.CUSTOM;

    public HunterConfig() {
    }

    public HunterConfig setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public HunterConfig setOnlyThisAuthor(boolean onlyThisAuthor) {
        this.onlyThisAuthor = onlyThisAuthor;
        return this;
    }

    public HunterConfig setTitleRegex(String titleRegex) {
        this.titleRegex = titleRegex;
        return this;
    }

    public HunterConfig setContentRegex(String contentRegex) {
        this.contentRegex = contentRegex;
        return this;
    }

    public HunterConfig setReleaseDateRegex(String releaseDateRegex) {
        this.releaseDateRegex = releaseDateRegex;
        return this;
    }

    public HunterConfig setAuthorRegex(String authorRegex) {
        this.authorRegex = authorRegex;
        return this;
    }

    public HunterConfig setTargetLinksRegex(String targetLinksRegex) {
        this.targetLinksRegex = targetLinksRegex;
        return this;
    }

    public HunterConfig setTagRegex(String tagRegex) {
        this.tagRegex = tagRegex;
        return this;
    }

    public HunterConfig setKeywordsRegex(String keywordsRegex) {
        this.keywordsRegex = keywordsRegex;
        return this;
    }

    public HunterConfig setDescriptionRegex(String descriptionRegex) {
        this.descriptionRegex = descriptionRegex;
        return this;
    }

    public HunterConfig setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public HunterConfig setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public HunterConfig setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    public HunterConfig setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
        return this;
    }

    public HunterConfig setCycleRetryTimes(int cycleRetryTimes) {
        this.cycleRetryTimes = cycleRetryTimes;
        return this;
    }

    public HunterConfig setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }

    public HunterConfig setEntryUrls(List<String> entryUrls) {
        this.entryUrls = entryUrls;
        return this;
    }

    public HunterConfig setEntryUrls(String entryUrls) {
        if (StringUtils.isNotEmpty(entryUrls)) {
            if (entryUrls.startsWith("[")) {
                entryUrls = entryUrls.substring(1);
            }
            if (entryUrls.endsWith("]")) {
                entryUrls = entryUrls.substring(0, entryUrls.length() - 1);
            }
            List<String> list = Arrays.asList(entryUrls.split("\r\n"));
            this.entryUrls = new LinkedList<>();
            this.entryUrls.addAll(list);
        }
        return this;
    }

    public HunterConfig addEntryUrl(String url) {
        if (CollectionUtils.isEmpty(this.entryUrls)) {
            this.entryUrls = new LinkedList<>();
        }
        this.entryUrls.add(url);
        return this;
    }

    public HunterConfig setExitWay(String exitWay) {
        this.exitWay = exitWay;
        return this;
    }

    public HunterConfig setExitWay(ExitWayEnum exitWay) {
        this.exitWay = exitWay.toString();
        this.count = exitWay.getDefaultCount();
        return this;
    }

    public HunterConfig setCount(int count) {
        this.count = count;
        return this;
    }

    public HunterConfig setHeader(String key, String value) {
        Map<String, String> headers = this.getHeaders();
        headers.put(key, value);
        return this;
    }

    public HunterConfig setHeader(String headersStr) {
        if (StringUtils.isNotEmpty(headersStr)) {
            String[] headerArr = headersStr.split("\r\n");
            for (String s : headerArr) {
                String[] header = s.split("=");
                setHeader(header[0], header[1]);
            }
        }
        return this;
    }

    public HunterConfig setCookie(String domain, String key, String value) {
        List<Cookie> cookies = this.getCookies();
        cookies.add(new Cookie(domain, key, value));
        return this;
    }

    public HunterConfig setCookie(String cookiesStr) {
        if (StringUtils.isNotEmpty(cookiesStr)) {
            List<Cookie> cookies = this.getCookies();
            String[] cookieArr = cookiesStr.split(";");
            for (String aCookieArr : cookieArr) {
                String[] cookieNode = aCookieArr.split("=");
                if (cookieNode.length <= 1) {
                    continue;
                }
                cookies.add(new Cookie(cookieNode[0].trim(), cookieNode[1].trim()));
            }
        }
        return this;
    }

    public HunterConfig setAjaxRequest(boolean ajaxRequest) {
        this.ajaxRequest = ajaxRequest;
        return this;
    }

    private void addProxy(Proxy proxy) {
        if (this.proxyType == ProxyType.CUSTOM || null == proxy) {
            return;
        }
        proxyList.add(proxy);
    }

    public HunterConfig setProxy(String proxyStr) {
        if (this.proxyType != ProxyType.CUSTOM || proxyStr == null) {
            return this;
        }
        String[] proxyArr = proxyStr.split("\r\n");
        for (String s : proxyArr) {
            String[] proxy = s.split("|");
            if (proxy.length == 2) {
                this.addProxy(new Proxy(proxy[0], Integer.parseInt(proxy[1])));
            } else if (proxy.length == 4) {
                this.addProxy(new Proxy(proxy[0], Integer.parseInt(proxy[1]), proxy[2], proxy[3]));
            }
        }
        return this;
    }

    public HunterConfig setConvertImg(boolean convertImg) {
        this.convertImg = convertImg;
        return this;
    }

    public HunterConfig setSingle(boolean single) {
        this.single = single;
        return this;
    }

    enum ProxyType {
        /**
         * 自动获取IP代理池
         */
        AUTO,
        /**
         * 自定义
         */
        CUSTOM,
        /**
         * 禁用代理
         */
        DISABLE
    }
}
