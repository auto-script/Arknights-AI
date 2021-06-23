import com.mlick.mrfzai.MainAuto;
import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.core.Constants;
import com.mlick.mrfzai.strategy.*;
import com.mlick.mrfzai.utils.FactoryUtil;
import com.mlick.mrfzai.utils.NoxUtils;
import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.junit.Before;
import org.junit.Test;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import static com.mlick.mrfzai.utils.RandomUtils.getRandom;
import static com.mlick.mrfzai.utils.ShellUtils.*;

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
        execute(adbPath, "shell", getTapPhone(1128, 660));
        execute(adbPath, "shell", getTapPhone(1128, 660));

        // 开始行动 2
        System.out.println("开始行动 2");
        execute(adbPath, "shell", getTapPhone(1128, 500));

        int waitMinute = getRandom(120, 180);

        // 等待 战斗结束
        sleepTime(waitMinute);

        // 可能出现 失误
//    execute(adbPath, "shell", getTapPhone(1155, 465));
//    sleepTime(3);

        // 退出结算页面
        System.out.println("退出结算页面");
        execute(adbPath, "shell", getTapPhone(1128, 660));
    }

    @Before
    public void init() {
        adbPath = String.format(adbPath, "adb1036");
    }

    @Test
    public void screenCap() {
        ShellUtils.screenCap();
    }

    @Test
    public void startAppTest() {
        FactoryUtil.exec(StartAppStrategy.class);
    }

    @Test
    public void loginTest() {
        FactoryUtil.exec(LoginStrategy.class);
    }

    @Test
    public void testAutoLogin() {
        new LoginStrategy().autoLogin();
    }


    @Test
    public void indexTest() {
        FactoryUtil.exec(IndexStrategy.class);
    }

    //@Test
    public void acceptEmail() {
        AutoStrategy autoStrategy = new EmailStrategy();
        autoStrategy.exec();
    }

    //@Test
    public void devices() throws IOException {
        ShellUtils.executeByResult(adbPath, "connect 106.12.196.97");
        ShellUtils.screenCap();
    }

    @Test
    public void testBuild() {
        FactoryUtil.exec(BuildStrategy.class);
    }


    @Test
    public void testExit() {
        FactoryUtil.exec(ExitNoxStrategy.class);
    }

    @Test
    public void proxyStrategy() {
        FactoryUtil.exec(new JumpChapterStrategy(2));

        ProxyActionStrategy strategy = new ProxyActionStrategy();
        strategy.setMaxCount(4);
        strategy.setEnergy(0);
        FactoryUtil.exec(strategy);
    }

    //@Test
    public void allAuto() {
        FactoryUtil.exec(LoginStrategy.class);
        FactoryUtil.exec(IndexStrategy.class);
    }


    //@Test
    public void dayTask() {

        //处理可能出现其它情况 随机点击一处屏幕
        ShellUtils.tapPhone(255, 255);

        allAuto();

        FactoryUtil.exec(new JumpChapterStrategy(1));
//    FactoryUtil.exec(new ProxyActionStrategy(10));
//
//    FactoryUtil.exec(new JumpChapterStrategy(2));
        FactoryUtil.exec(ProxyActionStrategy.class);
    }

    //@Test
    public void dayTask2() {
        allAuto();
        FactoryUtil.exec(BuildStrategy.class);
    }


    @Test
    public void IWantMoreMoney() {
//        FactoryUtil.exec(StartNoxStrategy.class);
//        startAppTest();
//        loginTest();
//        indexTest();
//        FactoryUtil.exec(JumpChapterStrategy.class);

        try {
            ProxyActionStrategy strategy = new ProxyActionStrategy();
            int en = 10;
            strategy.setEnergy(en);
            strategy.setMaxCount(4 * en);
            FactoryUtil.exec(strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            FactoryUtil.exec(ExitNoxStrategy.class);
        }
    }

    //@Test
    public void IWantExperience() {
        allAuto();
        FactoryUtil.exec(new JumpChapterStrategy(1));
        FactoryUtil.exec(ProxyActionStrategy.class);
    }

    /**
     * 固定刷 某件物品
     */
    //@Test
    public void dayTask4() {
        FactoryUtil.exec(new ProxyActionStrategy(100));
    }


    //@Test
    public void t1() {
        ShellUtils.swipePhone(255, 255, 200, 255);
    }


    //@Test
    public void t2() {

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

    //@Test
    public void t3() {

        FactoryUtil.exec(ExitNoxStrategy.class);

//    ShellUtils.screenCap();
//
//    ShellUtils.sleepTime(5);
//
//    FactoryUtil.exec(LauncyStragery.class);


//    allAuto();
//
//    FactoryUtil.exec(new JumpChapterStrategy(1));
//    FactoryUtil.exec(ProxyActionStrategy.class);
    }


    //@Test
    public void t5() {


        UUID.randomUUID().toString();

        String u = "W82yxMQruqWRM6U2ejypBUSb4z8yz7+TacvPTvGK1i7Q+/+hdA" +
                "+Fnr84bk8IHEgCVjUelblcRKrGrCG6C0Aw7CbuYWC4Zvvtx8aqk9y527rz7XNOim8XdgvzgCurhyn65gwv6nK/wZ1RSvKCwHSu" +
                "/dm5HhbRgkHVyWxq4kEiwNqIYGTSEhQrOcKe9bybbuf43tHm8VcDYnUQJtlXUn2Nqh" +
                "+mUrdBjHBCzcgM0FxOigU6lxlyIGn2chM6UMZhvietI5pl/gOaD4rJhlMM621lQWEK/QF+DvPjI7vNZlriClzc" +
                "+xnrfqgGtKaNwv2ZP43Y9F5/jxEjNR/B4o6tGtyrig==";

        String p = "Test1234";

        System.out.println(Base64.getEncoder().encodeToString(p.getBytes()));

        assert u.equals(Base64.getEncoder().encodeToString(p.getBytes()));

    }


    //@Test
    public void taskTest() {
        FactoryUtil.exec(TaskStrategy.class);
    }

    //@Test
    public void taskPurchasing() {
        FactoryUtil.exec(PurchasingStrategy.class);
    }


    //@Test
    public void testActivity() {
        FactoryUtil.exec(ActivityLimitTimeStrategy.class);
    }

    @Test
    public void testJumpHome() {
        FactoryUtil.exec(new JumpChapterStrategy(2));
    }


    @Test
    public void tA2() {
        /** 刷活动图*/
        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-7"));
        FactoryUtil.exec(ProxyActionStrategy.energy(2));
        FactoryUtil.exec(ProxyActionStrategy.count(15));

        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-8"));
        FactoryUtil.exec(ProxyActionStrategy.class);

        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-F4"));
        FactoryUtil.exec(ProxyActionStrategy.class);
    }


    @Test
    public void startUp() throws InterruptedException {
        NoxUtils.startUp();
        Thread.currentThread().join();
    }

    @Test
    public void getDevices() {
        MainAuto.getAdb(new String[]{});
    }


    @Test
    public void t6() {
        OpenCvUtils.findAndAction(Action.YES_3);
        ShellUtils.screenCap();
    }


    @Test
    public void t7() {
//        String templateImg = Action.INDEX_TERMINAL.getImg();
        Constants.FILE_TEMP_PATH = "testFile/";

        Path source = Paths.get("D:\\workspace\\github\\Arknights-AI\\testFile\\2021-06-20-180439-screen.png");
//        Path template = Paths.get("D:\\workspace\\github\\Arknights-AI\\resources\\START.png");
//        Path template = Paths.get("D:\\workspace\\github\\Arknights-AI\\resources\\start_1.png");
        Path template = Paths.get("D:\\workspace\\github\\Arknights-AI\\testFile\\login_account_btn5.png");

        String screen = Objects.requireNonNull(source.toFile()).getAbsolutePath();
        System.out.println(screen);

        String path = Objects.requireNonNull(template.toFile()).getAbsolutePath();
        System.out.println(path);

        OpenCvUtils.findImage(screen, path, "Test", 0.65, Imgproc.TM_SQDIFF);
    }

}
