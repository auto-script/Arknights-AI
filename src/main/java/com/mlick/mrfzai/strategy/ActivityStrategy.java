package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;

/**
 * @author lixiangxin
 * @date 2019/8/17 15:58
 **/
public class ActivityStrategy extends AutoStrategy {


    @Override
    public void exec() {

        OpenCvUtils.loopFind("limit_time_active",true);

        OpenCvUtils.loopFind("receive", true);

        OpenCvUtils.loopFind("exit_btn", true);

    }
}
