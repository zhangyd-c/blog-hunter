package me.zhyd.hunter.exception;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/26 16:10
 * @since 1.8
 */
public class HunterException extends RuntimeException {

    public HunterException(String message) {
        super(message);
    }

    public HunterException(String message, Throwable cause) {
        super(message, cause);
    }
}
