package org.xzh.springboot.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MyAnnotation(color="grep", value="123", arrint={4,5,6}, 
lamp=EumTrafficLamp.GREEN, annotationAttr=@MetaAnnotation("ddd"))
public class MyAnnotationTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MyAnnotationTest.class);

	/**
	 * 类注解
	 */
	@Test
	public void classTest() {
		//这里是检查Annotation类是否有注解，这里需要使用反射才能完成对Annotation类的检查
		if(MyAnnotationTest.class.isAnnotationPresent(MyAnnotation.class)){
			/**
			* 用反射方式获得注解对应的实例对象后，在通过该对象调用属性对应的方法
			* MyAnnotation是一个类，这个类的实例对象annotation是通过反射得到的，这个实例对象是如何创建的呢？
			* 一旦在某个类上使用了@MyAnnotation，那么这个MyAnnotation类的实例对象annotation就会被创建出来了
			*/
			MyAnnotation myAnnotation = MyAnnotationTest.class.getAnnotation(MyAnnotation.class);
			
			logger.info("color:{}", myAnnotation.color());
			logger.info("value:{}", myAnnotation.value());
			logger.info("arrint:{}", myAnnotation.arrint());
			logger.info("lamp:{}", myAnnotation.lamp());
			logger.info("annotationAttr:{}", myAnnotation.annotationAttr().value());
		}
	}
	
	@MyAnnotation("run method")
	public void run(){
		logger.info("this method is running...");
	}
	
	/**
	 * 方法注解
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@Test
	public void methodTest() throws NoSuchMethodException, SecurityException{
		//利用class获取method
		Method run = MyAnnotationTest.class.getMethod("run");
		
		//判断方式是否有MyAnnotation注解
		if(run.isAnnotationPresent(MyAnnotation.class)){
			MyAnnotation myAnnotation = run.getAnnotation(MyAnnotation.class);
			logger.info("color:{}", myAnnotation.color());
			logger.info("value:{}", myAnnotation.value());
			logger.info("arrint:{}", myAnnotation.arrint());
			logger.info("lamp:{}", myAnnotation.lamp());
			logger.info("annotationAttr:{}", myAnnotation.annotationAttr().value());
		}
	}
	
	@MyAnnotation("xiezhonghong")
	private String name;
	
	/**
	 * 属性注解
	 * @throws ClassNotFoundException 
	 */
	@Test
	public void fieldTest() throws ClassNotFoundException{
		//获取class
		Class<?> clazz = Class.forName("org.xzh.springboot.annotation.MyAnnotationTest");
		//获取当前类的属性
		Field[] fields = clazz.getDeclaredFields();
		//取消安全性检查
		Field.setAccessible(fields, true);
		
		for(Field field : fields){
			if(field.isAnnotationPresent(MyAnnotation.class)){
				MyAnnotation myAnnotation = field.getAnnotation(MyAnnotation.class);
				
				logger.info("color:{}", myAnnotation.color());
				logger.info("value:{}", myAnnotation.value());
				logger.info("arrint:{}", myAnnotation.arrint());
				logger.info("lamp:{}", myAnnotation.lamp());
				logger.info("annotationAttr:{}", myAnnotation.annotationAttr().value());
			}
		}
	}
}
