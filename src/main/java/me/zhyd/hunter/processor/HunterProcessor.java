package me.zhyd.hunter.processor;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.hunter.Hunter;
import me.zhyd.hunter.config.HunterConfig;
import me.zhyd.hunter.config.HunterConfigContext;
import me.zhyd.hunter.config.HunterDateDeserializer;
import me.zhyd.hunter.entity.Cookie;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.resolver.HtmlResolver;
import me.zhyd.hunter.resolver.JsonResolver;
import me.zhyd.hunter.resolver.Resolver;
import me.zhyd.hunter.util.CommonUtil;
import me.zhyd.hunter.util.HunterPrintWriter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 统一对页面进行解析处理
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
@Slf4j
public abstract class HunterProcessor implements PageProcessor {
    protected HunterConfig config;
    protected HunterPrintWriter writer = new HunterPrintWriter();
    protected String uuid;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    HunterProcessor() {
    }

    HunterProcessor(HunterConfig m) {
        this(m, UUID.randomUUID().toString());
    }

    HunterProcessor(HunterConfig m, String uuid) {
        this(m, null, uuid);
    }

    HunterProcessor(HunterConfig config, HunterPrintWriter writer, String uuid) {
        this.config = HunterConfigContext.parseConfig(config);
        this.uuid = uuid;
        if (null != writer) {
            this.writer = writer;
        }
    }

    HunterProcessor(String url, boolean convertImage) {
        this(HunterConfigContext.getHunterConfig(url).setConvertImg(convertImage));
    }

    HunterProcessor(String url, boolean convertImage, HunterPrintWriter writer) {
        this(HunterConfigContext.getHunterConfig(url).setConvertImg(convertImage));
        if (writer != null) {
            this.writer = writer;
        }
    }

    /**
     * 程序入口方法
     *
     * @return 返回VirtualArticle列表
     */
    public abstract CopyOnWriteArrayList<VirtualArticle> execute();

    @Override
    public void process(Page page) {
        Resolver resolver = new HtmlResolver();
        if (config.getAjaxRequest()) {
            resolver = new JsonResolver();
        }
        resolver.process(page, config);

    }

    @Override
    public Site getSite() {
        Site site = Site.me()
                .setCharset(config.getCharset())
                .setDomain(config.getDomain())
                .setUserAgent(config.getUa())
                .setSleepTime(config.getSleepTime())
                .setRetryTimes(config.getRetryTimes())
                .setCycleRetryTimes(config.getCycleRetryTimes());

        //添加抓包获取的cookie信息
        List<Cookie> cookies = config.getCookies();
        if (CollectionUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (StringUtils.isEmpty(cookie.getDomain())) {
                    site.addCookie(cookie.getName(), cookie.getValue());
                    continue;
                }
                site.addCookie(cookie.getDomain(), cookie.getName(), cookie.getValue());
            }
        }
        //添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
        Map<String, String> headers = config.getHeaders();
        if (MapUtils.isNotEmpty(headers)) {
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                site.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return site;
    }

    /**
     * 校验参数
     *
     * @param t 待校验的参数
     */
    final <T> List<String> validateModel(T t) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);

        List<String> messageList = new ArrayList<>();
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            messageList.add(constraintViolation.getMessage());
        }
        return messageList;
    }

    /**
     * 自定义管道的处理方法
     *
     * @param resultItems     自定义Processor处理完后的所有参数
     * @param virtualArticles 爬虫文章集合
     */
    final void process(ResultItems resultItems, List<VirtualArticle> virtualArticles, Hunter spider) {
        if (null == spider) {
            return;
        }
        Map<String, Object> map = resultItems.getAll();
        if (CollectionUtil.isEmpty(map)) {
            return;
        }
        String title = String.valueOf(map.get("title"));
        ParserConfig jcParserConfig = new ParserConfig();
        jcParserConfig.putDeserializer(Date.class, HunterDateDeserializer.instance);
        VirtualArticle virtualArticle = JSON.parseObject(JSON.toJSONString(map), VirtualArticle.class, jcParserConfig, JSON.DEFAULT_PARSER_FEATURE);
        virtualArticle.setDescription(CommonUtil.getRealDescription(virtualArticle.getDescription(), virtualArticle.getContent()))
                .setKeywords(CommonUtil.getRealKeywords(virtualArticle.getKeywords()));
        if (this.config.isConvertImg()) {
            virtualArticle.setContent(CommonUtil.formatHtml(virtualArticle.getContent()));
            virtualArticle.setImageLinks(CommonUtil.getAllImageLink(virtualArticle.getContent()));
        }
        if (CollectionUtils.isEmpty(virtualArticle.getTags())) {
            virtualArticle.setTags(Collections.singletonList("其他"));
        }
        virtualArticles.add(virtualArticle);
        writer.print(String.format("<a href=\"%s\" target=\"_blank\">%s</a> -- %s -- %s", virtualArticle.getSource(), title, virtualArticle.getAuthor(), virtualArticle.getReleaseDate()));
    }

    public HunterConfig getConfig() {
        return config;
    }
}
