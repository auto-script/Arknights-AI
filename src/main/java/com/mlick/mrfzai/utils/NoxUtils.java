package com.mlick.mrfzai.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author lixiangxin
 * @date 2019/8/13 08:02
 **/
public class NoxUtils {

    private static Logger logger = LoggerFactory.getLogger(NoxUtils.class);

    public static void startUp() {
        ShellUtils.executeCmd("cmd /k \"D:\\Program Files\\Nox\\bin\\Nox.exe\" -clone:nox -startPackage:com.hypergryph.arknights");
        logger.info("exec finish");
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
