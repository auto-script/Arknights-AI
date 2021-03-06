package com.mlick.mrfzai.utils;

import org.opencv.core.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lixiangxin
 * @date 2019/6/5 11:05
 **/
public class ShellUtils {

    private ShellUtils() {
    }

    public static String adbPath = Paths.get("resources/adb/%s.exe").toFile().getAbsolutePath();


    public static String screenPath = "screen.png";

    public static void screenCap() {
        try {
            screenCap(screenPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void screenCap(String saveFile) throws IOException {
        boolean result = execute(adbPath, "shell", "screencap", "-p", "/sdcard/screen.png");
        if (!result) {
            throw new RuntimeException("手机分辨率检测失败，请检查电脑与手机连接和手机设置。");
        }

        File pullFile = new File(saveFile);
        result = execute(false, adbPath, "pull", "/sdcard/screen.png", pullFile.getAbsolutePath());
        if (!result) {
            System.err.println("手机分辨率检测失败，请检查电脑与手机连接和手机设置。");
            return;
        }

        BufferedImage src = ImageIO.read(new FileInputStream(pullFile));
        if (src.getWidth() >= src.getHeight()) {
            return;
        }
        //顺时针旋转270度
        BufferedImage des1 = RotateImage.Rotate(src, 270);
        ImageIO.write(des1, "png", pullFile);

        System.out.println("pull screen.png success:" + pullFile.getAbsolutePath());
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
                System.out.println(line);
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
        System.out.println("==>" + Arrays.toString(cmd).replaceAll(",", " "));
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            return readSuccess(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> executeCmdByResult(String cmd) {
        System.out.println("==>" + cmd);
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


    public static boolean execute(String... cmd) {
        return execute(true, cmd);
    }

    public static boolean executeCommand(String cmd) {
        return execute(adbPath, "shell", cmd);
    }

    public static boolean execute(boolean show, String... cmd) {
        if (show) {
            System.out.println("==>" + Arrays.toString(cmd).replaceAll(",", ""));
        }

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            int result = process.waitFor();
            if (result != 0) {
                System.out.println("Failed to execute \"" + Arrays.toString(cmd) + "\", result code is " + result);
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> readSuccess(Process proc) throws IOException {
        System.out.println("readSuccess---------->");
        List<String> strs = new ArrayList<>();

        InputStream stdin = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin, "GBK");

        BufferedReader br = new BufferedReader(isr);
        String line;

        while ((line = br.readLine()) != null) {
            System.out.println("out ===>" + line);
            strs.add(line);
        }
        System.out.println("=================");
        return strs;
    }

    /**
     * 等待时间
     *
     * @param i 等待时间数 单位为秒
     *          注意: 默认会多等待3秒钟
     */
    public static void sleepTime(int i) {
        try {
            if (i < 5) {
                TimeUnit.SECONDS.sleep(i);
                return;
            }
            String ctx = "";
            for (int j = i; j > 0; j--) {
                ctx = "请等待==>" + j + "s";
                System.out.print(getBackslash(ctx.length()) + ctx);
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.print(getBackslash(ctx.length()));

            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//  public static void sleepTime2(int i) {
//    System.out.println("等待 战斗结束->" + i + "秒");
//    for (int j = i; j > 0; j--) {
//      System.out.print("\r" + j);
//      try {
//        TimeUnit.SECONDS.sleep(1);
//      } catch (InterruptedException ignored) {
//      }
//    }
//
//    System.out.print("\r");
//  }

    private static String getBackslash(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("\r");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        sleepTime(10);


        System.out.println("finish");
    }


}
