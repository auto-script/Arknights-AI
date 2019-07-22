package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoProxy;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.RandomUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

import java.io.IOException;

import static com.mlick.mrfzai.utils.ShellUtils.*;

/**
 * @author lixiangxin
 * @date 2019/6/6 13:06
 * 自动刷图界面
 **/
public class ProxyActionStrategy extends AutoStrategy implements AutoProxy {

  private int num = 1;
  private int maxCount = Integer.MAX_VALUE;

  public ProxyActionStrategy() {
  }

  public ProxyActionStrategy(int maxCount) {
    this.maxCount = maxCount;
  }

  @Override
  public void exec() {
    int s = 1;
    do {
      System.out.println("第" + s++ + "次执行");
      try {
        loopExec(s);
      } catch (IOException ignored) {
      }
    } while (s <= maxCount);

  }

  @Override
  public void loopExec(int n) throws IOException {
    // 开始行动 1
    System.out.println("开始行动 1");

    screenCap("current" + ".png");

    Point point = OpenCvUtils.findProxyStartAction();

    if (point == null) {
      ShellUtils.sleepTime(2);
      System.err.println("未找到 开始行动 1");
      ShellUtils.sleepTime(2);

      //处理可能出现其它情况 随机点击一处屏幕
      ShellUtils.tapPhone(255, 255);
      ShellUtils.sleepTime(3);
    }

    executePoint(point);
    sleepTime(RandomUtils.getRandom(5, 8));

    // 开始行动 2
    System.out.println("开始行动 2");
    point = OpenCvUtils.findStartAction();

    if (point == null) {
      ShellUtils.sleepTime(2);
      System.err.println("未找到 开始行动 2");
      ShellUtils.sleepTime(2);

      // 处理智力不够情况
      processNoWit();
    } else {
      executePoint(point);
    }

    // 等待 战斗结束
    sleepTime2(RandomUtils.getRandom(120, 180));
    // 退出结算页面
    System.out.println("退出结算页面");
    point = OpenCvUtils.findEndAction();


    for (int i = 0; i < 6 && point == null; i++) {// 可能出现失误 重试

      ShellUtils.sleepTime(2);
      System.err.println("没有找到结算页面");
      ShellUtils.sleepTime(2);

      point = OpenCvUtils.findContinueAction();

      if (point == null) { // 可能是 等级升级
        point = OpenCvUtils.findLevelUpAction();
        if (point != null) {
          OpenCvUtils.findAndAction("recover_wit.png");
          System.out.println("检测到 等级升级界面");
        }
      } else {
        System.out.println("检测到 出现失误界面");
      }

      executePoint(point);
      sleepTime(RandomUtils.getRandom(5, 8));

      point = OpenCvUtils.findEndAction();
    }

    executePoint(point);

    sleepTime(RandomUtils.getRandom(10, 20));
  }

  private void processNoWit() {
    System.out.println("处理可能没理智的情况");

    // 仅换3次源石
    if (num++ >= 3) {
      System.out.println("检测到已经兑换了3次源石了，等待6分钟后执行...");

      // 等待6分钟
      ShellUtils.sleepTime(6 * 60);
      OpenCvUtils.findAndAction("exit_black.png");
    } else {
      OpenCvUtils.findAndAction("next_black.png");
    }

    ShellUtils.sleepTime(3);
    OpenCvUtils.findAndAction("start_action_btn.png");
    ShellUtils.sleepTime(3);
    Point andAction = OpenCvUtils.findAndAction("start_action_btn2.png");

    // 如果未找到
    if (andAction == null) {
      processNoWit();
    }
  }

}