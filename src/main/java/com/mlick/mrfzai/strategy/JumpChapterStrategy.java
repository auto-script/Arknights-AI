package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
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

    private int stage = 2;
    private TYPE type = TYPE.MONEY;

    enum TYPE {
        INDEX,
        MONEY,
        EXPERIENCE,
        BUILDING
    }

    public JumpChapterStrategy() {
    }


    public JumpChapterStrategy(int i) {
        super();
        this.stage = i;
    }

    public JumpChapterStrategy(TYPE i) {
        super();
        this.type = i;
    }

    @Override
    public void exec() {
        Point indexAction = OpenCvUtils.findAndAction(Action.INDEX_TERMINAL);
        if (indexAction == null) {
            System.err.println("没有发现在首页,待处理");
            return;
        }

        switch (type) {
            case INDEX:// 首页
                OpenCvUtils.findAndAction("home.png");
                ShellUtils.sleepTime(3);
                OpenCvUtils.findAndAction("home_index.png");
                break;
            case EXPERIENCE:// 跳转到经验
                execJump("fight_experience.png", "LS-5.png");
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

    private void execJump(String s, String s2) {
        Point point = OpenCvUtils.findAndAction(Action.GOODS);
        if (point == null) {
            return;
        }

        int i = loopFindAction(s, 1);
        if (i == 0) {
            System.out.println("拖动...");
            ShellUtils.swipePhone(255, 255, 150, 255);
            i = loopFindAction(s, 1);
        }
        if (i == 0) {
            loopFindAction("fight_experience", 1);
            loopFindAction("LS-5.png", 1);
        } else {
            loopFindAction(s2, 1);
        }
    }

    private void execJump(Action s, Action s2) {
        Point point = OpenCvUtils.retryExec(Action.GOODS, 3);
        if (point == null) {
            throw new RuntimeException("资源物资未找到");
        }

        point = OpenCvUtils.findAndAction(s);
        if (point == null) {
            return;
        }

        ShellUtils.sleepTime();

        OpenCvUtils.findAndAction(s2);
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

}
