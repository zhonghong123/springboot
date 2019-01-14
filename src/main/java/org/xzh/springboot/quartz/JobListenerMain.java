package org.xzh.springboot.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.listeners.JobListenerSupport;

public class JobListenerMain {

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
		
		//添加监听器
		scheduler.getListenerManager().addJobListener((new JobListenerMain()).new MyJobListener(), 
				KeyMatcher.keyEquals(new JobKey("cronJob", "cronGroup")));
		
		scheduler.start();
	}
	
	public class MyJobListener extends JobListenerSupport{

		@Override
		public String getName() {
			return "MyJobListener";
		}
		
		@Override
		public void jobToBeExecuted(JobExecutionContext context) {
			System.out.println("jobToBeExecuted...");
		}
		
		@Override
		public void jobWasExecuted(JobExecutionContext context,
				JobExecutionException jobException) {
			System.out.println("jobWasExecuted...");
		}
		
	}
}
