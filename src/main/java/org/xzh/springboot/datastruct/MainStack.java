package org.xzh.springboot.datastruct;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainStack {
	
	private static final Logger logger = LoggerFactory.getLogger(MainStack.class);

	public static String nixun(final String res){
		StringBuffer result = new StringBuffer();
		
		Stack<Character> stack = new Stack<Character>();
		char[] chars = res.toCharArray();
		for(char c : chars){
			stack.push(c);
		}
		
		while(!stack.isEmpty()){
			result.append(stack.pop());
		}
		
		return result.toString();
	}
	
	public static void main(String[] args) {
		String result = MainStack.nixun("are you ok");
		logger.info(result);
	}
}
