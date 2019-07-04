import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.strategy.*;
import com.mlick.mrfzai.utils.FactoryUtil;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Point;

import java.io.IOException;

import static com.mlick.mrfzai.utils.RandomUtils.getRandom;
import static com.mlick.mrfzai.utils.ShellUtils.*;
import static com.mlick.mrfzai.utils.ShellUtils.sleepTime;

/**
 * @author lixiangxin
 * @date 2019/6/11 17:29
 **/
public class StratrgyTest {


  private static void loopExec() {
    // 开始行动 1
    System.out.println("开始行动 1");

    Point point = OpenCvUtils.findProxyStartAction();

    execute(adbPath, "shell", tapPhone(1128, 660));
    sleepTime(getRandom(5, 8));
    execute(adbPath, "shell", tapPhone(1128, 660));
    sleepTime(getRandom(5, 8));
    execute(adbPath, "shell", tapPhone(1128, 660));
    sleepTime(getRandom(5, 8));

    // 开始行动 2
    System.out.println("开始行动 2");
    execute(adbPath, "shell", tapPhone(1128, 500));

    int waitMinute = getRandom(120, 180);

    // 等待 战斗结束
    sleepTime2(waitMinute);

    // 可能出现 失误
//    execute(adbPath, "shell", tapPhone(1155, 465));
//    sleepTime(3);

    // 退出结算页面
    System.out.println("退出结算页面");
    execute(adbPath, "shell", tapPhone(1128, 660));
    sleepTime(getRandom(10, 20));

  }

  @Before
  public void init() {
    adbPath = String.format(adbPath, "adb1036");
  }

  @Test
  public void screenCap() throws IOException {
    ShellUtils.screenCap();
  }


  @Test
  public void loginTest() {
    AutoStrategy autoStrategy = new com.mlick.mrfzai.strategy.LoginStrategy();
    autoStrategy.exec();
  }

  @Test
  public void indexTest() {
    AutoStrategy autoStrategy = new com.mlick.mrfzai.strategy.IndexStrategy();
    autoStrategy.exec();
  }

  @Test
  public void acceptEmail() {
    AutoStrategy autoStrategy = new com.mlick.mrfzai.strategy.AcceptEmailStrategy();
    autoStrategy.exec();
  }

  @Test
  public void devices() throws IOException {
    ShellUtils.executeByResult(adbPath, "connect 106.12.196.97");
    ShellUtils.screenCap();
  }

  @Test
  public void testBuild(){
    AutoStrategy autoStrategy = new BuildStrategy();
    autoStrategy.exec();
  }

  @Test
  public void testAutoLogin1() {
    OpenCvUtils.findAndAction("input_password.png");

    ShellUtils.execute(adbPath, "shell", "input", "text", "301415926l");

    OpenCvUtils.findAndAction("enter_btn.png");

    OpenCvUtils.findAndAction("login_btn.png");

  }

  @Test
  public void allAuto(){
    FactoryUtil.exec(LauncyStragery.class);
    FactoryUtil.exec(LoginStrategy.class);
    FactoryUtil.exec(IndexStrategy.class);
    FactoryUtil.exec(AcceptEmailStrategy.class);
    FactoryUtil.exec(BuildStrategy.class);
  }
}
