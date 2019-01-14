package org.xzh.springboot.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.SchedulerListenerSupport;

public class SchedulerListenerMain {

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
		
		//添加scheduler监听器
		scheduler.getListenerManager().addSchedulerListener(new SchedulerListenerMain().new MySchedulerListener());
		
		scheduler.start();
	}
	
	public class MySchedulerListener extends SchedulerListenerSupport{
		@Override
		public void schedulerStarted() {
			System.out.println("MySchedulerListener start");
		}
	}
}
