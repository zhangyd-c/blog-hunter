package me.zhyd.hunter.exception;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
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
