package com.servlet.tool;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.backstage.user.ManageUser;
import com.backstage.user.ManageVerification;
import com.project.model.User;
import com.project.model.Verification;

/* 身份验证的servlet，用于用户身份验证
 * 第一个方法用于给用户发送验证码
 * 第二个方法用于检测用户输入的验证码
 * */

public class ProvingIdentityServlet extends HttpServlet
  { 
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException
	  {  
		 request.setCharacterEncoding("UTF-8");
		 response.setContentType("text/xml; charset=UTF-8");
	     response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");
		 PrintWriter out = response.getWriter();
		 if(!request.getParameter("phone").equals("")&&!request.getParameter("phone").equals(" "))
		   { 
			 new ManageVerification().deleteVerificationCode(request.getParameter("phone"));
		     Verification verification=new Verification();
		     verification.setPhone(request.getParameter("phone")); //用户接受号码
		     verification.setVerificationcode("666666");           //验证码
		     verification.setType(request.getParameter("type"));   //验证操作类别（登录：注册：绑定银行卡）
		     new ManageVerification().addVerificationCode(verification);
		     out.print("发送成功");
		   }
		 else{out.print("发送失败");}
		 /*phone=request.getParameter("phone");  
         code=new VerificationCode().getCode();
		 if(!phone.equals("")&&!phone.equals(" "))
           new VerificationCode().sendCode(code);
		 else
		   out.print("");*/
	  }
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
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
	     if(user!=null)
	       { 
	    	 if(new ManageVerification().queryVerificationCode(phone, code, type)!=null)
	    	   out.print(user.getName());
	    	 else
	    	   out.print("验证码错误");
	       }
	     else
	       out.print("用户不存在，请注册后登录");
	  }
  }
