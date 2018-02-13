package com.Listen;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class requestListen implements ServletRequestListener {

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("------request end-------");
	}

	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("-------request init-----");
		System.out.println(arg0.getServletRequest().getServletContext());
	}

}
