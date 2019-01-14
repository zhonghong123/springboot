package org.xzh.springboot.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class CronTriggerMain {

	public static void main(String[] args) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		
		JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
				.withIdentity("cronJob", "cronGroup")
				.build();
		
		CronTrigger cronTrigger = TriggerBuilder.newTrigger()
				.withIdentity("cronTrigger", "cronGroup")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
				.forJob("cronJob", "cronGroup")
				.build();
		
		scheduler.scheduleJob(jobDetail, cronTrigger);
		
		scheduler.start();
	}
}
