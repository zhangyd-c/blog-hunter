package me.zhyd.hunter.util;

import me.zhyd.hunter.exception.HunterException;

import java.util.Date;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2019/2/26 19:03
 * @since 1.8
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {
    private static final String PATTERN1 = "yyyy/MM/dd HH:mm:ss";
    private static final String PATTERN2 = "yyyy/MM/dd";
    private static final String PATTERN3 = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN4 = "yyyy-MM-dd";
    private static final String PATTERN5 = "yyyyMMddHHmmssSSS";
    private static final String PATTERN6 = "HH:mm";
    private static final String PATTERN7 = "dd/MM/yyyy";
    private static final String PATTERN8 = "dd/MM/yyyy HH:mm:ss";
    private static final String PATTERN9 = "dd-MM-yyyy HH:mm:ss";
    private static final String PATTERN10 = "dd-MM-yyyy";
    private static final String PATTERN11 = "yyyy年MM月dd日 00:00:00";
    private static final String PATTERN12 = "yyyy-MM-dd HH:mm";
    private static final String PATTERN13 = "yyyy.MM.dd HH:mm";

    private static final String FULL_TIME_PATTERN0 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static Date parse(Object originalDateObj) {
        if (null == originalDateObj) {
            return null;
        }
        String originalDateStr = String.valueOf(originalDateObj);
        String pattern = null;
        boolean containsSemicolon = originalDateStr.contains(":");
        boolean fullTimeFormat = originalDateStr.length() > 20;
        if (fullTimeFormat) {
            pattern = FULL_TIME_PATTERN0;
        } else if (originalDateStr.contains("/")) {
            if (originalDateStr.split("/")[0].length() == 2) {
                pattern = containsSemicolon ? PATTERN8 : PATTERN7;
            } else {
                pattern = containsSemicolon ? PATTERN1 : PATTERN2;
            }
        } else if (originalDateStr.contains("-")) {
            if (originalDateStr.split("-")[0].length() == 2) {
                pattern = containsSemicolon ? PATTERN9 : PATTERN10;
            } else {
                pattern = containsSemicolon ? (originalDateStr.split(":").length == 2 ? PATTERN12 : PATTERN3) : PATTERN4;
            }
        } else if (originalDateStr.contains("年") || originalDateStr.contains("月")) {
            pattern = PATTERN11;
        } else if (originalDateStr.contains(".")) {
            pattern = PATTERN13;
        } else {
            if (originalDateStr.length() <= 5) {
                pattern = PATTERN6;
            } else {
                pattern = PATTERN5;
            }
        }

        Date date = null;
        try {
            date = parse(originalDateStr, pattern);
        } catch (Exception e1) {
            throw new HunterException("Invalid date. [" + originalDateStr + "]");
        }
        return date;
    }

}
