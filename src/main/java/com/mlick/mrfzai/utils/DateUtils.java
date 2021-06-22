package com.mlick.mrfzai.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lixiangxin
 * @date 2019/7/24 15:27
 **/
public class DateUtils {

    public static String formatDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getNowDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss"));
    }
    
}
