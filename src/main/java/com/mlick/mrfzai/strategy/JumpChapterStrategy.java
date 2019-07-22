package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.RandomUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

/**
 * @author lixiangxin
 * @date 2019/7/11 11:29
 **/
public class JumpChapterStrategy extends AutoStrategy {

  private int stage;

  public JumpChapterStrategy(int i) {
    super();
    this.stage = i;
  }

  @Override
  public void exec() {


    Point andAction = OpenCvUtils.findAndAction("home_fight.png");
    if (andAction == null) {

      OpenCvUtils.findAndAction("home.png");
      ShellUtils.sleepTime(3);
      OpenCvUtils.findAndAction("fight.png");
      ShellUtils.sleepTime(8);
    }

    switch (stage) {
      case 1:// 跳转到经验
        execJump("fight_experience.png", "LS-5.png");
        break;

      case 2:// 跳转到龙门币
        execJump("fight_money.png", "CE-5.png");
        break;

      default:
        break;
    }

    OpenCvUtils.findAndAction("un_proxy_.png");
  }

  private void execJump(String s, String s2) {
    loopFindAction("goods.png");

    int i = loopFindAction(s);
    if (i == 0) {
      System.out.println("拖动...");
      ShellUtils.swipePhone(255, 255, 200, 255);
      loopFindAction(s);
    }

    loopFindAction(s2);
  }

  private int loopFindAction(String s) {
    int i = 3;
    while (OpenCvUtils.findAndAction(s) == null) {
      if (--i < 0) {
        System.err.println(i + "未找到" + s);
        ShellUtils.tapPhone(255, 255);
        return 0;
      }
      ShellUtils.sleepTime(RandomUtils.getRandom(1, 2));
    }
    return 1;
  }

}
