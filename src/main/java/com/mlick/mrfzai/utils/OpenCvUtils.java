package com.mlick.mrfzai.utils;

import com.mlick.mrfzai.core.Action;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author lixiangxin
 * @date 2019/6/4 14:30
 **/
public class OpenCvUtils {

    private static final double DESIRED_ACCURACY = 0.89;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Point findProxyStartAction() {
        return findImage("start_action_btn.png");
    }

    public static Point findStartAction() {
        return findImage("start_action_btn2.png");
    }

    public static Point findYesAction() {
        return findImage("yes_btn.png");
    }

    public static Point findNextAction() {
        return findImage("next_btn.png");
    }

    public static Point findNextWhiteAction() {
        return findImage("next_white_btn.png");
    }

    public static Point findExitAction() {
        return findImage("exit_btn.png");
    }

    public static Point findContinueAction() {
        return findImage("continue_submit.png");
    }

    public static Point findEndAction() {
        return findImage("end_action_btn.png");
    }

    public static Point findLevelUpAction() {
        return findImage("level_up_btn.png");
    }

    public static Point findStart() {
        return findImage("START.png");
    }

    public static Point findStartWake() {
        return findImage("start_wake.png");
    }

    public static Point findEmail() {
        return findImage("email_btn.png");
    }

    public static Point findAcceptEmail() {
        return findImage("email_accept_all.png");
    }

    public static Point findDeleteEmail() {
        return findImage("email_delete_btn.png");
    }

    public static Point findBackBtn() {
        return findImage("back_white_btn.png");
    }

    public static Point findBlackBackBtn() {
        return findImage("back_black_btn.png");
    }

    public static Point findLoginAccountBtn() {
        return findImage("login_account_btn.png");
    }


    public static Point findAndAction(String templateImg) {
        Point point = findImage(templateImg);
        ShellUtils.executePoint(point);
        return point;
    }

    public static boolean findAndActionForResult(String templateImg) {
        return ShellUtils.executePoint(findImage(templateImg));
    }

    public static Point findImage(String templateImg) {

        if (templateImg == null) {
            return null;
        }

        if (!templateImg.endsWith(".png")) {
            templateImg += ".png";
        }

        ShellUtils.screenCap();

        String path = Objects.requireNonNull(Paths.get("resources/" + templateImg).toFile()).getAbsolutePath();
        System.out.println(path);
        return findImage(ShellUtils.screenPath, path);
    }

    public static Point findImage(String sourceImg, String templateImg) {
        return findImage(sourceImg, templateImg, null);
    }

    public static Point findImage(String sourceImg, String templateImg, String resultImgFile) {
        Action action = Action.INSTANCE.getAction(templateImg);
        return findImage(sourceImg, action, resultImgFile);
    }

    public static Point findImage(String sourceImg, Action templateImg, String resultImgFile) {

        Mat sourceMat = Imgcodecs.imread(sourceImg);
        Mat templateMat = Imgcodecs.imread(templateImg.getImg());

        if (sourceMat.width() < templateMat.width() || sourceMat.height() < templateMat.height()) {
            System.err.println("The template image is larger than the source image. Ensure that the width and/or " +
                "height of " +
                "the image you are trying to find do not exceed the dimensions of the source image.");
            return null;
        }

        Mat result = new Mat(sourceMat.rows() - templateMat.rows() + 1, sourceMat.rows() - templateMat.rows() + 1,
            CvType.CV_32FC1);
        int intMatchingMethod;


        intMatchingMethod = Imgproc.TM_CCOEFF_NORMED;

        Imgproc.matchTemplate(sourceMat, templateMat, result, intMatchingMethod);
        Core.MinMaxLocResult minMaxLocRes = Core.minMaxLoc(result);

        double accuracy = minMaxLocRes.maxVal;

        System.out.println(accuracy);

        if (accuracy < DESIRED_ACCURACY) {
            ShellUtils.sleepTime(1);
            System.err.println(templateImg.getImg() + "=>【" + templateImg.getName() + "】未找到=>" + accuracy);
            ShellUtils.sleepTime(1);
            return null;
        }

        if (!minMaxLocResultIsValid(minMaxLocRes)) {
            System.out.println(
                "Image find result (MinMaxLocResult) was invalid. This usually happens when the source image is " +
                    "covered in " +
                    "one solid color.");
            return null;
        }

        Point matchLocation = minMaxLocRes.maxLoc;

        Point resultPoint = new Point(matchLocation.x + templateMat.width() / 2.0f,
            matchLocation.y + templateMat.height() / 2.0f);

        if (resultImgFile == null || resultImgFile.length() == 0) {
            return resultPoint;
        }

        Imgproc.rectangle(sourceMat, matchLocation,
            new Point(matchLocation.x + templateMat.cols(), matchLocation.y + templateMat.rows()),
            new Scalar(0, 0, 0, 0));

        Imgcodecs.imwrite(resultImgFile, sourceMat);

        return resultPoint;
    }

    private static boolean minMaxLocResultIsValid(Core.MinMaxLocResult minMaxLocRes) {
        if (minMaxLocRes.minVal == 1
            && minMaxLocRes.maxVal == 1
            && minMaxLocRes.maxLoc.x == 0
            && minMaxLocRes.maxLoc.y == 0
            && minMaxLocRes.minLoc.x == 0
            && minMaxLocRes.minLoc.y == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static Point loopFindIfNullExit(String img) {
        return loopFind(img, true, null);
    }

    public static Point loopFind(String img, boolean ifNullExit, String nextImg) {

        Point point;
        int count = 10;
        do {
            point = OpenCvUtils.findAndAction(img);
            if (count-- < 0) {
                break;
            }
        } while (!ifNullExit || point != null || OpenCvUtils.findAndAction(nextImg) != null);

        return null;
    }

    public static Point loopFind(String img) {
        return loopFind(img, 10);
    }

    public static Point loopFind(String img, int count) {
        ShellUtils.sleepTime(2);

        Point point;
        while ((point = OpenCvUtils.findAndAction(img)) == null) {
            ShellUtils.sleepTime(3);
            if (count-- < 0) {
                break;
            }
        }

        ShellUtils.sleepTime(5);
        return point;
    }


    public static void main2(String[] args) {

        Point b = findImage("D:\\tmp\\bg\\screen3_1.png", "D:\\tmp\\START.png");
        System.out.println(b);

        b = findImage("D:\\tmp\\login.png", "D:\\tmp\\start_wake.png");
        System.out.println(b);

        b = findImage("D:\\tmp\\screen3_1.png", "D:\\tmp\\start_wake.png");
        System.out.println(b);

        b = findImage("D:\\tmp\\proxy_start_action.png", "D:\\tmp\\start_action_btn.png");
        System.out.println(b);

        b = findImage("D:\\tmp\\end_action.png", "D:\\tmp\\end_action_btn.png");
        System.out.println(b);

        b = findImage("D:\\tmp\\end_action2.png", "D:\\tmp\\end_action_btn.png");
        System.out.println(b);
    }


    public static void main(String[] args) {
        OpenCvUtils.findImage("D:\\tmp\\bg\\screen.png", "D:\\tmp\\RES\\START.png");//, "D:\\tmp\\bh\\match2.png");
    }


    public static Point isNullFindAndAction(Point point, String tmp) {
        if (point != null) {
            return point;
        }

        return findAndAction(tmp);
    }
}
