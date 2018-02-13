package com.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fenye.Fenye;
import com.service.CardService;
import com.service.Service;
import com.service.UserService;
import com.user.User;


@Component("user")
public class userControl extends control {
	
	@Autowired
	private Service service;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private CardService cardservice;
	/*
	 * 这里需要特别注意方法的注解不能和类的注解相同,这是因为在后面的自动创建Server对象时,这里的方法
	 * 注解和Service的类注解要相同，这样才能方便自动创建对象,这里方法的注解和对应的Service注解相同
	 * 这样方便构造方法创建相应的对象,但是这里自己在写代码时犯了一个错误就是将Control层的类注解和Service层
	 * 的类注解重名，这样是不可取的因为这样会产生一个问题，在后面自动创建对象时，程序不知道创建哪个对象,因此在这里命名的规则非常重要
	 */

	public String load(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Set factory constraints
		factory.setSizeThreshold(10240);
		
		String src = req.getServletContext().getRealPath("/");
		
		String path=null;
		
		if(src.endsWith(File.separator)) {
			path="WEB-INF";
		}
		else {
			path="/WEB-INF";
		}
		//这里再次验证了路径的问题,运行tomcat时src后面是带/ 但是如果用jetty则相反
		
		factory.setRepository(new File(src +path+"/load-tmp"));

		// Create a new file upload handler
		ServletFileUpload load = new ServletFileUpload(factory);

		// Set overall request size constraint
		load.setSizeMax(1024 * 500);
		// Parse the request
		try {
			List<FileItem> items = load.parseRequest(req);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			    	 String name = item.getFieldName();
			    	 String value = item.getString();
			    } else {
			    	 HttpSession session = req.getSession();
			    	 User user = (User)session.getAttribute("user");
			    	 String fileName = "" + user.getUsername();
			    	 File loadedFile = new File(src +path+"/load/" +  fileName);
			    	 item.write(loadedFile);
			    }
			}
			return toUsercenter(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/*
	 * 文件的上传可以模仿Apache的fileupload
	 */
	
	public void showPicture(HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String src=req.getServletContext().getRealPath("/");
		System.out.println(">>>>>>>"+src);
		HttpSession session=req.getSession();//这里的一个知识点就是session和request的区别一个是全局变量一个是局部变量
		User user=(User)session.getAttribute("user");

		String path=null;
		
		if(src.endsWith(File.separator)) {
			path="WEB-INF";
		}
		else {
			path="/WEB-INF";
		}
		try (FileInputStream in = new FileInputStream(src+path+"/load/" + user.getUsername());
				OutputStream out = resp.getOutputStream()) {
			byte[] data = new byte[1024];
			int length = -1;
			while((length=in.read(data))!=-1) {
				out.write(data, 0, length);
				out.flush();
			}
		}
	}
	
	public String  toRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		return "register";
	
	}
	
	public String Register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();//如果有进行赋值就是原来的Session，如果没有创建新的Session
		String username= req.getParameter("username");
		String psssword= req.getParameter("password");
		User user= userservice.register(username,psssword);
		if(user==null) {
			return "register";
		}
		else {
			session.setAttribute("user",user);
			return "login";
		}
	}
	
	public String toLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		return "login";																																								
	}

	public String  login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		String username= req.getParameter("username");
		String psssword= req.getParameter("password");
		User user=userservice.login(username, psssword);
		if(null==user) {
			return "login";	
		}
		else {
			session.setAttribute("user", user);
			session.setAttribute("username",username);
			return toUsercenter(req, resp);
		}
	}
	
	public String toUsercenter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("--------");
		javax.servlet.http.HttpSession session = req.getSession();
		String username= (String) session.getAttribute("username");
		System.out.println(username);
		String currentPage = req.getParameter("currentPage");
		currentPage=currentPage ==null ? "1" : currentPage;//注意这里的分页后台实现
		Fenye list=cardservice.list(username, Integer.parseInt(currentPage));
		System.out.println("\\\\\\"+list);
		req.setAttribute("fenye", list);
		req.setAttribute("currentPage", Integer.parseInt(currentPage));
		req.setAttribute("username", username);
		return "usercenter";
	}
	/*
	 * 这里出现一个问题如果将 toUsercenter()方法放在抽象类中出现报错的情况，具体原因不太明确,这里可以分析一下
	 */

}
