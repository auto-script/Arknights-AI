package com.mlick.mrfzai;

import com.mlick.mrfzai.strategy.*;
import com.mlick.mrfzai.utils.FactoryUtil;
import com.mlick.mrfzai.utils.NoxUtils;
import com.mlick.mrfzai.utils.ShellUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/8/13 07:11
 **/
public class MainExec {

    public static String executeCmd(String command) throws IOException {
        System.out.println("Execute command : " + command);
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("cmd /c " + command);
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder build = new StringBuilder();
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            build.append(line);
        }
        return build.toString();
    }

    /**
     * 记一个坑： cmd 执行时不能使用 String ... 这种泛型格式
     *
     * @param args
     */
    public static void main(String[] args) {

        adbPath = String.format(adbPath, "adb1036");

        if (!NoxUtils.isRunning()) {
            NoxUtils.startUp();
            ShellUtils.sleepTime(50);
        }

        FactoryUtil.exec(LoginStrategy.class);

        FactoryUtil.exec(IndexStrategy.class);

        FactoryUtil.exec(ActivityStrategy.class);

        FactoryUtil.exec(EmailStrategy.class);
        FactoryUtil.exec(TaskStrategy.class);
        FactoryUtil.exec(PurchasingStrategy.class);
        FactoryUtil.exec(BuildStrategy.class);

        FactoryUtil.exec(new JumpChapterStrategy(getType()));
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
