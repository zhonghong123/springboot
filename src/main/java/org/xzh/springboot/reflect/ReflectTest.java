package org.xzh.springboot.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectTest {

	private static final Logger logger = LoggerFactory
			.getLogger(ReflectTest.class);
	private static Class<User> userClass = User.class;

	/**
	 * 在类加载的时候，jvm会创建一个class对象
	 * 
	 * class对象是可以说是反射中最常用的，获取class对象的方式的主要有三种
	 * 
	 * 根据类名：类名.class 根据对象：对象.getClass() 根据全限定类名：Class.forName(全限定类名)
	 * 
	 * @throws Exception
	 */
	@Test
	public void classTest() throws Exception {
		// 获取class对象的三种方法
		Class<?> clazz1 = User.class;
		logger.info("根据类名：{}", clazz1);
		Class<?> clazz2 = new User().getClass();
		logger.info("根据对象：{}", clazz2);
		Class<?> clazz3 = Class.forName("org.xzh.springboot.reflect.User");
		logger.info("根据全限定类名：{}", clazz3);

		// class常用方法
		logger.info("获取全限定类名:{}", userClass.getName());
		logger.info("获取类名：{}", userClass.getSimpleName());
		logger.info("实例化：{}", userClass.newInstance());
	}

	/**
	 * 构造函数是java创建对象的必经之路，所以通过反射拿到一个类的构造函数后， 再去创建这个类的对象自然是易如反掌，
	 * 常用的方法如下：
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@Test
	public void constructorTest() throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		// 获取全部的构造函数
		Constructor<?>[] constructors = userClass.getConstructors();
		// 取消安全性检查,设置后才可以使用private修饰的构造函数，也可以单独对某个构造函数进行设置
		// Constructor.setAccessible(constructors, true);
		for (int i = 0; i < constructors.length; i++) {
			Constructor<?> constructor = constructors[i];
			// 获取构造方法参数类型
			Class<?>[] types = constructor.getParameterTypes();
			logger.info("第{}个构造方法，参数{}", i, types);
			if (types.length == 0) {
				logger.info("第{}个构造方法，实例化{}", i, constructor.newInstance());
			} else {
				logger.info("第{}个构造方法，实例化{}", i,
						constructor.newInstance("zhang", "18"));
			}
		}
	}

	/**
	 * 犹记得学习spring ioc之时，对未提供set方法的private属性依然可以注入感到神奇万分，
	 * 现在看来，这神奇的根源自然是来自于java的反射，常用的方法如下：
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@Test
	public void fieldTest() throws InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException {
		User user = userClass.newInstance();
		// 获取当前类所有属性
		Field[] fields = userClass.getDeclaredFields();
		// 获取公有属性(包括父类)
		// Field fields[] = cl.getFields();
		// 取消安全性检查,设置后才可以获取或者修改private修饰的属性，也可以单独对某个属性进行设置
		Field.setAccessible(fields, true);

		for (Field field : fields) {
			logger.info("属性名称：{}，属性值：{}，属性类型：{}", field.getName(),
					field.get(user), field.getType());
		}

		fields[0].set(user, "zhangsan");
		fields[1].set(user, "13");
		logger.info("实例化{}", user);

		Field nameField = userClass.getDeclaredField("name");
		// 取消安全性检查,设置后才可以获取或者修改private修饰的属性，也可以批量对所有属性进行设置
		nameField.setAccessible(true);
		nameField.set(user, "lisi");
		logger.info("实例化{}", user);
	}

	/**
	 * 大家对javabean肯定不会陌生，在用框架操作javabean时，
	 * 大多都是通过反射调用get,set方法Javabean进行操作，常用的方法如下：
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void methodTest() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		User user = userClass.newInstance();
		// 获取当前类的所有方法
		Method[] methods = userClass.getDeclaredMethods();
		// 获取公有方法(包括父类)
		// Method[] methods = userClass.getMethods();
		// 取消安全性检查,设置后才可以调用private修饰的方法，也可以单独对某个方法进行设置
		Method.setAccessible(methods, true);
		
		for(Method method : methods){
			logger.info("方法名称{}，参数{}， 返回类型{}", method.getName(),method.getParameters(), method.getReturnType().getName());
		}
		
		// 获取无参方法
		Method getNameMethod = userClass.getDeclaredMethod("getName");
		// 取消安全性检查,设置后才可以调用private修饰的方法，也可以批量对所有方法进行设置
		getNameMethod.setAccessible(true);
		logger.info("调用getName方法：{}", getNameMethod.invoke(user));
		
		// 获取有参方法
		Method setNameMethod = userClass.getDeclaredMethod("setName", String.class);
		setNameMethod.setAccessible(true);
		setNameMethod.invoke(user, "lisis");
		logger.info("实例化{}", user);
	}
}
