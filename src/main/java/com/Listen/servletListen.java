package com.Listen;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.handler.Hand;

public class servletListen implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("-------servletListen end");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("-------servletListen init");
		Hand.init();
	}

}
