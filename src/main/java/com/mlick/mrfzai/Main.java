package com.mlick.mrfzai;

import com.mlick.mrfzai.core.AutoStrategy;
import com.mlick.mrfzai.strategy.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;

/**
 * @author lixiangxin
 * @date 2019/6/1 16:38
 */
public class Main {

  public static void main(String[] args) {

    MainAuto.getAdb(args);

    Scanner scanner = new Scanner(System.in);

    while (true) {

      System.out.println("=====================");
      System.out.println("======= -1 退出  ====");
      System.out.println("=======  0 启动  ====");
      System.out.println("=======  1 登陆  ====");
      System.out.println("=======  2 首页  ====");
      System.out.println("=======  3 邮件  ====");
      System.out.println("=======  4 经验  ====");
      System.out.println("=======  5 金币  ====");
      System.out.println("=======  6 代理  ====");
      System.out.println("=====================");

      int sel = scanner.nextInt();
      if (sel == -1) {
        break;
      }
      AutoStrategy autoStrategy = null;
      switch (sel) {
        case 0:
          autoStrategy = new StartAppStrategy();
          break;
        case 1:
          autoStrategy = new LoginStrategy();
          break;
        case 2:
          autoStrategy = new IndexStrategy();
          break;
        case 3:
          autoStrategy = new EmailStrategy();
          break;
        case 4: //经验
          autoStrategy = JumpChapterStrategy.EXPERIENCE;
          break;
        case 5: //龙门币
          autoStrategy = JumpChapterStrategy.MONEY;
          break;
        case 6:
          autoStrategy = new ProxyActionStrategy();
          break;
        default:
          break;
      }
      if (autoStrategy != null) {
        autoStrategy.exec();
      }
    }

  }

//  public static void main2(String[] args) throws IOException {
//
//    String userDir = System.getProperty("user.dir");
//    logger.info(userDir);
//
//    logger.info(Paths.get(userDir + "/resource").toFile().exists());
//
//    //    String path = Main.class.getResource("next_btn.png").getPath();
//    //    logger.info("URL=>" + path);
//
//    String path1 =
//        Optional.ofNullable(Main.class.getClassLoader().getResource("next_btn.png"))
//            .map(URL::getPath)
//            .orElse("null");
//
//    logger.info("URL1=>" + path1);
//
//    File directory = new File(""); // 参数为空
//    String courseFile = directory.getCanonicalPath(); // 标准的路径 ;
//    String author = directory.getAbsolutePath(); // 绝对路径;
//    logger.info(courseFile);
//    logger.info(author);
//
////    String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
////    logger.info("path: " + path);
////    JarFile localJarFile = new JarFile(new File(path));
////    Enumeration<JarEntry> entries = localJarFile.entries();
////    while (entries.hasMoreElements()) {
////      JarEntry jarEntry = entries.nextElement();
////      logger.info("jarEntry->name=>" + jarEntry.getName());
////    }
//
//    URL l2 = Thread.currentThread().getContextClassLoader().getResource("next_btn.png");
//
//    logger.info("URL2=>" + l2.getPath());
//  }
}
