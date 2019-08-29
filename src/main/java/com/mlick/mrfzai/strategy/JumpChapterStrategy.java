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

    private int stage = 2;

    public JumpChapterStrategy() {
    }


    public JumpChapterStrategy(int i) {
        super();
        this.stage = i;
    }

    @Override
    public void exec() {

        Point andAction = OpenCvUtils.findAndAction("home_fight.png");
        if (stage != 0 && andAction == null) {
            OpenCvUtils.findAndAction("home.png");
            ShellUtils.sleepTime(3);
            OpenCvUtils.findAndAction("fight.png");
            ShellUtils.sleepTime(8);
        }

        switch (stage) {

            case 0:// 首页
                OpenCvUtils.findAndAction("home.png");
                ShellUtils.sleepTime(3);
                OpenCvUtils.findAndAction("home_index.png");
                break;
            case 1:// 跳转到经验
                execJump("fight_experience.png", "LS-5.png");
                break;

            case 2:// 跳转到龙门币
                execJump("fight_money.png", "CE-5.png");
                break;

            case 3:// 跳转到基站
                execJump("fight_money.png", "CE-5.png");
                break;

            default:
                break;
        }

    }

    private void execJump(String s, String s2) {
        loopFindAction("goods.png", 3);

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
