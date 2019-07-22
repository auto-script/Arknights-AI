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

    execute(adbPath, "shell", getTapPhone(1128, 660));
    sleepTime(getRandom(5, 8));
    execute(adbPath, "shell", getTapPhone(1128, 660));
    sleepTime(getRandom(5, 8));
    execute(adbPath, "shell", getTapPhone(1128, 660));
    sleepTime(getRandom(5, 8));

    // 开始行动 2
    System.out.println("开始行动 2");
    execute(adbPath, "shell", getTapPhone(1128, 500));

    int waitMinute = getRandom(120, 180);

    // 等待 战斗结束
    sleepTime2(waitMinute);

    // 可能出现 失误
//    execute(adbPath, "shell", getTapPhone(1155, 465));
//    sleepTime(3);

    // 退出结算页面
    System.out.println("退出结算页面");
    execute(adbPath, "shell", getTapPhone(1128, 660));
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
    FactoryUtil.exec(LoginStrategy.class);
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
  public void testBuild() {
    FactoryUtil.exec(BuildStrategy.class);
  }

  @Test
  public void testAutoLogin() {
    new LoginStrategy().autoLogin();
  }

  @Test
  public void exit(){
    FactoryUtil.exec(ExitStragery.class);
  }

  @Test
  public void proxyStrategy(){
    FactoryUtil.exec(ProxyActionStrategy.class);
  }

  @Test
  public void allAuto() {
    FactoryUtil.exec(LoginStrategy.class);
    FactoryUtil.exec(IndexStrategy.class);
  }


  @Test
  public void dayTask(){

    //处理可能出现其它情况 随机点击一处屏幕
    ShellUtils.tapPhone(255, 255);
    ShellUtils.sleepTime(3);

    allAuto();

    FactoryUtil.exec(new JumpChapterStrategy(1));
//    FactoryUtil.exec(new ProxyActionStrategy(10));
//
//    FactoryUtil.exec(new JumpChapterStrategy(2));
    FactoryUtil.exec(ProxyActionStrategy.class);
  }

  @Test
  public void dayTask2(){
    allAuto();
    FactoryUtil.exec(BuildStrategy.class);
  }

  @Test
  public void IWantMoreMoney(){

    //处理可能出现其它情况 随机点击一处屏幕
    ShellUtils.tapPhone(255, 255);
    ShellUtils.sleepTime(3);

//    allAuto();
    FactoryUtil.exec(new JumpChapterStrategy(2));
    FactoryUtil.exec(ProxyActionStrategy.class);
  }

  @Test
  public void IWantExperience(){
//    allAuto();
    FactoryUtil.exec(new JumpChapterStrategy(1));
    FactoryUtil.exec(ProxyActionStrategy.class);
  }

  /**
   * 固定刷 某件物品
   */
  @Test
  public void dayTask4(){
    FactoryUtil.exec(new ProxyActionStrategy(5));
  }




  @Test
  public void t1(){
    ShellUtils.sleepTime(5);
    ShellUtils.swipePhone(255, 255, 200, 255);
  }


  @Test
  public void t2(){

//    Point andAction = OpenCvUtils.findAndAction("home_fight.png");
//    if (andAction == null) {
//
//      OpenCvUtils.findAndAction("home.png");
//      ShellUtils.sleepTime(3);
//      OpenCvUtils.findAndAction("fight.png");
//      ShellUtils.sleepTime(8);
//    }
//
//    OpenCvUtils.findAndAction("goods.png");
//
//    ShellUtils.swipePhone(255, 255, 155, 255);

    Point point = OpenCvUtils.findImage("fight_money.png");
    ShellUtils.executePoint(point);

    ShellUtils.screenCap();

  }

  @Test
  public void t3(){

    FactoryUtil.exec(ExitStragery.class);
    FactoryUtil.exec(LauncyStragery.class);

    allAuto();

    ShellUtils.screenCap();
  }


}
