package me.zhyd.hunter.util;

import me.zhyd.hunter.exception.HunterException;

import java.util.Calendar;
import java.util.Date;

/**
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @since 1.8
 */
public class DateUtil extends cn.hutool.core.date.DateUtil {
    private static final String PATTERN1 = "yyyy/MM/dd HH:mm:ss";
    private static final String PATTERN16 = "yyyy/MM/dd HH:mm";
    private static final String PATTERN15 = "MM/dd HH:mm";
    private static final String PATTERN2 = "yyyy/MM/dd";
    private static final String PATTERN8 = "dd/MM/yyyy HH:mm:ss";
    private static final String PATTERN7 = "dd/MM/yyyy";

    private static final String PATTERN3 = "yyyy-MM-dd HH:mm:ss";
    private static final String PATTERN4 = "yyyy-MM-dd";
    private static final String PATTERN9 = "dd-MM-yyyy HH:mm:ss";
    private static final String PATTERN10 = "dd-MM-yyyy";

    private static final String PATTERN11 = "yyyy年MM月dd日";
    private static final String PATTERN12 = "yyyy年MM月dd日 00:00:00";
    private static final String PATTERN17 = "yyyy年MM月dd日 HH:mm:ss";

    private static final String PATTERN5 = "yyyyMMddHHmmssSSS";
    private static final String PATTERN6 = "HH:mm";

    private static final String PATTERN13 = "yyyy-MM-dd HH:mm";
    private static final String PATTERN14 = "yyyy.MM.dd HH:mm";

    private static final String FULL_TIME_PATTERN0 = "yyyy-MM-dd HH:mm:ss.SSS";

    public static Date parse(Object originalDateObj) {
        if (null == originalDateObj) {
            return null;
        }
        String originalDateStr = String.valueOf(originalDateObj);
        originalDateStr = originalDateStr.replace("T", " ").replace("Z", "");
        String pattern = null;
        boolean containsSemicolon = originalDateStr.contains(":");
        if (originalDateStr.length() > 20) {
            pattern = FULL_TIME_PATTERN0;
        } else if (originalDateStr.contains("/")) {
            if (originalDateStr.split("/")[0].length() == 2) {
                pattern = containsSemicolon ? (originalDateStr.split("/").length == 2 ? PATTERN15 : PATTERN8) : PATTERN7;
            } else {
                pattern = containsSemicolon ? PATTERN1 : PATTERN2;
            }
        } else if (originalDateStr.contains("-")) {
            if (originalDateStr.split("-")[0].length() == 2) {
                pattern = containsSemicolon ? PATTERN9 : PATTERN10;
            } else {
                pattern = containsSemicolon ? (originalDateStr.split(":").length == 2 ? PATTERN13 : PATTERN3) : PATTERN4;
            }
        } else if (originalDateStr.contains("年") || originalDateStr.contains("月")) {
            pattern = containsSemicolon ? (originalDateStr.contains("00:00") ? PATTERN12 : PATTERN17) : PATTERN11;
        } else if (originalDateStr.contains(".")) {
            pattern = PATTERN14;
        } else {
            if (originalDateStr.length() <= 5) {
                pattern = PATTERN6;
            } else {
                pattern = PATTERN5;
            }
        }
        Date date = null;
        try {
            if (pattern.equals(PATTERN15)) {
                pattern = PATTERN16;
                Calendar calendar = Calendar.getInstance();
                originalDateStr = calendar.get(Calendar.YEAR) + originalDateStr;
            }
            date = parse(originalDateStr, pattern);
        } catch (Exception ignored) {

        }
        return date;
    }

}
