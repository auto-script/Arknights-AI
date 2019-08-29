package com.mlick.mrfzai;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.quartz.QuartzManager;
import com.mlick.mrfzai.quartz.job.ArkNightsJob;
import com.mlick.mrfzai.strategy.*;
import com.mlick.mrfzai.utils.FactoryUtil;
import com.mlick.mrfzai.utils.ShellUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/6/1 16:38
 */
@SuppressWarnings("AlibabaRemoveCommentedCode")
public class MainAuto {

  public static void main(String[] args) {

//    getAdb(args);
//
//    FactoryUtil.exec(LauncyStragery.class);
//    FactoryUtil.exec(LoginStrategy.class);
//    FactoryUtil.exec(IndexStrategy.class);
//    FactoryUtil.exec(EmailStrategy.class);

    // 每隔1小时整点触发
    QuartzManager.addJob("ark","build", ArkNightsJob.class,"0 0 0/1 * * ?",1);

    //每隔4小时10分时触发
    QuartzManager.addJob("ark","per4hour", ArkNightsJob.class,"0 10 0/4 * * ?",2);

    QuartzManager.schedulerStart();
  }

  public static void getAdb(String[] args) {
    adbPath = String.format(adbPath, args.length == 0 ? "adb1036" : args[0]);

    ArrayList<String> connectedDevices = ShellUtils.getConnectedDevices();
    System.out.println(connectedDevices);
    if (connectedDevices.isEmpty()) {
      System.out.println("devices is empty");

      ShellUtils.executeByResult(adbPath, "connect 127.0.0.1:62001");
    }

    if (connectedDevices != null && connectedDevices.size() > 1){
      ShellUtils.executeByResult(adbPath, "connect 127.0.0.1:62001");
    }
  }
}
