package com.mlick.mrfzai.quartz.job;

import com.mlick.mrfzai.quartz.QuartzManager;
import com.mlick.mrfzai.utils.DateUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * job工具类
 *
 * @author Administrator
 */
@DisallowConcurrentExecution
public class ArkNightsJob implements Job {

  private static Logger logger = LoggerFactory.getLogger(QuartzManager.class);

  @Override
  public void execute(JobExecutionContext context) {

    Integer flag = (Integer) context.getMergedJobDataMap().get("entity");

    logger.info("==>" + flag);

    switch (flag) {
      case 1:
        System.out.println("执行 基站");

        break;
      case 2:
        System.out.println("执行 经验 金币");
        break;
      default:
        break;
    }
  }
}