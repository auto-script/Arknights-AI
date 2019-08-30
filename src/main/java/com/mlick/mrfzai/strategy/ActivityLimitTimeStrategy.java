package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import org.opencv.core.Point;

/**
 * @author lixiangxin
 * @date 2019/8/17 15:58
 **/
public class ActivityLimitTimeStrategy extends AutoStrategy {


    @Override
    public void exec() {

        Point point = OpenCvUtils.loopFindIfNullExit("limit_time_active");
        if (point == null) {
            return;
        }

        OpenCvUtils.loopFindIfNullExit("receive");

        OpenCvUtils.loopFindIfNullExit("exit_btn");

    }
}
