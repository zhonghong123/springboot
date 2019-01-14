package org.xzh.springboot.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class PropertiesMain {

	public static void main(String[] args) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			//添加job
			JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever())
					.build();
			scheduler.scheduleJob(job, trigger);
			
			//the Scheduler will not actually act on any triggers (execute jobs) until it has been started with the start() method
			scheduler.start();
			
			//Once you obtain a scheduler using StdSchedulerFactory.getDefaultScheduler(), 
			//your application will not terminate until you call scheduler.shutdown(), 
			//because there will be active threads.
			scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
