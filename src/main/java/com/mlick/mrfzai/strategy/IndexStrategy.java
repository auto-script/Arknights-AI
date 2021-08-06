package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mlick.mrfzai.core.Action.*;

/**
 * @author lixiangxin
 * @date 2019/6/11 00:18
 * 首页 策略
 **/
public class IndexStrategy extends AutoStrategy {
    private static Logger logger = LoggerFactory.getLogger(IndexStrategy.class);

    @Override
    public void exec() {

        ShellUtils.sleepTime(10);

        Point point = OpenCvUtils.loopFind(YES_2, 3);

        if (point == null) {
            OpenCvUtils.loopFind(DAY_TASK_GOODS, 3);
        }

        ShellUtils.sleepTime(3);

        OpenCvUtils.retryExec(EXIT_1, 3);

        OpenCvUtils.loopFindIfNullExit(EXIT_1);
    }


}
