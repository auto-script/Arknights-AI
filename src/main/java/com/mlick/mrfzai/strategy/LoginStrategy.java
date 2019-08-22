package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.RandomUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/6/10 23:54
 * <p>
 * 登陆 逻辑
 **/
public class LoginStrategy extends AutoStrategy {

  @Override
  public void exec() {

    Point point = OpenCvUtils.findImage("email_btn.png");
    if (point != null) {
      System.out.println("检测到已在首页");
      return;
    }

    // 点击开始 按钮
    while ((point = OpenCvUtils.findStart()) == null) {
      OpenCvUtils.findImage("yes_btn");
      ShellUtils.sleepTime(5);
    }
    ShellUtils.executePoint(point);

    ShellUtils.sleepTime(5);

    point = OpenCvUtils.loopFind("start_wake.png");

    if (point != null) {
      System.out.println("发现【开始唤醒】...");
      ShellUtils.executePoint(point);
      ShellUtils.sleepTime(RandomUtils.getRandom(6, 8));
    }

    point = OpenCvUtils.findNextWhiteAction();
    Point loginPoint = OpenCvUtils.findLoginAccountBtn();

    if (point == null && loginPoint == null) {
      System.out.println("登陆可能成功");
      return;
    }

    if (point != null) {
      System.out.println("发现【登陆失败】或者【更新】...");
      ShellUtils.executePoint(point);
      ShellUtils.sleepTime(RandomUtils.getRandom(1, 2));
    }

    loginPoint = OpenCvUtils.findLoginAccountBtn();

    while (loginPoint == null) {
      System.out.println("等待检测【账号登陆】...");
      loginPoint = OpenCvUtils.findLoginAccountBtn();
      ShellUtils.sleepTime(5);
    }

    System.out.println("发现【账号登陆】...");
    ShellUtils.executePoint(loginPoint);
    ShellUtils.sleepTime(5);

    autoLogin();

  }

  public void autoLogin() {
    Point loginPoint = OpenCvUtils.findImage("login_mlick_input.png");

    // 重试 3 次
    for (int i = 1; i <= 3 && loginPoint == null; i++) {
      loginPoint = OpenCvUtils.findAndAction("login_account_input.png");
      ShellUtils.sleepTime(i + 1);
      ShellUtils.execute(adbPath, "shell", "input", "text", "18321295235");
      OpenCvUtils.findAndAction("enter_btn.png");
    }

    OpenCvUtils.findAndAction("login_password_input.png");

    ShellUtils.execute(adbPath, "shell", "input", "text", "123456lxx");

    OpenCvUtils.findAndAction("enter_btn.png");

    OpenCvUtils.findAndAction("login_btn.png");

    ShellUtils.sleepTime(10);

    // TODO 可能登录失败

  }

}
