package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

/**
 * @author lixiangxin
 * @date 2019/6/11 00:18
 * 首页 策略
 **/
public class IndexStrategy extends AutoStrategy {
  @Override
  public void exec() {
    Point point1 = null;
    Point point2;

    while ((point2 = OpenCvUtils.findExitAction()) != null || (point1 = OpenCvUtils.findNextAction()) != null) {
      ShellUtils.executePoint(point2);
      ShellUtils.executePoint(point1);
    }

    OpenCvUtils.loopFindIfNullExit("exit_btn");

    OpenCvUtils.loopFindIfNullExit("next_btn");

  }


}
