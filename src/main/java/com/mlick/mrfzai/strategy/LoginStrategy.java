package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.RandomUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/6/10 23:54
 * <p>
 * 登陆 逻辑
 **/
public class LoginStrategy extends AutoStrategy {

    private static Logger logger = LoggerFactory.getLogger(LoginStrategy.class);
    @Override
    public void exec() {

        Point point, loginPoint;

        point = OpenCvUtils.findStartWake();
        if (point != null) {
            ShellUtils.executePoint(point);

            ShellUtils.sleepTime(RandomUtils.getRandom(6, 8));

            if (OpenCvUtils.findImage(Action.LOGIN_LOADING) != null) {
                logger.info("正在登录中...");
                return;
            }

            Point yesAction = OpenCvUtils.findImage(Action.YES_3);
            // 记忆已经模糊，请重新输入登录信息
            Point jiYiMoHu = OpenCvUtils.findImage(Action.JI_YI_MO_HU);

            if (yesAction != null && jiYiMoHu == null) {
                throw new RuntimeException("检测到异常");
            }

            if (yesAction != null) {
                ShellUtils.executePoint(yesAction);
            }
        }

        ShellUtils.sleepTime();

        loginPoint = OpenCvUtils.findLoginAccountBtn();

        if (loginPoint == null) {
            logger.info("可能正在登录中...");
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
                logger.info("账号输入按钮失败,待处理");
            }

            ShellUtils.execute(adbPath, "shell", "input", "text", "18321295235");

            OpenCvUtils.findAndAction(Action.ENTER_BTN);
        }

        OpenCvUtils.findAndAction(Action.LOGIN_PASSWORD_INPUT);
        ShellUtils.sleepTime(3);

        ShellUtils.execute(false,adbPath, "shell", "input", "text", "1234567809l");

        OpenCvUtils.findAndAction(Action.ENTER_BTN);

        OpenCvUtils.findAndAction(Action.LOGIN_BTN);

        ShellUtils.sleepTime(15);

    }

}
