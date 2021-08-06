package com.mlick.mrfzai.utils;

import com.mlick.mrfzai.core.Constants;
import org.opencv.core.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.mlick.mrfzai.core.Constants.EXIST_FILE_COUNT;
import static com.mlick.mrfzai.core.Constants.FILE_TEMP_PATH;

/**
 * @author lixiangxin
 * @date 2019/6/5 11:05
 **/
public class ShellUtils {

    private static Logger logger = LoggerFactory.getLogger(ShellUtils.class);

    private ShellUtils() {
    }

    public static String adbPath = Paths.get("res/adb/%s.exe").toFile().getAbsolutePath();


    public static void screenCap() {
        String nowDate = DateUtils.getNowDate();
        Constants.screenPath = String.format("%s-screen.png", nowDate);
        Constants.resultPath = String.format("%s-result.png", nowDate);
        try {
            screenCap(Constants.screenPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void screenCap(String saveFile) throws IOException {
        checkFileMore();
        boolean result = execute(false, adbPath, "shell", "screencap", "-p", "/sdcard/screen.png");
        if (!result) {
            throw new RuntimeException("手机分辨率检测失败，请检查电脑与手机连接和手机设置。");
        }

        File pullFile = new File(FILE_TEMP_PATH + saveFile);
        result = execute(false, adbPath, "pull", "/sdcard/screen.png", pullFile.getAbsolutePath());
        if (!result) {
            System.err.println("手机分辨率检测失败，请检查电脑与手机连接和手机设置。");
            return;
        }

//        logger.info("pull screen.png success:" + pullFile.getAbsolutePath());

        BufferedImage src = ImageIO.read(new FileInputStream(pullFile));
        if (src.getWidth() >= src.getHeight()) {
            return;
        }
        //顺时针旋转270度
        BufferedImage des1 = RotateImage.Rotate(src, 270);
        ImageIO.write(des1, "png", pullFile);
    }

    private static void checkFileMore() {
        File pathTempDir = new File(FILE_TEMP_PATH);

        if (!pathTempDir.isDirectory()) {
            return;
        }

        File[] files = pathTempDir.listFiles((dir, name) -> name.endsWith(".png"));

        if (files == null || files.length == 0) {
            return;
        }

        // 按照最新修改的倒序排序
        Arrays.sort(files, (f1, f2) -> {
            long diff = f1.lastModified() - f2.lastModified();
            if (diff > 0)
                return -1;
            else if (diff == 0)
                return 0;
            else
                return 1;
        });

        for (int i = EXIST_FILE_COUNT; i < files.length; i++) {
            files[i].deleteOnExit();
        }
    }

    public static ArrayList<String> getConnectedDevices() {
        ArrayList<String> devices = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{adbPath, "devices"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean startCount = false;
            String deviceName;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
                if (line.contains("List of devices attached")) {
                    startCount = true;
                } else if (startCount && !line.trim().isEmpty()
                        && !line.contains("unauthorized")
                        && !line.contains("offline")
                        && !line.contains("* daemon")) {
                    String seg[] = line.split("\t");
                    deviceName = seg[0];
                    devices.add(deviceName);
                }
            }
        } catch (Exception e) {
        }
        return devices;
    }


    public static String openOrClosePower() {
        return String.format("input keyevent %d", 26);
    }

    public static void swipePhone(int sx, int sy, int ex, int ey) {
        executeCommand(String.format("input touchscreen swipe %d %d %d %d", sx, sy, ex, ey));
    }

    public static String getTapPhone(Point point) {
        return String.format("input tap %f %f", point.x, point.y);
    }

    public static String getTapPhone(int x, int y) {
        return String.format("input tap %d %d", x, y);
    }

    public static void tapPhone(int x, int y) {
        executeCommand(String.format("input tap %d %d", x, y));
        ShellUtils.sleepTime(3);
    }


    // false 表示屏幕关闭 || true 表示屏幕亮起
    public static boolean getIsPower() {
        //Process process = Runtime.getRuntime().exec("su dumpsys power|findstr \"mScreenOn=\" \n");
        //List<String> list = readSuccess(process);
        //process.destroy();
        //String cmdr = list != null && list.size() > 0 ? list.get(0) : null;
        ////        runCmd("adb shell dumpsys power|findstr \"mScreenOn=\"");
        List<String> cmdr = executeByResult(adbPath, "shell", "dumpsys power");
        if (cmdr == null || cmdr.size() == 0) {
            return false;
        }
        return false;
    }


    /**
     * 执行命令并且输出结果
     *
     * @return
     */
    public static List<String> executeByResult(String... cmd) {
        logger.debug("==>" + Arrays.toString(cmd).replaceAll(",", " "));
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            return readSuccess(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void executeCmd(String cmd) {
        logger.debug("==>" + cmd);
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> executeCmdByResult(String cmd) {
        logger.debug("==>" + cmd);
        try {
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("cmd /c " + cmd);
//      Process p = Runtime.getRuntime().exec(cmd);
            return readSuccess(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 模拟点击 事件
     *
     * @param point 坐标点
     * @return true 表示触发了点击命令执行成功 false 表示失败
     */
    public static boolean executePoint(Point point) {
        if (point == null) {
            return false;
        }
        return execute(false, adbPath, "shell", getTapPhone(point));
    }

    public static boolean executePoint(Point point, boolean flag) {
        if (point == null && flag) {
            tapPhone(480, 270);
        }
        if (point == null) {
            return false;
        }
        return execute(true, adbPath, "shell", getTapPhone(point));
    }


    public static boolean execute(String... cmd) {
        return execute(true, cmd);
    }

    public static boolean executeCommand(String cmd) {
        return execute(adbPath, "shell", cmd);
    }

    public static boolean execute(boolean show, String... cmd) {
        if (show) {
            logger.info(Arrays.toString(cmd).replaceAll(",", ""));
        }

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            int result = process.waitFor();
            if (result != 0) {
                logger.info("Failed to execute \"" + Arrays.toString(cmd) + "\", result code is " + result);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> readSuccess(Process proc) throws IOException {
        List<String> strs = new ArrayList<>();

        InputStream stdin = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin, "GBK");

        BufferedReader br = new BufferedReader(isr);
        String line;

        while ((line = br.readLine()) != null) {
            strs.add(line);
        }
        return strs;
    }

    /**
     * 等待时间
     *
     * @param n 等待时间数 单位为秒
     */
    public static void sleepTime(boolean show, int n) {
        if (show) {
            String ctx = "等待==>[" + n + "s]";
            System.out.println(ctx);
        }

        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepTime(int i) {
        sleepTime(true, i);
    }

    public static void sleepTime() {
        sleepTime(Constants.SLEEP_TIME);
    }

    public static void main(String[] args) {
        sleepTime(100);
    }

}
