package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

/**
 * @author lixiangxin
 * @date 2019/8/17 15:58
 **/
public class ActivityHuoLanZhiXinStrategy extends AutoStrategy {

  String tempImg;

  public ActivityHuoLanZhiXinStrategy(String s) {
    super();
    this.tempImg = s;
  }

  public static AutoStrategy get(String s) {
    return new ActivityHuoLanZhiXinStrategy(s);
  }

  @Override
  public void exec() {

    OpenCvUtils.loopFind("active_huo_lan_zhi_xin", 3);


    switch (tempImg) {
      case "OF-7":
      case "OF-8":
        OpenCvUtils.loopFind("zhu_wu_tai", 3);
        break;
      case "OF-F3":
      case "OF-F4":
        OpenCvUtils.loopFind("jia_nian_hua", 3);
        break;
      default:
        break;
    }

    // 黑曜石少于 4000时 用此策略
    Point point = OpenCvUtils.findAndAction(tempImg);
    int s = 10;
    while (point == null && s-- > 0) {
      ShellUtils.swipePhone(500, 100, 50, 100);
      ShellUtils.sleepTime(3);
      point = OpenCvUtils.findAndAction(tempImg);
    }

    //否则 刷门票
//    point = OpenCvUtils.findAndAction("OF-8");


  }
}
