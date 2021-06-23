package com.mlick.mrfzai;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author lixiangxin
 * @date 2021/6/22 21:18
 **/
public class MainWindows {

    static Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包

    /**
     * JAVA 图像等比缩放
     *
     * @param srcImageFilePath  缩放的图片
     * @param destImageFilePath 缩放后的图片
     * @param scale             缩放比例
     */
    public static void scale(String srcImageFilePath, String destImageFilePath, float scale) {
        try {
            File srcImageFile = new File(srcImageFilePath);
            File destImageFile = new File(destImageFilePath);
            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            //获取缩放后的宽高
            int width = (int) (read.getWidth() * scale);
            int height = (int) (read.getHeight() * scale);
            //调用缩放方法获取缩放后的图片
            Image img = read.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            //创建一个新的缓存图片
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //获取画笔
            Graphics2D graphics = image.createGraphics();
            //将Image对象画在画布上,最后一个参数,ImageObserver:接收有关 Image 信息通知的异步更新接口,没用到直接传空
            graphics.drawImage(img, 0, 0, null);
            //一定要释放资源
            graphics.dispose();
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出
            ImageIO.write(image, formatName, destImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MyPanel extends Panel {

        double scale = 2;
        private final Image screenImage = new BufferedImage((int) (540 * scale), (int) (960 * scale), 2);

        private final Graphics2D screenGraphic = (Graphics2D) screenImage
                .getGraphics();

        private Image backgroundImage;

        public MyPanel() {
            loadImage();
            // 设定焦点在本窗体
            setFocusable(true);
            // 设定初始构造时面板大小,这先采用图片的大里小
            setPreferredSize(kit.getScreenSize());
            // 绘制背景
            drawView();
        }

        /**
         * 载入图像
         */
        private void loadImage() {

            float scale = 0.5f;
            String sourceImg = "D:\\workspace\\github\\Arknights-AI\\tempFile\\2021-06-22-231057-screen.png";
            String sourceScaleImg = "D:\\workspace\\github\\Arknights-AI\\tempFile\\2021-06-22-231057-screen-s.png";

            scale(sourceImg, sourceScaleImg, scale);

            //获得当前类对应的相对位置image文件夹下的背景图像
            ImageIcon icon = new ImageIcon(sourceScaleImg);
            //将图像实例赋给backgroundImage
            backgroundImage = icon.getImage();
        }

        private void drawView() {
            screenGraphic.drawImage(backgroundImage, 0, 0, null);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(screenImage, 0, 0, null);
        }
    }


    public static void main(String[] args) {
        String title = "Source image; Control; Result image";
        JFrame frame = new JFrame(title);

        // 获得面板的实例
        MyPanel panel = new MyPanel();
        frame.add(panel);

        frame.addWindowListener(new WindowAdapter() {
            //设置关闭
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        frame.setMinimumSize(screenSize);
        // 执行并构建窗体设定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
