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
          autoStrategy = new JumpChapterStrategy(1);
          break;
        case 5: //龙门币
          autoStrategy = new JumpChapterStrategy(2);
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

  public static void main2(String[] args) throws IOException {

    String userDir = System.getProperty("user.dir");
    System.out.println(userDir);

    System.out.println(Paths.get(userDir + "/resource").toFile().exists());

    //    String path = Main.class.getResource("next_btn.png").getPath();
    //    System.out.println("URL=>" + path);

    String path1 =
        Optional.ofNullable(Main.class.getClassLoader().getResource("next_btn.png"))
            .map(URL::getPath)
            .orElse("null");

    System.out.println("URL1=>" + path1);

    File directory = new File(""); // 参数为空
    String courseFile = directory.getCanonicalPath(); // 标准的路径 ;
    String author = directory.getAbsolutePath(); // 绝对路径;
    System.out.println(courseFile);
    System.out.println(author);

//    String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//    System.out.println("path: " + path);
//    JarFile localJarFile = new JarFile(new File(path));
//    Enumeration<JarEntry> entries = localJarFile.entries();
//    while (entries.hasMoreElements()) {
//      JarEntry jarEntry = entries.nextElement();
//      System.out.println("jarEntry->name=>" + jarEntry.getName());
//    }

    URL l2 = Thread.currentThread().getContextClassLoader().getResource("next_btn.png");

    System.out.println("URL2=>" + l2.getPath());
  }
}
