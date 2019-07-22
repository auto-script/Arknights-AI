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
public class AcceptEmailStrategy extends AutoStrategy {

  @Override
  public void exec() {

    Point point = OpenCvUtils.findEmail();
    ShellUtils.executePoint(point);

    System.out.println("接受所有邮件");
    ShellUtils.executePoint(OpenCvUtils.findAcceptEmail());


    while ((point = OpenCvUtils.findNextAction()) != null) {
      System.out.println("确定接受");
      ShellUtils.executePoint(point);
    }

//    System.out.println("删除所有已读邮件");
//    ShellUtils.executePoint(OpenCvUtils.findDeleteEmail());

    System.out.println("返回");
    ShellUtils.executePoint(OpenCvUtils.findBackBtn());


  }

}
