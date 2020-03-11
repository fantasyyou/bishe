package com.servlet.user;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.backstage.user.ManageBankCard;
import com.backstage.user.ManageSchedule;
import com.project.model.BankCard;
import com.project.model.Schedule;

/* 交易转账的servlet，用于用户进行转账操作
 * 第一个方法用于验证用户的银行卡卡号和用户输入的支付密码是否匹配，且存在该账户
 * 第二个方法用于验证用户输入的验证码和银行卡的密码是否正确，正确给予绑定，错误返回错误结果
 * */

public class TransferServlet extends HttpServlet{
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException
	  {  
		 request.setCharacterEncoding("UTF-8");
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("application/json");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");		
		 PrintWriter out = response.getWriter(); 
		 String myaccount=request.getParameter("myaccount"); 
         String code=request.getParameter("code");
		 BankCard bankcard=new ManageBankCard().verifyCode(myaccount,code);
		 if(bankcard!=null)
		   out.print("密码正确");
		 else
		   out.print("密码错误");
	  }
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException
	  {  
		 request.setCharacterEncoding("UTF-8");
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("application/json");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");	
		 PrintWriter out = response.getWriter(); 
		 String money=request.getParameter("money");
		 BankCard myaccount=new ManageBankCard().queryBankCard(request.getParameter("myaccount"));
		 BankCard counteraccount=new ManageBankCard().queryBankCard(request.getParameter("cardnumber"));
		 myaccount.setBalance(String.valueOf(Float.valueOf(myaccount.getBalance())-Float.valueOf(money)));
		 counteraccount.setBalance(String.valueOf(Float.valueOf(counteraccount.getBalance())+Float.valueOf(money)));
		 new ManageBankCard().updateBalance(myaccount);
		 new ManageBankCard().updateBalance(counteraccount);
		 Schedule schedule1=new Schedule();
	 	 Schedule schedule2=new Schedule();
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 schedule1.setTime(df.format(System.currentTimeMillis()));
		 schedule2.setTime(df.format(System.currentTimeMillis()));
		 schedule1.setMyaccount(request.getParameter("myaccount"));
		 schedule2.setMyaccount(request.getParameter("cardnumber"));
		 schedule1.setSummary("银行转账");
		 schedule2.setSummary("银行转账");
		 schedule1.setPlace("中国工商银行");
		 schedule2.setPlace("中国工商银行");
		 schedule1.setCost("-"+Double.valueOf(money));
		 schedule2.setCost("+"+Double.valueOf(money));
		 schedule1.setCurrency("人民币");
		 schedule2.setCurrency("人民币");  
		 schedule1.setBalance(myaccount.getBalance());
		 schedule2.setBalance(counteraccount.getBalance());
		 schedule1.setCounteraccount(request.getParameter("cardnumber"));
		 schedule2.setCounteraccount(request.getParameter("myaccount"));
		 schedule1.setAccountname(counteraccount.getName());
		 schedule2.setAccountname(myaccount.getName());
		 new ManageSchedule().addSchedule(schedule1);
		 new ManageSchedule().addSchedule(schedule2);
		 out.print("支付成功");
	  }
}
