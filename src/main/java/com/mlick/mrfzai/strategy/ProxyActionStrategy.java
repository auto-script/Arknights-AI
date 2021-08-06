package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.AutoProxy;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.FactoryUtil;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.RandomUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mlick.mrfzai.core.Action.START_ACTION2;
import static com.mlick.mrfzai.utils.ShellUtils.sleepTime;

/**
 * @author lixiangxin
 * @date 2019/6/6 13:06
 * 自动刷图界面
 **/
public class ProxyActionStrategy extends AutoStrategy implements AutoProxy {
    private static Logger logger = LoggerFactory.getLogger(ProxyActionStrategy.class);


    /**
     * 需要换购的原石数
     */
    private int energyNum = 0;
    private int numC;

    /**
     * 最大要执行的次数 为0表示不执行
     */
    private int maxCount = Integer.MAX_VALUE;

    private boolean isSkipIndex = true;

    public void disableSkipIndex() {
        this.isSkipIndex = false;
    }

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

        logger.info("总共执行" + maxCount + "次");

        int s = 1;
        do {
            logger.info("开始第" + s + "次执行");
            try {
                loopExec(s);
            } catch (Exception e) {
                System.err.println(e.toString());
                break;
            }

            logger.info("剩余执行源石" + numC + "个");
            if (maxCount < 10000) {
                logger.info("剩余执行" + (maxCount - s) + "次");
            }
            logger.info("第" + s + "次执行完毕");
            s++;
        } while (s <= maxCount);

        logger.info("任务执行完毕，一共" + (s - 1) + "次执行");

//        调回首页首页
        if (isSkipIndex) {
            FactoryUtil.exec(JumpChapterStrategy.INDEX);
        }
    }

    private volatile int waitTime = 0;

    public void setWaitTime(int wt){
        this.waitTime = wt;
    }

    @Override
    public void loopExec(int n) {
        // 开始行动 1
        logger.info("开始行动 1");

        Point point = OpenCvUtils.findProxyStartAction();

        if (point == null) {
            throw new RuntimeException("Not Find 开始行动 1");
        }

        sleepTime(RandomUtils.getRandom(2, 3));

        // 开始行动 2
        logger.info("开始行动 2");
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
        logger.info("等待战斗结束...");

        if (waitTime == 0){
            waitTime = RandomUtils.getRandom(100, 120);
        }

        sleepTime(waitTime);

        // 可能正在战斗
        Point image = OpenCvUtils.findImage(Action.JIE_GUAN_ZUO_ZHAN);
        while (image != null) {
//            System.out.println("战斗任在继续...");
            sleepTime(false, 10);
            image = OpenCvUtils.findImage(Action.JIE_GUAN_ZUO_ZHAN);
        }

        // 退出结算页面
        logger.info("退出结算页面");
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
                    logger.info("检测到 等级升级界面");
                }
            } else {
                logger.info("检测到 出现失误界面");
            }

            sleepTime(RandomUtils.getRandom(5, 8));

            point = OpenCvUtils.findEndAction(1);
        }

        sleepTime(RandomUtils.getRandom(5, 10));
    }


    // 处理没有理智的情况
    private void processNoWit() {
        logger.info("处理可能没理智的情况");
        if (energyNum == 0) {
            logger.info("所配策略，不允许兑换源石");
            maxCount = 0;
            return;
        }

        if (numC <= 0) {
            logger.info("检测到已经兑换了" + energyNum + "次源石了");
            maxCount = 0;
            return;
        }

        numC--;

        Point image = OpenCvUtils.findImage(Action.YUAN_SHI);
        if (image != null) {
            logger.info("正在准备消耗一个源石,恢复智力...");
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