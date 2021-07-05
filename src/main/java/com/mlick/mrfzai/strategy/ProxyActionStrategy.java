package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.AutoProxy;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.*;
import org.opencv.core.Point;

import static com.mlick.mrfzai.core.Action.START_ACTION2;
import static com.mlick.mrfzai.utils.ShellUtils.sleepTime;

/**
 * @author lixiangxin
 * @date 2019/6/6 13:06
 * 自动刷图界面
 **/
public class ProxyActionStrategy extends AutoStrategy implements AutoProxy {

    /**
     * 需要换购的原石数
     */
    private int energyNum = 0;
    private int numC;

    /**
     * 最大要执行的次数 为0表示不执行
     */
    private int maxCount = Integer.MAX_VALUE;

    public void setEnergy(int num) {
        this.energyNum = num;
    }

    public static ProxyActionStrategy energy(int num) {
        ProxyActionStrategy proxyActionStrategy = new ProxyActionStrategy();
        proxyActionStrategy.setEnergy(num);
        return proxyActionStrategy;
    }

    public static ProxyActionStrategy maxCount(int maxCount) {
        ProxyActionStrategy proxyActionStrategy = new ProxyActionStrategy();
        proxyActionStrategy.setMaxCount(maxCount);
        return proxyActionStrategy;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public ProxyActionStrategy() {
    }

    public ProxyActionStrategy(int maxCount) {
        this.maxCount = maxCount;
    }


    @Override
    public void exec() {
        numC = energyNum;
        OpenCvUtils.findAndAction(Action.UN_PROXY);

        int s = 1;
        do {
            System.out.println("第" + s + "次执行");
            try {
                loopExec(s);
            } catch (Exception e) {
                System.err.println(e.toString());
                break;
            }
            s++;
        } while (s <= maxCount);


        System.out.println("任务执行完毕，一共" + s + "次执行");
//        执行完成后 会首页
        FactoryUtil.exec(JumpChapterStrategy.INDEX);
    }

    @Override
    public void loopExec(int n) {
        // 开始行动 1
        System.out.println("开始行动 1");

        Point point = OpenCvUtils.findProxyStartAction();

        if (point == null) {
            throw new RuntimeException("Not Find 开始行动 1");
        }

        sleepTime(RandomUtils.getRandom(2, 3));

        // 开始行动 2
        System.out.println("开始行动 2");
        point = OpenCvUtils.retryExec(START_ACTION2, 2);

        if (point == null) {
            ShellUtils.sleepTime(2);
            System.err.println("未找到 开始行动 2");
            ShellUtils.sleepTime(2);

            processNoWit();
        }

        if (maxCount == 0) {
            // 智力不够
            OpenCvUtils.findAndAction(Action.CLOSE_4);
            throw new RuntimeException("Not Wit");
        }

        // 等待 战斗结束
        System.out.println("等待战斗结束...");
        sleepTime(RandomUtils.getRandom(100, 120));

        // 可能正在战斗
        Point image = OpenCvUtils.findImage(Action.JIE_GUAN_ZUO_ZHAN);
        while (image != null) {
            System.out.println("战斗任在继续...");
            sleepTime(10);
            image = OpenCvUtils.findImage(Action.JIE_GUAN_ZUO_ZHAN);
        }


        // 退出结算页面
        System.out.println("退出结算页面");
        point = OpenCvUtils.findEndAction(2);

        for (int i = 0; i < 6 && point == null; i++) {

            ShellUtils.sleepTime(2);
            System.err.println("没有找到结算页面");
            ShellUtils.sleepTime(2);

            // 可能出现失误 重试 继续结算
            point = OpenCvUtils.findContinueAction();
            if (point == null) { // 可能是 等级升级
                point = OpenCvUtils.findLevelUpAction();
                Point rwPoint = OpenCvUtils.findImage(Action.RECOVER_WIT);
                if (point != null || rwPoint != null) {
                    ShellUtils.executePoint(point, true);
                    System.out.println("检测到 等级升级界面");
                }
            } else {
                System.out.println("检测到 出现失误界面");
            }

            sleepTime(RandomUtils.getRandom(5, 8));

            point = OpenCvUtils.findEndAction(1);
        }

        sleepTime(RandomUtils.getRandom(5, 10));
    }


    // 处理没有理智的情况
    private void processNoWit() {
        System.out.println("处理可能没理智的情况");
        if (energyNum == 0) {
            System.out.println("所配策略，不允许兑换源石");
            maxCount = 0;
            return;
        }

        if (numC <= 0) {
            System.out.println("检测到已经兑换了" + energyNum + "次源石了");
            maxCount = 0;
            return;
        }

        numC--;

        Point image = OpenCvUtils.findImage(Action.YUAN_SHI);
        if (image != null) {
            System.out.println("正在准备消耗一个源石,恢复智力...");
            System.out.println("剩余:"+numC);
        }

        OpenCvUtils.findAndAction(Action.YES_4);

        ShellUtils.sleepTime(3);
        Point point = OpenCvUtils.findProxyStartAction();
        if (point == null) {
            throw new RuntimeException("Not Find 开始行动 1");
        }

        ShellUtils.sleepTime(3);
        point = OpenCvUtils.findAndAction(Action.START_ACTION2);
        if (point == null) {
            throw new RuntimeException("Not Find 开始行动 2");
        }
    }


}