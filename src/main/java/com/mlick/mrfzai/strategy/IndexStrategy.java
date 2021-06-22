package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

import static com.mlick.mrfzai.core.Action.EXIT_1;

/**
 * @author lixiangxin
 * @date 2019/6/11 00:18
 * 首页 策略
 **/
public class IndexStrategy extends AutoStrategy {
    @Override
    public void exec() {
        OpenCvUtils.loopFindIfNullExit(EXIT_1);
    }


}
