package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import org.opencv.core.Point;

/**
 * @author lixiangxin
 * @date 2019/8/17 00:24
 **/
public class TaskStrategy extends AutoStrategy {


    @Override
    public void exec() {

        OpenCvUtils.loopFind("task_index");

        Point point = OpenCvUtils.loopFind("day_task", 2);
        if (point != null && OpenCvUtils.findImage("baochou_end") == null) {
            OpenCvUtils.loopFind("click_get", true, "exit_btn");
        }

        point = OpenCvUtils.loopFind("hu_dong_task", 2);
        if (point != null && OpenCvUtils.findImage("baochou_end") == null) {
            OpenCvUtils.loopFind("click_get", true, "exit_btn");
        }

        point = OpenCvUtils.loopFind("week_task", 2);
        if (point != null && OpenCvUtils.findImage("baochou_end") == null) {
            OpenCvUtils.loopFind("click_get", true, "exit_btn");
        }

        OpenCvUtils.loopFind("back_black_btn");
    }
}
