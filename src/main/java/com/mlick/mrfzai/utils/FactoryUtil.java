package com.mlick.mrfzai.utils;

import com.mlick.mrfzai.core.AutoStrategy;

import java.awt.dnd.Autoscroll;

/**
 * @author lixiangxin
 * @date 2019/6/28 07:51
 **/
public class FactoryUtil {

  public static void exec(Class t) {
    try {
      AutoStrategy instance = (AutoStrategy) t.newInstance();
      instance.exec();
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
