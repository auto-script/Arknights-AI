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
import org.opencv.imgproc.Imgproc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

import static com.mlick.mrfzai.utils.ShellUtils.adbPath;

/**
 * @author lixiangxin
 * @date 2019/6/11 17:29
 **/
public class StratrgyTest {

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

    @Test
    public void comeInIndex() {
        FactoryUtil.exec(StartNoxStrategy.class);
        startAppTest();
        loginTest();

        indexTest();
    }

    @Test
    public void acceptEmail() {
        AutoStrategy autoStrategy = new EmailStrategy();
        autoStrategy.exec();
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
        ProxyActionStrategy strategy = new ProxyActionStrategy();
        strategy.setEnergy(4);
        FactoryUtil.exec(strategy);

        FactoryUtil.exec(ExitNoxStrategy.class);
    }

    @Test
    public void IWantMoreMoney() {
        comeInIndex();

        FactoryUtil.exec(JumpChapterStrategy.MONEY);
        FactoryUtil.exec(ProxyActionStrategy.energy(10));

        FactoryUtil.exec(ExitNoxStrategy.class);
    }

    @Test
    public void IWantExperience() {
        comeInIndex();

        FactoryUtil.exec(JumpChapterStrategy.EXPERIENCE);

        FactoryUtil.exec(ProxyActionStrategy.energy(10));
        FactoryUtil.exec(ExitNoxStrategy.class);
    }

    @Test
    public void testJumpIndex() {
        FactoryUtil.exec(JumpChapterStrategy.INDEX);
    }

    @Test
    public void customTask() {

//        comeInIndex();

        FactoryUtil.exec(JumpChapterStrategy.MONEY);
        FactoryUtil.exec(ProxyActionStrategy.energy(20));

        FactoryUtil.exec(JumpChapterStrategy.EXPERIENCE);
        FactoryUtil.exec(ProxyActionStrategy.energy(30));

        FactoryUtil.exec(ExitNoxStrategy.class);
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
    public void tA2() {
        /** 刷活动图*/
        FactoryUtil.exec(ActivityHuoLanZhiXinStrategy.get("OF-7"));
        FactoryUtil.exec(ProxyActionStrategy.maxCount(15));

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
