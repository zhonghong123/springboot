package org.xzh.springboot.reflect;

public class User {

	private String name = "init";
	private String age;
	public User() {}
	public User(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}
	private String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}
}
