package com.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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

import com.AccountFlow.Account;
import com.BankCard.Card;
import com.fenye.Fenye;
import com.service.CardService;
import com.service.Service;
import com.user.User;



@Component("card")//������ʵ���Ǽ�ע��,���������и���ע���ҵ���,������Ҫ��Spring��xml�ļ���Ͽ�֮ǰ�İ汾��֪��,�����Ͳ���Ҫ��xml�ļ���������Ϣ
public class cardControl extends control{
	
	@Autowired	//���ע�������Զ��ҵ���ص���,�����Զ��ĳ�ʼ��,�����Ͳ���Ҫ��set�������г�ʼ��,������Խ��Service���@Component
	private Service service;
	
	@Autowired
	private CardService cardservice;

	public void down(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		javax.servlet.http.HttpSession session = req.getSession(); 
		User user=(User) session.getAttribute("user");
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		Card cad=service.Get(number, password);
		String filename = number + ".csv";
		resp.setContentType("application/octet-stream");  
		resp.setHeader("Content-Disposition", "attachment;filename="+ filename);  
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream()))) {
			int currentPage = 1;
			String header = "����,���,��ע,ʱ��";
			bw.write(header);
			bw.newLine();
			bw.flush();
			while (true) {
				Fenye list = service.List(number, password, currentPage);
				System.out.println(currentPage);
				 ArrayList<Account>account=(ArrayList<Account>) list.getObject();
				if(null==account) {
					break;
				}
				if(account.isEmpty()) {//�����ر�ע�ⲻȻ�����������
					break;
				}
				for(Account i:account) {
					StringBuilder text = new StringBuilder();
					text.append(i.getNumber()).append(",")
					.append(i.getMoney()).append(",")
					.append(i.getDescription()).append(",")
					.append(i.getCreatetime()).append(",");
					bw.write(text.toString());
					bw.newLine();
					bw.flush();
					}
				System.out.println(111);
				currentPage++;
			}
		}
	}

	
	
	public String toDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "delete";
	}
	
	public String delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		User user=(User)session.getAttribute("user");
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		Card cad=service.Get(number,password);
		if(cad==null) {
			return null;
		}
		else {
			service.delete(number);
			session.setAttribute("user",user);
			return toUsercenter(req, resp);
		}
	}
	
	public String openAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		User user=(User) session.getAttribute("user");
		String username=user.getUsername();
		String password=req.getParameter("password");
		Card card=service.open(username,password);
		req.setAttribute("card", card);//������ͨ����ֵ�Ե���ʽ�洢��ص���ֵ,����Attribute������set����Ҳ��get����������Ժ�Parameter��������
		return "success";
	}
	
	public String toOpenAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		return "OpenAccount";
	}
	
	public String save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		String b=req.getParameter("money");
		double money=Double.parseDouble(b);
		Card cad=service.Get(number,password);
		if(cad==null) {
			return null;
		}
		else {
			service.save(number, password, money);
			Card card=service.GetCard(number);
			req.setAttribute("card", card);
			return "success";
		}
	}
	
	public String  toSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "save";
	}
	
	public String transfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		String b=req.getParameter("money");
		double money=Double.parseDouble(b);
		Card cad=service.Get(number,password);
		String c=req.getParameter("InNumber");
		int InNumber=Integer.parseInt(c);
		service.transfer(number, password, money, InNumber);
		Card card1=service.GetCard(number);
		Card card2=service.GetCard(InNumber);
		if((card1==null)||(card2==null)) {
			return null;
		}
		else {
			req.setAttribute("card1", card1);
			req.setAttribute("card2", card2);
			return "success2";
		}
	}
	
	public String toTransfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "transfer";
	}
	
	public String  check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		String b=req.getParameter("money");
		double money=Double.parseDouble(b);
		Card cad=service.Get(number,password);
		service.draw(number, password, money);
		Card card=service.GetCard(number);
		if(card==null) {
			return null;
		}
		else {
			req.setAttribute("card", card);
			return "success";
		}
	}
	
	public String toCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "check";
	}
	
	public String list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String currentPage = req.getParameter("currentPage");
			String a=req.getParameter("number");
			int number=Integer.parseInt(a);
			String password=req.getParameter("password");
			Card cad=service.Get(number,password);
			System.out.println(">>>>>>>>1"+cad);
			if(null==cad) {
				return null;
			}
			else {
				Fenye list = service.List(number, password, Integer.parseInt(currentPage));//��ȡ��¼
				System.out.println("22222"+list);
				req.setAttribute("fenye", list);//�����¼
				req.setAttribute("number", number);
				req.setAttribute("password", password);//����֮���Ա���number��password��Ϊ��ǰ��ҳ����ʾ������
				return "list";
			}
		}
	
	public String toList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "list";
	
	}
	
	public String toChangePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "password";
	
	}
	
	public String  password(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String oldPassword= req.getParameter("oldPassword");
		Card cad=service.Get(number,oldPassword);
		if(cad==null) {
			return null;
		}
		else {
			String newPassword= req.getParameter("newPassword");
			Card card=service.ChangePassword(number, oldPassword, newPassword);
			req.setAttribute("card", card);
			return "success";
		}
	}
	
	public String toUsercenter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("--------");
		javax.servlet.http.HttpSession session = req.getSession();
		String username= (String) session.getAttribute("username");
		System.out.println(username);
		String currentPage = req.getParameter("currentPage");
		currentPage=currentPage ==null ? "1" : currentPage;//ע������ķ�ҳ��̨ʵ��
		Fenye list=cardservice.list(username, Integer.parseInt(currentPage));
		System.out.println("\\\\\\"+list);
		req.setAttribute("fenye", list);
		req.setAttribute("currentPage", Integer.parseInt(currentPage));
		req.setAttribute("username", username);
		return "usercenter";
	}

}
