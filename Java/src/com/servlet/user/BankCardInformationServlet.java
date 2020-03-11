package com.servlet.user;
import java.io.IOException;
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
import com.project.model.BankCard;

/* 银行卡信息查询的servlet，用于用户查询绑定的银行卡信息
 * 第一个方法用于检测用户绑定的银行卡的信息是否正确，并且银行卡是否存在
 * 第二个方法用于验证用户输入的验证码和银行卡的密码是否正确，正确给予绑定，错误返回错误结果
 * */

public class BankCardInformationServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException
	  {  
		 request.setCharacterEncoding("UTF-8");
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("application/json");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");		
		 String phone=request.getParameter("phone");  
		 List<BankCard> record=new ArrayList<BankCard>();
		 record=new ManageBankCard().queryBankCardList(phone,"phone");
		 JSONArray jsonarray = new JSONArray();
		 if(record!=null)
		   {
		     for(BankCard bankcard:record)
		       { 
		         JSONObject jsonobject = new JSONObject();
		         jsonobject.put("phone",bankcard.getPhone());
		         jsonobject.put("bindphone",bankcard.getBindphone()); 
		         jsonobject.put("cardnumber",bankcard.getCardnumber());  
		         jsonobject.put("bank",bankcard.getBank());  
		         jsonobject.put("type",bankcard.getType()); 
		         jsonobject.put("name",bankcard.getName()); 
		         jsonobject.put("balance",bankcard.getBalance());  		 
		         jsonarray.put(jsonobject);
		       }		 
		   }
         ServletOutputStream os = response.getOutputStream();
         os.write(jsonarray.toString().getBytes());
         os.flush();
         os.close();	
	  }
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException
	  {  

	  }
}
