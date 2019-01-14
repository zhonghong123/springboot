package org.xzh.springboot.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzMain {
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzMain.class);

	public static void main(String[] args) {
		try {
			//从工厂类创建调度器实例
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever())
					.build();
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
			
			logger.info("end...");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
