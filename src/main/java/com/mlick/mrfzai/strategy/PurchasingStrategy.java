package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;

/**
 * @author lixiangxin
 * @date 2019/8/17 01:31
 **/
public class PurchasingStrategy extends AutoStrategy {
    @Override
    public void exec() {

        OpenCvUtils.loopFind("caigou_center");

        OpenCvUtils.loopFind("jiaoyisuo",5);

        OpenCvUtils.loopFindIfNullExit("get_credit");

        OpenCvUtils.loopFind("back_black_btn");

    }
}
