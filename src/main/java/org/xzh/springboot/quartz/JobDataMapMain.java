package org.xzh.springboot.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class JobDataMapMain {

	public static void main(String[] args) {
		try {
			//从工厂类创建调度器实例
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			JobDetail job = JobBuilder.newJob(DumbJob.class).withIdentity("job1", "group1")
					.usingJobData("jobSays", "hello quartz")
					.usingJobData("myFloatValue", 3.141f)
					.usingJobData("name", "xzh")
					.build();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever())
					.build();
			scheduler.scheduleJob(job, trigger);
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
