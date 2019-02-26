package me.zhyd.hunter.util;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;

/**
 * 系统输出工具类，当传入PrintWriter时可以将字符流输出到页面
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2017/11/18 11:48
 * @since 1.0
 */
@Slf4j
public class ResponseWriterUtil {

    private PrintWriter writer;

    public ResponseWriterUtil() {
    }

    public ResponseWriterUtil(PrintWriter writer) {
        this.writer = writer;
    }

    public ResponseWriterUtil print(String... msgs) {
        if (null == writer) {
            for (String msg : msgs) {
                log.info(msg);
            }
            return this;
        }
        for (String msg : msgs) {
            log.info(msg);
            writer.print("<script>parent.printMessage('" + msg + "');</script>");
            if (null != writer) {
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
