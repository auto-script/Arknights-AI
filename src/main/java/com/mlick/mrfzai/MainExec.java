package com.mlick.mrfzai;

import com.mlick.mrfzai.strategy.*;
import com.mlick.mrfzai.utils.FactoryUtil;
import com.mlick.mrfzai.utils.ShellUtils;

import java.time.LocalDateTime;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/8/13 07:11
 **/
public class MainExec {


    /**
     * 记一个坑： cmd 执行时不能使用 String ... 这种泛型格式
     */
    public static void main(String[] args) {

        adbPath = String.format(adbPath, "adb1036");

        MainAuto.getAdb(args);

        FactoryUtil.exec(StartNoxStrategy.class);

        FactoryUtil.exec(StartAppStrategy.class);

        ShellUtils.sleepTime(10);

        FactoryUtil.exec(LoginStrategy.class);

        FactoryUtil.exec(IndexStrategy.class);

        FactoryUtil.exec(ActivityLimitTimeStrategy.class);

        FactoryUtil.exec(EmailStrategy.class);
        FactoryUtil.exec(TaskStrategy.class);
        FactoryUtil.exec(PurchasingStrategy.class);
        FactoryUtil.exec(BuildStrategy.class);


        /** 常规刷图 */
        FactoryUtil.exec(new JumpChapterStrategy(getType()));
        FactoryUtil.exec(ProxyActionStrategy.count(3));

        /** 刷活动图*/
//        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-7"));
//        FactoryUtil.exec(ProxyActionStrategy.energy(2));
//        FactoryUtil.exec(ProxyActionStrategy.count(15));
//
//        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-8"));
//        //FactoryUtil.exec(ProxyActionStrategy.energy(1));
//        FactoryUtil.exec(ProxyActionStrategy.class);
//
//        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-F4"));
//        FactoryUtil.exec(ProxyActionStrategy.class);

        /** 常规刷图 */
//        FactoryUtil.exec(new JumpChapterStrategy(getType()));
//        FactoryUtil.exec(ProxyActionStrategy.class);

        FactoryUtil.exec(TaskStrategy.class);
        FactoryUtil.exec(PurchasingStrategy.class);
        FactoryUtil.exec(BuildStrategy.class);

        FactoryUtil.exec(ExitNoxStrategy.class);
    }

    private static int getType() {

        switch (LocalDateTime.now().toLocalDate().getDayOfWeek()) {
            case MONDAY:
            case WEDNESDAY:
            case FRIDAY:
                return 1;
            default:
                return 2;
        }

    }
}
