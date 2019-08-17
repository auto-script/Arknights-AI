package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;

/**
 * @author lixiangxin
 * @date 2019/8/17 00:24
 **/
public class TaskStrategy extends AutoStrategy {


    @Override
    public void exec() {

        OpenCvUtils.loopFind("task_index");

        OpenCvUtils.loopFind("day_task");

        OpenCvUtils.loopFind("click_get",true,"exit_btn");

        OpenCvUtils.loopFind("week_task");
        OpenCvUtils.loopFind("click_get",true,"exit_btn");

        OpenCvUtils.loopFind("back_black_btn");
    }
}
