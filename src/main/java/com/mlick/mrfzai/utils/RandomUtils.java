package com.mlick.mrfzai.utils;

import java.util.Random;

/**
 * @author lixiangxin
 * @date 2019/6/6 13:08
 **/
public class RandomUtils {

  private RandomUtils() {
  }

  public static int getRandom(int n, int m) {
    Random random = new Random(System.currentTimeMillis());
    return random.nextInt(m - n + 1) + n;
  }


}
