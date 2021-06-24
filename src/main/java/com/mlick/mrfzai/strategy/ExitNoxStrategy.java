package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.utils.NoxUtils;
import com.mlick.mrfzai.utils.ShellUtils;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/7/12 16:30
 **/
public class ExitNoxStrategy extends AutoStrategy {

    @Override
    public void exec() {
        ShellUtils.executeByResult(
                adbPath, "shell", "am force-stop com.hypergryph.arknights");

        NoxUtils.shutDown();

        System.out.println("Exit System!!!");

        System.exit(0);
        
    }

}
