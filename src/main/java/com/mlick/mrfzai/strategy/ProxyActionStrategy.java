package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoProxy;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.RandomUtils;
import org.opencv.core.Point;

import java.io.IOException;

import static com.mlick.mrfzai.utils.ShellUtils.*;

/**
 * @author lixiangxin
 * @date 2019/6/6 13:06
 * 自动刷图界面
 **/
public class ProxyActionStrategy extends AutoStrategy implements AutoProxy {

  @Override
  public void exec() {
    int s = 1;

    do {
      System.out.println("第" + s++ + "次执行");
      try {
        loopExec(s);
      } catch (IOException ignored) {
      }
    } while (true);

  }

  @Override
  public void loopExec(int n) throws IOException {
    // 开始行动 1
    System.out.println("开始行动 1");

    screenCap("current" + ".png");

    Point point = OpenCvUtils.findProxyStartAction();

    if (point == null) {
      System.err.println("未找到 开始行动 1");
      throw new RuntimeException("未找到 开始行动 1");
    }
    executePoint(point);

    sleepTime(RandomUtils.getRandom(5, 8));

    // 开始行动 2
    System.out.println("开始行动 2");
    point = OpenCvUtils.findStartAction();

    if (point == null) {
      System.err.println("未找到 开始行动 2");
    } else {
      executePoint(point);
      // 等待 战斗结束
      sleepTime2(RandomUtils.getRandom(120, 180));
    }

    // 退出结算页面
    System.out.println("退出结算页面");
    point = OpenCvUtils.findEndAction();
    if (point == null) {// 可能出现 失误
      point = OpenCvUtils.findContinueAction();
      
      if (point == null) { // 可能是 等级升级
        point = OpenCvUtils.findLevelUpAction();
        System.out.println("检测到 等级升级界面");
      } else {
        System.out.println("检测到 出现失误界面");
      }

      executePoint(point);
      sleepTime(RandomUtils.getRandom(5, 8));

      point = OpenCvUtils.findEndAction();
    }

//    screenCap(picPath + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss")) + n + ".png");

    executePoint(point);

    sleepTime(RandomUtils.getRandom(10, 20));
  }

}
