package org.xzh.springboot.junit;

import java.util.ArrayList;

import org.junit.Test;

public class ExceptionMain {

	@Test(expected = IndexOutOfBoundsException.class) 
	public void empty() { 
	     new ArrayList<Object>().get(0); 
	}
}
