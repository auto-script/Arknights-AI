package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.NoxUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.mlick.mrfzai.core.Constants.isVmRunning;

/**
 * @author lixiangxin
 * @date 2021/6/22 11:53
 **/
public class StartNoxStrategy extends AutoStrategy {
    private static Logger logger = LoggerFactory.getLogger(StartNoxStrategy.class);
    @Override
    public void exec() {
        isVmRunning = NoxUtils.isRunning();

        if (isVmRunning) {
            logger.info("Nox.exe is Running");
            return;
        }

        NoxUtils.startUp();
        logger.info("启动模拟器Nox...[66s]");
        ShellUtils.sleepTime(false,66);

    }
}
