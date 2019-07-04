package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

/**
 * @author lixiangxin
 * @date 2019/6/27 23:52
 * 基建 策略管理
 **/
public class BuildStrategy extends AutoStrategy {


  @Override
  public void exec() {

    OpenCvUtils.findAndAction("build_btn.png");
    ShellUtils.sleepTime(3);

    Point point;

    while ((point = OpenCvUtils.findImage("product_tip.png")) != null) {
      ShellUtils.executePoint(point);
      ShellUtils.sleepTime(3);
    }

    while ((point = OpenCvUtils.findImage("order_tip.png")) != null) {
      ShellUtils.executePoint(point);
      ShellUtils.sleepTime(3);

      OpenCvUtils.findAndAction("order_list_btn.png");
      ShellUtils.sleepTime(3);

      while ((point = OpenCvUtils.findImage("get_money_btn.png")) != null) {
        ShellUtils.executePoint(point);
        ShellUtils.sleepTime(3);
      }


    }

  }


}
