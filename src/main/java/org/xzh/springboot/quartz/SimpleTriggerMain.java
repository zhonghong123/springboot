package org.xzh.springboot.quartz;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleTriggerMain {

	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail job = JobBuilder.newJob(MyJob.class)
					.withIdentity("myJob", "myGroup")
					.build();
			
			SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
					.withIdentity("myTrigger", "myGroup")
					.startAt(new Date())	//开始时间
					.withSchedule(SimpleScheduleBuilder
							.simpleSchedule()
							.withIntervalInSeconds(10)	//间隔10s
							.withRepeatCount(10))	//重复10次
					.forJob("myJob", "myGroup")
					.build();
			
			scheduler.scheduleJob(job, trigger);
			
			scheduler.start();			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
