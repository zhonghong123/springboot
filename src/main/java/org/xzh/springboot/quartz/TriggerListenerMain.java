package org.xzh.springboot.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.listeners.TriggerListenerSupport;

public class TriggerListenerMain {

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
		
		//添加trigger监听器
		scheduler.getListenerManager().addTriggerListener(new TriggerListenerMain().new MyTriggerListener(), 
				KeyMatcher.keyEquals(new TriggerKey("cronTrigger", "cronGroup")));
		
		scheduler.start();
	}
	
	public class MyTriggerListener extends TriggerListenerSupport{

		@Override
		public String getName() {
			return "MyTriggerListener";
		}
		
		@Override
		public void triggerComplete(Trigger trigger,
				JobExecutionContext context,
				CompletedExecutionInstruction triggerInstructionCode) {
			System.out.println(trigger.getKey().getName());
		}
	}
}
