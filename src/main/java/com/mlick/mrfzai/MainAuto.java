package com.mlick.mrfzai;

import com.mlick.mrfzai.quartz.QuartzManager;
import com.mlick.mrfzai.quartz.job.ArkNightsJob;
import com.mlick.mrfzai.utils.ShellUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/6/1 16:38
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
public class MainAuto {
    private static Logger logger = LoggerFactory.getLogger(MainAuto.class);

    public static void main(String[] args) {

//    getAdb(args);
//
//    FactoryUtil.exec(LauncyStragery.class);
//    FactoryUtil.exec(LoginStrategy.class);
//    FactoryUtil.exec(IndexStrategy.class);
//    FactoryUtil.exec(EmailStrategy.class);

        // 每隔1小时整点触发
        QuartzManager.addJob("ark", "build", ArkNightsJob.class, "0 0 0/1 * * ?", 1);

        //每隔4小时10分时触发
        QuartzManager.addJob("ark", "per4hour", ArkNightsJob.class, "0 10 0/4 * * ?", 2);

        QuartzManager.schedulerStart();
    }

    public static void getAdb(String[] args) {
        adbPath = String.format(adbPath, args.length == 0 ? "adb1036" : args[0]);

        ArrayList<String> connectedDevices = ShellUtils.getConnectedDevices();
        if (connectedDevices.isEmpty()) {
            logger.info("devices is empty");

            ShellUtils.executeByResult(adbPath, "connect 127.0.0.1:62001");
        }

        int size = connectedDevices.size();
        if (size > 2) {
            for (int i = 2; i < size; i++) {
                String item = connectedDevices.get(i);
                String port = item.split("-")[1].split(" ")[0];
                logger.info(port);
                List<String> results = ShellUtils.executeByResult("netstat -ano|findstr " + port);
                if (results != null && !results.isEmpty()) {
                    results.forEach(s -> {
                        String[] s1 = s.split(" ");
                        String s2 = s1[s1.length - 1];
                        ShellUtils.executeByResult("taskkill /pid " + s2 + " /f");
                    });
                }
            }
        }

        
//        ShellUtils.executeByResult(adbPath, "connect 127.0.0.1:62001");
    }
}
