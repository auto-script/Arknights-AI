package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
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

        Point point, loginPoint;

        point = OpenCvUtils.findImage(Action.START_WAKE);
        if (point != null) {
            ShellUtils.executePoint(point);
            ShellUtils.sleepTime(RandomUtils.getRandom(6, 8));


            Point andAction = OpenCvUtils.findImage(Action.YES_3);

            // 记忆已经模糊，请重新输入登录信息
            Point jiYiMoHu = OpenCvUtils.findImage(Action.JI_YI_MO_HU);

            if (andAction != null && jiYiMoHu == null) {
                throw new RuntimeException("检测到异常");
            }

            if (andAction != null) {
                ShellUtils.executePoint(andAction);
            }
        }

        loginPoint = OpenCvUtils.findLoginAccountBtn();

        if (loginPoint == null) {
            System.out.println("可能正在登录中...");
            return;
        }

        ShellUtils.executePoint(loginPoint);
        ShellUtils.sleepTime(5);

        autoLogin();
    }

    public void autoLogin() {
        Point loginPoint = OpenCvUtils.findImage(Action.LOGIN_MLICK_INPUT);

        if (loginPoint == null) {
            loginPoint = OpenCvUtils.findAndAction(Action.LOGIN_ACCOUNT_INPUT);
            if (loginPoint == null) {
                System.out.println("账号输入按钮失败,待处理");
            }

            ShellUtils.execute(adbPath, "shell", "input", "text", "18321295235");

            OpenCvUtils.findAndAction(Action.ENTER_BTN);
        }

        OpenCvUtils.findAndAction(Action.LOGIN_PASSWORD_INPUT);
        ShellUtils.sleepTime(3);

        ShellUtils.execute(adbPath, "shell", "input", "text", "301415926l");

        OpenCvUtils.findAndAction(Action.ENTER_BTN);

        OpenCvUtils.findAndAction(Action.LOGIN_BTN);

        ShellUtils.sleepTime(15);

    }

}
