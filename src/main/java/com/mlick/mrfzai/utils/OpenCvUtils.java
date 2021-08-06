package com.mlick.mrfzai.utils;

import com.mlick.mrfzai.core.Action;
import com.mlick.mrfzai.core.Constants;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Objects;

import static com.mlick.mrfzai.core.Action.*;
import static com.mlick.mrfzai.core.Constants.FILE_TEMP_PATH;

/**
 * @author lixiangxin
 * @date 2019/6/4 14:30
 **/
public class OpenCvUtils {


    private static Logger logger = LoggerFactory.getLogger(OpenCvUtils.class);

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Point findProxyStartAction() {
        return findAndAction(START_ACTION);
    }

    public static Point findEndAction(int count) {
        return retryExec(END_ACTION_BTN, count);
    }

    public static Point findYesAction() {
        return findImage("yes_btn.png");
    }

    public static Point findNextAction() {
        return findAndAction("next_btn.png");
    }

    public static Point findNextWhiteAction() {
        return findAndAction("next_white_btn.png");
    }

    public static Point findExitAction() {
        return findAndAction(EXIT_1);
    }

    public static Point findContinueAction() {
        return findImage("continue_submit.png");
    }


    public static Point findLevelUpAction() {
        return findImage(LEVEL_UP_BTN);
    }

    public static Point findStart() {
        return findImage(START);
    }

    public static Point findStartWake() {
        return retryExec(START_WAKE, 3);
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
        return retryExec(LOGIN_ACCOUNT_BTN, 3);
    }

    public static Point findAndAction(String templateImg) {
        Point point = findImage(templateImg);
        ShellUtils.executePoint(point);
        return point;
    }

    public static Point findAndAction(Action action) {
        Point point = findImage(action);
        ShellUtils.executePoint(point);
        return point;
    }

    public static Point findAndAction(Action action, double ex) {
        Point point = findImage(action.getImg(), ex);
        ShellUtils.executePoint(point);
        return point;
    }


    public static Point findImage(Action action) {
        return findImage(action, Imgproc.TM_CCOEFF_NORMED);
    }


    public static Point findImage(Action action, int method) {
        Point image = findImage(action.getImg(), method);

        if (image != null) {
            return image;
        }

        String[] other = action.getOther();
        if (other == null) {
            return image;
        }

        for (String s : other) {
            image = findImage(s, false, Constants.DESIRED_ACCURACY, method);
            if (image != null) {
                return image;
            }
        }

        return image;
    }


    public static Point findImage(String templateImg) {
        return findImage(templateImg, Constants.DESIRED_ACCURACY);
    }

    public static Point findImage(String templateImg, double ex) {
        return findImage(templateImg, true, ex);
    }

    public static Point findImage(String templateImg, int method) {
        return findImage(templateImg, true, Constants.DESIRED_ACCURACY, method);
    }

    public static Point findImage(String templateImg, boolean isCapScreen, double ex) {
        return findImage(templateImg, isCapScreen, ex, Imgproc.TM_CCOEFF_NORMED);
    }

    public static Point findImage(String templateImg, boolean isCapScreen, double ex, int method) {

        if (templateImg == null) {
            return null;
        }

        String desc = Action.DESC.get(templateImg);
        if (!templateImg.endsWith(".png")) {
            templateImg += ".png";
        }

        if (isCapScreen) {
            ShellUtils.screenCap();
        }

        String templatePath = Objects.requireNonNull(Paths.get("res/" + templateImg).toFile()).getAbsolutePath();
        String sourcePath = Objects.requireNonNull(Paths.get(FILE_TEMP_PATH + Constants.screenPath).toFile())
                .getAbsolutePath();

        return findImage(sourcePath, templatePath, desc, ex, method);
    }

    public static Point findImage(String sourceImg, String templateImg, String desc, double expect, int intMatchingMethod) {

        String sourceMsg = String.format("source  =>[%s]", sourceImg);
        String templateMsg = String.format("template=>[%s]", templateImg);
        logger.debug(sourceMsg);
        logger.debug(templateMsg);

        Mat sourceMat = Imgcodecs.imread(sourceImg);
        Mat templateMat = Imgcodecs.imread(templateImg);

        if (sourceMat.width() < templateMat.width() || sourceMat.height() < templateMat.height()) {
            logger.error("The template image is larger than the source image. Ensure that the width and/or " +
                                 "height of " +
                                 "the image you are trying to find do not exceed the dimensions of the source image.");
            return null;
        }

        Mat result = new Mat(sourceMat.rows() - templateMat.rows() + 1, sourceMat.rows() - templateMat.rows() + 1,
                             CvType.CV_32FC1);

//        intMatchingMethod = Imgproc.TM_SQDIFF_NORMED;//标准化差值平方和匹配
//        intMatchingMethod = Imgproc.TM_SQDIFF;//标准化差值平方和匹配

//        intMatchingMethod = Imgproc.TM_CCORR;//相关匹配
//        intMatchingMethod = Imgproc.TM_CCORR_NORMED;//相关匹配

//        intMatchingMethod = Imgproc.TM_CCOEFF_NORMED;//标准相关匹配
//        intMatchingMethod = Imgproc.TM_CCOEFF;//标准相关匹配

        Imgproc.matchTemplate(sourceMat, templateMat, result, intMatchingMethod);
        Core.MinMaxLocResult minMaxLocRes = Core.minMaxLoc(result);


        String resultMsg = "MaxAccuracy:" + minMaxLocRes.maxVal + " MinAccuracy:" + minMaxLocRes.minVal;
        logger.debug(resultMsg);

        if (!minMaxLocResultIsValid(minMaxLocRes)) {
            logger.error(
                    "Image find result (MinMaxLocResult) was invalid. This usually happens when the source image is " +
                            "covered in " +
                            "one solid color.");
            return null;
        }

        Point matchLocation;

        boolean isExpect = false;
        if (intMatchingMethod == Imgproc.TM_SQDIFF || intMatchingMethod == Imgproc.TM_SQDIFF_NORMED) {
            matchLocation = minMaxLocRes.minLoc;
        } else {
            isExpect = true;
            matchLocation = minMaxLocRes.maxLoc;
        }

        if (isExpect && minMaxLocRes.maxVal < expect) {
            ShellUtils.sleepTime(1);
            logger.error("【" + desc + "】未找到");
            ShellUtils.sleepTime(1);
            return null;
        }
        logger.info("成功找到【" + desc + "】");

        String resultImgFile = FILE_TEMP_PATH + getResultFileName(minMaxLocRes.maxVal);
        Imgproc.rectangle(sourceMat, matchLocation,
                          new Point(matchLocation.x + templateMat.cols(), matchLocation.y + templateMat.rows()),
                          new Scalar(0, 255, 0));
        Imgcodecs.imwrite(resultImgFile, sourceMat);


        return new Point(matchLocation.x + templateMat.width() / 2.0f,
                         matchLocation.y + templateMat.height() / 2.0f);
    }

    private static String getResultFileName(double accuracy) {
        String name = Constants.resultPath + accuracy;
        name = name.replace(".png", "-");
        name = name.replace("0.", "0_");
        name = name.replace("1.", "1_");
        return name + ".png";
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

    public static Point loopFindIfNullExit(Action img) {
        return loopFind(img.getImg(), true, null);
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

    public static Point loopFind(Action img, int count) {
        return loopFind(img.getImg(), count);
    }

    public static Point isNullFindAndAction(Point point, String tmp) {
        if (point != null) {
            return point;
        }

        return findAndAction(tmp);
    }

    public static Point retryExec(Action action, int count) {
        for (int i = 0; i < count; i++) {
            if (i == 1) {
                logger.info("即将循环查找" + count + "次");
            }
            Point point = OpenCvUtils.findAndAction(action);
            if (point != null) {
                return point;
            }
            logger.info("循环查找第:" + (i + 1) + "次");
            ShellUtils.sleepTime();
        }
        logger.error(String.format("【%s】循环%d次后，未找到,待处理", action.getName(), count));
        return null;
    }

}
