package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoProxy;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.*;
import org.opencv.core.Point;

import java.io.IOException;

import static com.mlick.mrfzai.utils.ShellUtils.*;

/**
 * @author lixiangxin
 * @date 2019/6/6 13:06
 * 自动刷图界面
 **/
public class ProxyActionStrategy extends AutoStrategy implements AutoProxy {

  /**
   * 需要换购的原石数
   */
  private int num = 1;

  /**
   * 最大要执行的次数 为0表示不执行
   */
  private int maxCount = Integer.MAX_VALUE;

  public void setNum(int num) {
    this.num = num;
  }

  public void setMaxCount(int maxCount) {
    this.maxCount = maxCount;
  }

  public ProxyActionStrategy() {
  }

  public ProxyActionStrategy(int num) {
    this.num = num;
  }

  public static ProxyActionStrategy count(int i) {
    ProxyActionStrategy proxyActionStrategy = new ProxyActionStrategy();
    proxyActionStrategy.maxCount = i;
    return proxyActionStrategy;
  }
  public static ProxyActionStrategy energy(int i) {
    return new ProxyActionStrategy(i);
  }

  @Override
  public void exec() {

    OpenCvUtils.findAndAction("un_proxy_.png");

    int s = 1;
    do {
      System.out.println("第" + s + "次执行");
      try {
        loopExec(s);
      } catch (IOException ignored) {
      } finally {
        s++;
      }
    } while (s <= maxCount);

    FactoryUtil.exec(new JumpChapterStrategy(0));
  }

  @Override
  public void loopExec(int n) throws IOException {
    // 开始行动 1
    System.out.println("开始行动 1");

    screenCap("current" + ".png");

    Point point = OpenCvUtils.findProxyStartAction();

    if (point == null) {
      point = OpenCvUtils.loopFind("active_start_action", 3);
    }

    if (point == null) {
      ShellUtils.sleepTime(2);
      System.err.println("未找到 开始行动 1");
      ShellUtils.sleepTime(2);

      //处理可能出现其它情况 随机点击一处屏幕
      ShellUtils.tapPhone(255, 255);
      ShellUtils.sleepTime(3);

      point = OpenCvUtils.findProxyStartAction();
      point = OpenCvUtils.isNullFindAndAction(point, "active_start_action");
    }

    boolean b = executePoint(point);
    if (!b) {
      ShellUtils.sleepTime(2);
      System.err.println("未找到开始行动 1");
      ShellUtils.sleepTime(2);
      maxCount = 0;
      System.err.println("退出执行策略");
      return;
    }

    sleepTime(RandomUtils.getRandom(5, 8));

    // 开始行动 2
    System.out.println("开始行动 2");
    point = OpenCvUtils.findStartAction();

    if (point == null) {
      ShellUtils.sleepTime(2);
      System.err.println("未找到 开始行动 2");
      ShellUtils.sleepTime(2);

//            maxCount = 0;
//            System.out.println("退出本策略");
//            return;
      // 处理智力不够情况
      processNoWit3();
    } else {
      executePoint(point);
    }

    if (maxCount == 0) {
      OpenCvUtils.loopFind("exit_black.png", 3);
      return;
    }

    // 等待 战斗结束
    System.out.println("等待战斗结束...");
    sleepTime(RandomUtils.getRandom(120, 180));
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
    if (num-- < 0) {
      System.out.println("检测到已经兑换了" + num + "次源石了");
      maxCount = 0;
      return;
    }

    OpenCvUtils.findAndAction("next_black.png");

    ShellUtils.sleepTime(3);
    OpenCvUtils.findAndAction("start_action_btn.png");
    ShellUtils.sleepTime(3);
    OpenCvUtils.findAndAction("start_action_btn2.png");

  }

  private void processNoWit3() {
    System.out.println("处理可能没理智的情况");
    if (num == 1) {
      processNoWit2();
    } else {
      processNoWit();
    }
  }

  private void processNoWit2() {
    if (OpenCvUtils.findImage("yingji_wit") != null) {
      OpenCvUtils.findAndAction("next_black.png");
    } else {
      maxCount = 0;
    }
  }

}