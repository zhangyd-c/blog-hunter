package me.zhyd.hunter.scheduler;

import me.zhyd.hunter.enums.ExitWayEnum;
import me.zhyd.hunter.config.HunterConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.DuplicateRemovedScheduler;
import us.codecraft.webmagic.scheduler.MonitorableScheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义的调度器，主要用来处理url
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 */
public class BlockingQueueScheduler extends DuplicateRemovedScheduler implements MonitorableScheduler {
    private BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private int realUrlCount = -1;

    public BlockingQueueScheduler(HunterConfig model) {
        if (ExitWayEnum.URL_COUNT.toString().equals(model.getExitWay())) {
            // 实际抓取的url数量包括入口页面
            this.realUrlCount = model.getCount() + model.getEntryUrls().size();
        }
    }

    @Override
    public void pushWhenNoDuplicate(Request request, Task task) {
        // 当程序退出方式非URL_COUNT时按照正常逻辑处理
        if (realUrlCount == -1) {
            this.queue.add(request);
            return;
        }
        // 在有效期内(realUrlCount > 0)，每次push url时realUrlCount - 1， 当 realUrlCount <= 0 时，当前Scheduler将不再收录新的url
        if (realUrlCount <= 0) {
            return;
        }
        realUrlCount--;
        this.queue.add(request);
    }

    @Override
    public Request poll(Task task) {
        return (Request) this.queue.poll();
    }

    @Override
    public int getLeftRequestsCount(Task task) {
        return this.queue.size();
    }

    @Override
    public int getTotalRequestsCount(Task task) {
        return this.getDuplicateRemover().getTotalRequestsCount(task);
    }
}
