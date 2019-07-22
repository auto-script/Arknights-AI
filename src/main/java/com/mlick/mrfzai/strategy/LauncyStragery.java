package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

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

    Point start = null;;

    // 点击开始 按钮  重试 3 次
    for (int i = 0; i < 3 && (start  = OpenCvUtils.findStart()) == null; i++) {
      System.out.println("等待启动完毕..."+i);
      ShellUtils.sleepTime(3);

      Point nextWhiteAction = OpenCvUtils.findNextWhiteAction();
      if (nextWhiteAction != null) {
        ShellUtils.executePoint(nextWhiteAction);
      }
    }

    if (start != null){
      ShellUtils.executePoint(start);
    }

    System.out.println("启动完毕!");

    ShellUtils.sleepTime(5);
  }
}
