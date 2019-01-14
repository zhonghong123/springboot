package org.xzh.springboot.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumbJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(DumbJob.class);
	
	private String name;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobKey jobKey = arg0.getJobDetail().getKey();
		
		TriggerKey triggerKey = arg0.getTrigger().getKey();
		
		JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();
		String jobSays = jobDataMap.getString("jobSays");
	    float myFloatValue = jobDataMap.getFloat("myFloatValue");
	    
	    logger.info("jobKey.name={}, jobSays={}, myFloatValue={}, name={}, triggerKey.name={}", 
	    		jobKey.getName(), jobSays, myFloatValue, name, triggerKey.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
