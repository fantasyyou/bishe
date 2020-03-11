package com.servlet.user;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.backstage.user.ManageBankCard;
import com.backstage.user.ManageVerification;
import com.project.model.BankCard;

/* 添加银行卡的servlet，用于用户绑定银行卡
 * 第一个方法用于检测用户绑定的银行卡的信息是否正确，并且银行卡是否存在
 * 第二个方法用于验证用户输入的验证码和银行卡的密码是否正确，正确给予绑定，错误返回错误结果
 * */

public class AddBankCardServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException
	  {  
		 request.setCharacterEncoding("UTF-8");
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("application/json");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");
		 PrintWriter out = response.getWriter(); 
		 String cardnumber=request.getParameter("cardnumber");
		 String bank=request.getParameter("bank");
		 String name=request.getParameter("name");
		 BankCard bankcard=new ManageBankCard().verifyBankCard(cardnumber, bank, name);
		 if(bankcard!=null)
		   out.print(bankcard.getBindphone());
		 else
		   out.print("银行卡不存在");
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
		 String cardnumber=request.getParameter("cardnumber");
		 String verificationcode=request.getParameter("verificationcode");
		 String code=request.getParameter("code");
		 String phone=request.getParameter("phone");
		 String bindphone=request.getParameter("bindphone");
		 String type=request.getParameter("type");
		 if(new ManageVerification().queryVerificationCode(bindphone, verificationcode,type)!=null)
		   {
			 if(!code.equals("")&&!code.equals(" ")&&new ManageBankCard().verifyCode(cardnumber, code)!=null)
			   {
				 BankCard bankcard=new BankCard();
				 bankcard.setPhone(phone);
				 bankcard.setCardnumber(cardnumber);
				 new ManageBankCard().updatePhone(bankcard);
				 new ManageVerification().deleteVerificationCode(cardnumber);
				 out.print("绑定成功"); 
			   }
			 else
			   out.print("密码错误"); 
		   }
		 else
		   out.print("验证码错误");
	  }
}
