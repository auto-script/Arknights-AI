package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author lixiangxin
 * @date 2019/6/27 23:52
 * 基建 策略管理
 **/
@SuppressWarnings("InfiniteLoopStatement")
public class BuildStrategy extends AutoStrategy {

  private static Logger logger = LoggerFactory.getLogger(BuildStrategy.class);

  private int max = 1;

  public BuildStrategy() {
  }

  public BuildStrategy(int max) {
    this.max = max;
  }

  @Override
  public void exec() {
    Point action = OpenCvUtils.findAndAction("build_btn.png");
    if (action == null) {
      OpenCvUtils.findAndAction("home.png");
      ShellUtils.sleepTime(2);
      OpenCvUtils.findAndAction("base_build.png");
    }
    ShellUtils.sleepTime(10);

    while (max-- > 0) {
      task();

      if (max > 0) {
        logger.info("等待十分钟...");
        try {
          TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

  }

  private void task() {

    Point point;

    // 黄色产品

    // 方式已过时
//    while ((point = OpenCvUtils.findImage("product_tip.png")) != null) {
//      ShellUtils.executePoint(point);
//      ShellUtils.sleepTime(3);
//    }



    // 蓝色产品
    while ((point = OpenCvUtils.findImage("order_tip.png")) != null) {
      ShellUtils.executePoint(point);
      ShellUtils.sleepTime(3);

      OpenCvUtils.findAndAction("order_list_btn.png");
      ShellUtils.sleepTime(3);

      while ((point = OpenCvUtils.findImage("get_money_btn.png")) != null) {
        ShellUtils.executePoint(point);
        ShellUtils.sleepTime(3);
      }

      ShellUtils.executePoint(OpenCvUtils.findBlackBackBtn());
      ShellUtils.sleepTime(3);
      ShellUtils.executePoint(OpenCvUtils.findBlackBackBtn());
    }

    // TODO  员工信任


    // TODO 线索收集


    // TODO 干员疲劳


    /* 退出基建*/
    ShellUtils.sleepTime(3);
    ShellUtils.executePoint(OpenCvUtils.findBlackBackBtn());


    OpenCvUtils.loopFindIfNullExit(Action.BACK_BLACK_BTN);

  }


}
