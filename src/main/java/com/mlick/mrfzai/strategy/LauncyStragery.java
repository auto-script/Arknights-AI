package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/6/28 07:59
 **/
public class LauncyStragery extends AutoStrategy {

  @Override
  public void exec() {

    // 包名 + 启动类名
    ShellUtils.executeByResult(
        adbPath, "shell", "am start -n com.hypergryph.arknights/com.u8.sdk.U8UnityContext");
    ShellUtils.sleepTime(5);

    // 点击开始 按钮
    while (OpenCvUtils.findStart() == null) {
      System.out.println("等待启动完毕...");
      ShellUtils.sleepTime(3);
    }
    System.out.println("启动完成!");
  }
}
