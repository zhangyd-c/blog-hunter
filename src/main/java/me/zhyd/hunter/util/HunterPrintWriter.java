package me.zhyd.hunter.util;

import lombok.extern.slf4j.Slf4j;
import me.zhyd.hunter.consts.HunterConsts;

import java.io.PrintWriter;

/**
 * 系统输出工具类，当传入PrintWriter时可以将字符流输出到页面， 默认为log日志输出
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class HunterPrintWriter {

    private String jsoupCallback = "<script>parent.printMessage('%s');</script>";
    private PrintWriter writer;

    public HunterPrintWriter() {
    }

    /**
     * @param writer        输出流
     * @param jsoupCallback 用于页面打印日志的jsoup回调函数，默认为使用iframe方式打开，回调函数为‘parent.printMessage’。具体使用方法，可参考帮助文档
     */
    public HunterPrintWriter(PrintWriter writer, String jsoupCallback) {
        this.writer = writer;
        if (null != jsoupCallback) {
            this.jsoupCallback = jsoupCallback;
        }
    }

    /**
     * @param writer 输出流
     */
    public HunterPrintWriter(PrintWriter writer) {
        this(writer, null);
    }

    public HunterPrintWriter print(String... msgs) {
        for (String msg : msgs) {
            if (!msg.equals("shutdown")) {
                msg = HunterConsts.LOG_PREFIX + msg;
            }

            log.info(msg);
            if (null != writer) {
                writer.print(String.format(this.jsoupCallback, msg));
                writer.flush();
            }
        }

        return this;
    }

    public void shutdown() {
        print("bye~~", "shutdown");
        if (null != writer) {
            writer.close();
            writer = null;
        }
    }
}
