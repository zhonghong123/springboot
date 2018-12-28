package org.xzh.springboot.spring.annotation;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SimpleServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

	private SimpleDao simpleDao;
	
	@Autowired
	@Qualifier("qualifier")
	private InstanceTest qualifierInstance;
	
	@Autowired
	private InstanceTest instanceTest;
	
	@Resource(name="privateInstance")
	private InstanceTest privateInstance;
	
	@Resource(name="privateInstance")
	private InstanceTest privateInstance2;
	
	@Autowired
	private ConfigureTest configureTest;

	@Autowired
	public void setSimpleDao(SimpleDao simpleDao) {
		this.simpleDao = simpleDao;
	}
	
	public void printString(){
		logger.info(simpleDao.getString());
		logger.info(qualifierInstance.returnIntanceNam());
		logger.info(instanceTest.returnIntanceNam());
		logger.info(privateInstance.returnIntanceNam());
		logger.info("{}", privateInstance == privateInstance2);
		logger.info("simpleDao: {}", simpleDao.publicInstance() == simpleDao.publicInstance());
		
		logger.info("configureTest: {}", configureTest.publicInstance() == configureTest.publicInstance());
	}
}
