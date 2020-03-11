package com.servlet.tool;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backstage.user.ManageBankCard;
import com.backstage.user.ManageUser;
import com.backstage.user.ManageVerification;
import com.project.model.BankCard;
import com.project.model.User;
 
/* 用户注册的servlet，用于用户进行注册
 * 第一个方法用于添加用户的注册信息
 * */

public class RegisterUserServlet extends HttpServlet
  { 
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException
	  {  
         request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/xml; charset=UTF-8");
	     response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");
		 PrintWriter out = response.getWriter();
		 String phone=request.getParameter("phone");   //用户接受号码
		 String code=request.getParameter("code");     //用户输入的验证码
		 String type=request.getParameter("type");     //验证操作类别（登录：注册：绑定银行卡，这里是注册）
		 User user=new ManageUser().queryUser(phone);
         if(user==null)
	       { 
	    	 if(new ManageVerification().queryVerificationCode(phone, code, type)!=null)
	    	   { 
	    		 List<BankCard> record=new ArrayList<BankCard>();
	    		 record=new ManageBankCard().queryBankCardList(phone,"bind_phone"); //根据预留手机号查询
	    		 if(record!=null)
	    		   {
	    			 User users=new User();
	    			 users.setPhone(phone);
	    			 users.setCode("fantasy.5282");
	    			 users.setName(record.get(0).getName());
	    			 new ManageUser().addUser(users);
	    			 out.print("注册成功");
	    		   }
	    		 else
	    		   out.print("注册失败，该手机未绑定已开户的银行卡或手机号为空");
	    	   }
	    	 else
	    	   out.print("验证码错误");
	       }
         else
	       out.print("用户已经注册");
	  }  

  }
