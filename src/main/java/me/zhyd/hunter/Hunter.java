package me.zhyd.hunter;

import me.zhyd.hunter.enums.ExitWayEnum;
import me.zhyd.hunter.config.HunterConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class Hunter extends Spider {

    /**
     * 用来保存正在运行的所有Spider，key要求唯一，一般为用户ID，需要调用方生成
     */
    public static final ConcurrentHashMap<String, Hunter> SPIDER_BUCKET = new ConcurrentHashMap<>();

    private HunterConfig config;

    /**
     * 唯一的key，一般为用户ID，需要调用方生成
     */
    private Object uuid;
    private volatile long startTime = 0L;

    private Hunter(PageProcessor pageProcessor, HunterConfig config, String uuid) {
        super(pageProcessor);
        this.config = config;
        this.uuid = uuid;
        SPIDER_BUCKET.put(uuid, this);
    }

    public static Hunter create(PageProcessor pageProcessor, HunterConfig model, String uuid) {
        return new Hunter(pageProcessor, model, uuid);
    }

    @Override
    protected void onSuccess(Request request) {
        super.onSuccess(request);
        if (this.getStatus() == Status.Running && ExitWayEnum.DURATION.toString().equals(config.getExitWay())) {
            if (startTime < System.currentTimeMillis()) {
                this.stop();
            }
        }
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis() + config.getCount() * 1000;
        super.run();
    }

    @Override
    protected void onError(Request request) {
        super.onError(request);
    }

    @Override
    public void close() {
        super.close();
        SPIDER_BUCKET.remove(this.uuid);
    }

    @Override
    public void stop() {
        super.stop();
//        this.close();
        SPIDER_BUCKET.remove(this.uuid);
    }
}
