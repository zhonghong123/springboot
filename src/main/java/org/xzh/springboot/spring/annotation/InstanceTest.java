package org.xzh.springboot.spring.annotation;

public class InstanceTest {
	
	private String name;
	
	public InstanceTest(String name) {
		this.name = name;
	}
	
	public String returnIntanceNam(){
		return "instanceTest:"+name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
