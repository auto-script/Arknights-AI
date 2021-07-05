package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.RandomUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

import java.time.LocalDateTime;

/**
 * @author lixiangxin
 * @date 2019/7/11 11:29
 **/
public class JumpChapterStrategy extends AutoStrategy {

    private TYPE type = TYPE.MONEY;

    public static JumpChapterStrategy INDEX = new JumpChapterStrategy(TYPE.INDEX);
    public static JumpChapterStrategy MONEY = new JumpChapterStrategy(TYPE.MONEY);
    public static JumpChapterStrategy EXPERIENCE = new JumpChapterStrategy(TYPE.EXPERIENCE);
    public static JumpChapterStrategy BUILDING = new JumpChapterStrategy(TYPE.BUILDING);

    public static AutoStrategy type(TYPE type) {
        return new JumpChapterStrategy(type);
    }

    private enum TYPE {
        INDEX,
        MONEY,
        EXPERIENCE,
        BUILDING
    }

    public JumpChapterStrategy(TYPE i) {
        super();
        this.type = i;
    }

    @Override
    public void exec() {
        ShellUtils.sleepTime();
        switch (type) {
            case INDEX:
                OpenCvUtils.findAndAction(Action.HOME);
                ShellUtils.sleepTime(1);
                OpenCvUtils.findAndAction(Action.HOME_INDEX);
                break;
            case EXPERIENCE:// 跳转到经验
                execJump(Action.FIGHT_EXPERIENCE, Action.LS_5);
                break;
            case MONEY:// 跳转到龙门币
                execJump(Action.FIGHT_MONEY, Action.CE_5);
                break;
            case BUILDING:// 跳转到基站
//                execJump("fight_money.png", "CE-5.png");
                break;
            default:
                break;
        }
    }

    private void execJump(Action s, Action s2) {
        Point indexAction = OpenCvUtils.retryExec(Action.INDEX_TERMINAL, 3);
        if (indexAction == null) {
            System.err.println("没有发现首页,待处理");
            return;
        }

        Point point = OpenCvUtils.retryExec(Action.GOODS, 3);
        if (point == null) {
            throw new RuntimeException("资源物资未找到");
        }

        point = OpenCvUtils.findAndAction(s);
        if (point == null) {
            return;
        }

        if (s2 == null) {
            return;
        }

        ShellUtils.sleepTime();

        Point exec = OpenCvUtils.retryExec(s2, 3);

        if (exec == null) {
            throw new RuntimeException(s2.getName() + "未找到");
        }
    }

    private int loopFindAction(String s, int i) {
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



    public static JumpChapterStrategy getType() {

        switch (LocalDateTime.now().toLocalDate().getDayOfWeek()) {
            case MONDAY:
            case WEDNESDAY:
            case FRIDAY:
                return JumpChapterStrategy.EXPERIENCE;
            default:
                return JumpChapterStrategy.MONEY;
        }
    }

}
