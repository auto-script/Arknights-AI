package com.mlick.mrfzai.core;

import com.mlick.mrfzai.strategy.LoginStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lixiangxin
 * @date 2019/6/10 23:55
 **/
public abstract class AutoStrategy {
    protected static Logger log = LoggerFactory.getLogger(AutoStrategy.class);

    /**
     * 执行 策略
     */
    public abstract void exec();
}
