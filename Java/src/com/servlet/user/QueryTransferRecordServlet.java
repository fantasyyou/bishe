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
import com.backstage.user.ManageSchedule;
import com.project.model.Schedule;

/* 查询明细表的servlet，用于查询用户的交易记录
 * 第一个方法用于查询用户的交易记录，并返回结果，若记录为空，返回相应的提示
 * */

public class QueryTransferRecordServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException
	  {  
		 request.setCharacterEncoding("UTF-8");
		 response.setCharacterEncoding("utf-8");
		 response.setContentType("application/json");
		 response.setHeader("Cache-Control", "no-cache");
		 response.setHeader("Pragma", "no-cache");	
		 String myaccount=request.getParameter("myaccount");  
		 List<Schedule> record=new ArrayList<Schedule>();
		 record=new ManageSchedule().queryScheduleList(myaccount);
		 JSONArray jsonarray = new JSONArray();
		 if(record!=null)
		   {
		     for(Schedule schedule:record)
		       {
		         JSONObject jsonobject = new JSONObject();
		         jsonobject.put("id",schedule.getId());
		         jsonobject.put("time",schedule.getTime()); 
		         jsonobject.put("myaccount",schedule.getMyaccount());  
		         jsonobject.put("summary",schedule.getSummary()); 
		         jsonobject.put("place",schedule.getPlace()); 
		         jsonobject.put("cost",schedule.getCost());  	
		         jsonobject.put("currency",schedule.getCurrency());  
		         jsonobject.put("balance",schedule.getBalance()); 
		         jsonobject.put("counteraccount",schedule.getCounteraccount()); 
		         jsonobject.put("accountname",schedule.getAccountname());  
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
