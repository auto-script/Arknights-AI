package com.mlick.mrfzai.strategy;

import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.core.Constants;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.opencv.core.Point;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/6/28 07:59
 **/
public class StartAppStrategy extends AutoStrategy {

    @Override
    public void exec() {

        // 包名 + 启动类名
        ShellUtils.executeByResult(
                adbPath, "shell", "am start -n com.hypergryph.arknights/com.u8.sdk.U8UnityContext");
        ShellUtils.sleepTime(15);

        // 点击开始 按钮
        OpenCvUtils.retryExec(Action.START,2);

        ShellUtils.sleepTime(15);

        // 检测是否有更新操作
        Point nextWhiteAction = OpenCvUtils.findNextWhiteAction();
        if (nextWhiteAction != null) {
            ShellUtils.executePoint(nextWhiteAction);
        } else if (OpenCvUtils.findLoginAccountBtn() != null) {
            System.out.println("成功启动");
            return;
        }

        OpenCvUtils.findAndAction(Action.START);
        System.out.println("启动有异常,待处理!");

        ShellUtils.sleepTime(5);
    }
}
