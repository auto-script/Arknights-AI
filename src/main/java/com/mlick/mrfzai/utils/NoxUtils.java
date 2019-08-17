package com.mlick.mrfzai.utils;

import java.util.List;

/**
 * @author lixiangxin
 * @date 2019/8/13 08:02
 **/
public class NoxUtils {

    public static void startUp() {
        new Thread(() -> {
            ShellUtils.executeCmdByResult("\"D:\\Program Files\\Nox\\bin\\Nox.exe\" -clone:nox -startPackage:com.hypergryph.arknights");
            System.out.println("exec finish");
        }).start();
    }

    public static void shutDown() {
        ShellUtils.executeCmdByResult("taskkill /fi \"imagename eq nox.exe\" /f");
    }


    /**
     * 模拟器是否运行中
     *
     * @return true表示启动中, false表示没启动
     */
    public static boolean isRunning() {
        List<String> strings = ShellUtils.executeCmdByResult("tasklist /fi \"imagename eq nox.exe\"");

        if (strings == null) {
            return false;
        }

        for (String s : strings) {
            if (s.contains("nox.exe") || s.contains("Nox.exe")) {
                return true;
            }
        }

        return false;
    }
}
