package me.zhyd.hunter.exception;

import me.zhyd.hunter.consts.HunterConsts;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class HunterException extends RuntimeException {

    public HunterException(String message) {
        super(HunterConsts.LOG_PREFIX + message);
    }

    public HunterException(String message, Throwable cause) {
        super(HunterConsts.LOG_PREFIX + message, cause);
    }
}
