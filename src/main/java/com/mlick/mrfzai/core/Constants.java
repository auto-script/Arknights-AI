package com.mlick.mrfzai.core;

/**
 * @author lixiangxin
 * @date 2019/8/19 13:51
 **/
public class Constants {

    public static final double DESIRED_ACCURACY = 0.78;

    public volatile static boolean isVmRunning = false;

    public volatile static String indexPath = "";
    public volatile static String FILE_TEMP_PATH = "tempFile/";
    public volatile static String screenPath = "screen.png";
    public volatile static String resultPath = "result.png";


    public final static int SLEEP_TIME = 5;
    public final static int RETRY_COUNT = 5;

    public final static int EXIST_FILE_COUNT = 8;

}
