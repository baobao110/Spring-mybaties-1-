package com.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Hand {

	private static ApplicationContext context;
	
	public static void init() {
		context = new ClassPathXmlApplicationContext(new String[] {"/spring/config.xml"});//这里配置Spring唯一需要注意的就是spring。xml文件的路径
	}
	
	public static Object getController(String value) {
		System.out.println(value);
		
		return context.getBean(value);
	}

}
