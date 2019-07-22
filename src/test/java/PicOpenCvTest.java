import com.mlick.mrfzai.utils.OpenCvUtils;
import com.mlick.mrfzai.utils.ShellUtils;
import org.junit.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * @author lixiangxin
 * @date 2019/6/3 11:28
 **/
public class PicOpenCvTest {

  //
//  @Test
//  public void t1() {
//    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//    Mat source, template;
//    //将文件读入为OpenCV的Mat格式
//    source = Highgui.imread("C:\\Users\\Administrator\\Desktop\\161272e10062b204.png");
//    template = Highgui.imread("C:\\Users\\Administrator\\Desktop\\161272e12cabae72.png");
//    //创建于原图相同的大小，储存匹配度
//    Mat result = Mat.zeros(source.rows() - template.rows() + 1, source.cols() - template.cols() + 1, CvType.CV_32FC1);
//    //调用模板匹配方法
//    Imgproc.matchTemplate(source, template, result, Imgproc.TM_SQDIFF_NORMED);
//    //规格化
//    Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1);
//    //获得最可能点，MinMaxLocResult是其数据格式，包括了最大、最小点的位置x、y
//    Core.MinMaxLocResult mlr = Core.minMaxLoc(result);
//    Point matchLoc = mlr.minLoc;
//    //在原图上的对应模板可能位置画一个绿色矩形
//    Core.rectangle(source, matchLoc, new Point(matchLoc.x + template.width(), matchLoc.y + template.height()), new Scalar(0, 255, 0));
//    //将结果输出到对应位置
//    Highgui.imwrite("C:\\Users\\Administrator\\Desktop\\new.png", source);
//  }

  @Test
  public void t2() {
    String filename = "C:\\Users\\Administrator\\Desktop\\161272e10062b204.png";
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
    System.out.println("mat = " + mat.dump());
  }

  /**
   * 320
   */
  @Test
  public void t3() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    Mat g_src = Imgcodecs.imread("D:\\tmp\\screen.jpg");
//    Mat g_tem = Imgcodecs.imread("D:\\crop1.png");
    Mat g_tem = Imgcodecs.imread("D:\\tmp\\building_result.jpg");

    int result_rows = g_src.rows() - g_tem.rows() + 1;
    int result_cols = g_src.cols() - g_tem.cols() + 1;
    Mat g_result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
    Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_CCOEFF_NORMED); // 归一化相关系数匹配法

//     Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_CCOEFF);
    // 相关系数匹配法：1表示完美的匹配；-1表示最差的匹配。

    // Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_CCORR); //
    // 相关匹配法

//    Imgproc.matchTemplate(g_src, g_tem, g_result, Imgproc.TM_SQDIFF); //
    // 平方差匹配法：该方法采用平方差来进行匹配；最好的匹配值为0；匹配越差，匹配值越大。

    // Imgproc.matchTemplate(g_src, g_tem,g_result,Imgproc.TM_CCORR_NORMED);
    // // 归一化相关匹配法

    Core.normalize(g_result, g_result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

    Core.MinMaxLocResult mmlr = Core.minMaxLoc(g_result);

    Point matchLocation = mmlr.maxLoc; // 此处使用maxLoc还是minLoc取决于使用的匹配算法
    Imgproc.rectangle(g_src, matchLocation,
        new Point(matchLocation.x + g_tem.cols(), matchLocation.y + g_tem.rows()),
        new Scalar(0, 0, 0, 0));

    Imgcodecs.imwrite("D:\\tmp\\match.jpg", g_src);
  }

  public static void main2(String[] args) {

    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    Mat source = null;
    Mat template = null;
    String filePath = "D:\\tmp\\";
    //Load image file

    source = Imgcodecs.imread(filePath + "screen3_1.png");
    template = Imgcodecs.imread(filePath + "START.png");

//    source = Imgcodecs.imread(filePath + "login.png");
//    template = Imgcodecs.imread(filePath + "start-wake.png");
//    template = Imgcodecs.imread(filePath + "start-screan.png");

//    Mat outputImage = new Mat();
    // / Create the result matrix
    int result_cols = source.cols() - template.cols() + 1;
    int result_rows = source.rows() - template.rows() + 1;
    Mat outputImage = new Mat(result_rows, result_cols, CvType.CV_32FC1);

//    int machMethod = Imgproc.TM_CCOEFF;
//    int machMethod = Imgproc.TM_CCORR;
//    int machMethod = Imgproc.TM_CCORR_NORMED; // 归一化平方差匹配法
    int machMethod = Imgproc.TM_SQDIFF; // 归一化平方差匹配法
    //Template matching method
    Imgproc.matchTemplate(source, template, outputImage, machMethod);

    Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);

    System.out.println(mmr.minVal + " " + mmr.maxVal);
    System.out.println(mmr.minLoc.x + " " + mmr.minLoc.y);
    //  min  x=683.0 y=331.0
    //  max  x=607.0 y=649.0
    System.out.println(mmr.maxLoc.x + " " + mmr.maxLoc.y);
    System.out.println((mmr.maxLoc.x + template.width() / 2.0f) + " " + (mmr.maxLoc.y + template.height() / 2.0f));
    System.out.println((mmr.maxLoc.x + template.cols() / 2.0f) + " " + (mmr.maxLoc.y + template.rows() / 2.0f));
    System.out.println((mmr.maxLoc.x + template.width()) + " " + (mmr.maxLoc.y + template.height()));

    Point matchLoc;
    if (machMethod == Imgproc.TM_SQDIFF
        || machMethod == Imgproc.TM_SQDIFF_NORMED) {
      matchLoc = mmr.minLoc;
      System.out.println(mmr.minVal);
    } else {
      matchLoc = mmr.maxLoc;
      System.out.println(mmr.maxVal);
    }

    //Draw rectangle on result image
    Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
        matchLoc.y + template.rows()), new Scalar(0, 255, 0));

    Imgcodecs.imwrite(filePath + "sonuc6.jpg", source);
    System.out.println("Complated.");
  }


  public static void main(String[] args) {

    OpenCvUtils.findImage(ShellUtils.screenPath,"home.png","home_t.png");
  }
}
