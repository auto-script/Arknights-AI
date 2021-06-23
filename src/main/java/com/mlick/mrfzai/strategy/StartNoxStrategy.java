package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.NoxUtils;
import com.mlick.mrfzai.utils.ShellUtils;

import static com.mlick.mrfzai.core.Constants.isVmRunning;

/**
 * @author lixiangxin
 * @date 2021/6/22 11:53
 **/
public class StartNoxStrategy extends AutoStrategy {
    @Override
    public void exec() {
        isVmRunning = NoxUtils.isRunning();
        if (!isVmRunning) {
            NoxUtils.startUp();
            ShellUtils.sleepTime(66);
        }

    }
}
