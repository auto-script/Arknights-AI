package com.mlick.mrfzai;

import com.mlick.mrfzai.strategy.*;
import com.mlick.mrfzai.utils.FactoryUtil;
import com.mlick.mrfzai.utils.NoxUtils;
import com.mlick.mrfzai.utils.ShellUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static com.mlick.mrfzai.core.Constants.isVmRunning;
import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/8/13 07:11
 **/
public class MainExec {


    /**
     * 记一个坑： cmd 执行时不能使用 String ... 这种泛型格式
     *
     * @param args
     */
    public static void main(String[] args) {

        adbPath = String.format(adbPath, "adb1036");

        isVmRunning = NoxUtils.isRunning();

        if (!isVmRunning) {
            NoxUtils.startUp();
            ShellUtils.sleepTime(3);
            ShellUtils.sleepTime(50);

            FactoryUtil.exec(LauncyStragery.class);

            ShellUtils.sleepTime(10);
        }

        MainAuto.getAdb(args);

        FactoryUtil.exec(LoginStrategy.class);

        FactoryUtil.exec(IndexStrategy.class);

        FactoryUtil.exec(ActivityLimitTimeStrategy.class);

        FactoryUtil.exec(EmailStrategy.class);
        FactoryUtil.exec(TaskStrategy.class);
        FactoryUtil.exec(PurchasingStrategy.class);
        FactoryUtil.exec(BuildStrategy.class);


        /** 常规刷图 */
//    FactoryUtil.exec(new JumpChapterStrategy(getType()));
//    FactoryUtil.exec(ProxyActionStrategy.class);

        /** 刷活动图*/
//    FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-7"));
//    FactoryUtil.exec(ProxyActionStrategy.count(5));
        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-8"));
        FactoryUtil.exec(ProxyActionStrategy.class);

        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-F4"));
        FactoryUtil.exec(ProxyActionStrategy.class);

        FactoryUtil.exec(TaskStrategy.class);
        FactoryUtil.exec(PurchasingStrategy.class);
        FactoryUtil.exec(BuildStrategy.class);

        FactoryUtil.exec(ExitStragery.class);

    }

    private static int getType() {

        DayOfWeek dayOfWeek = LocalDateTime.now().toLocalDate().getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
            case WEDNESDAY:
            case FRIDAY:
                return 1;
            default:
                return 2;
        }

    }
}
