package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

/**
 * @author lixiangxin
 * @date 2019/6/11 00:30
 * 接受 邮件
 **/
public class EmailStrategy extends AutoStrategy {

    @Override
    public void exec() {

        OpenCvUtils.loopFind("email_btn.png");

        OpenCvUtils.loopFind("email_accept_all.png");

        OpenCvUtils.findAndAction("next_btn.png");

        OpenCvUtils.loopFind("back_white_btn.png");


//    System.out.println("删除所有已读邮件");
//    ShellUtils.executePoint(OpenCvUtils.findDeleteEmail());

    }

}
