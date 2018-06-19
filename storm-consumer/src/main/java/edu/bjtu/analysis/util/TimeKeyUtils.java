package edu.bjtu.analysis.util;

import org.apache.commons.lang.StringUtils;

/**
 * 统计-时间戳key管理
 */
public class TimeKeyUtils {
    /**
     * @param timeString 2017-08-15T19:29:58+08
     * @return 2017-08-15 19:25:00
     * 5分钟
     */
    public static String timeKeyFormaterMinute(String timeString) {
        String fTime = StringUtils.replaceOnce(timeString, "T", " ");
        if (StringUtils.isNotEmpty(fTime) && fTime.length() > 17) {
            try {
                String hourStr = fTime.substring(0, 14);
                String minStr = fTime.substring(14, 16);
                int min = Integer.valueOf(minStr) / 5 * 5;
                return hourStr + min + ":00";
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * @param timeString 2017-08-15T19:29:58+08
     * @return 2017-08-15 19:00:00
     * 1小时
     */
    public static String timeKeyFormaterHour(String timeString) {
        String fTime = StringUtils.replaceOnce(timeString, "T", " ");
        if (StringUtils.isNotEmpty(fTime) && fTime.length() > 17) {
            try {
                String hourStr = fTime.substring(0, 14);
                return hourStr + "00:00";
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * @param timeString 2017-08-15T19:29:58+08
     * @return 2017-08-15
     * 1天
     */
    public static String timeKeyFormaterDay(String timeString) {
        try {
            return timeString.substring(0, 10);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param timeString 2017-08-15T19:29:58+08
     * @return 2017-08
     * 1月
     */
    public static String timeKeyFormaterMonth(String timeString) {
        try {
            return timeString.substring(0, 7);
        } catch (Exception e) {
            return null;
        }
    }
}
