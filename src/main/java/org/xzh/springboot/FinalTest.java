package org.xzh.springboot;

public class FinalTest {

	//非静态常量可以在构造方法中初始化，也可以在定义是初始化
	private final String name;
	private final int age = 14;
	
	//静态常量可以在static代码块中初始化，也可以在定义时初始化
	private static final String sex;
	private static final String phone = "123444";
	
	static{
		sex="1";
	}
	
	public FinalTest() {
		name = "123";
	}
	
	@Override
	public String toString() {
		return "FinalTest [name=" + name + ", age=" + age + "]";
	}

	public static void main(String[] args) {
		FinalTest test = new FinalTest();
		System.out.println(test.toString());
		System.out.println(FinalTest.sex);
		System.out.println(FinalTest.phone);
	}
}
