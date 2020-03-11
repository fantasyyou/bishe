package com.backstage.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.project.model.Schedule;
import com.project.tool.DataBase;

/* 明细表管理类，包括了3个基本方法，用于对交易记录进行管理
 * 第一个方法querySchedule用于查询明细表的信息，返回的是一个包含明细表信息的Schedule类的对象
 * 第二个方法queryScheduleList用于查询明细表的信息，返回的是一组包含明细表信息的Schedule类的对象数组
 * 第三个方法addSchedule用于添加一条明细记录，即交易转账时使用，交易转账会产生一条记录
 * */

public class ManageSchedule 
 {
   public Schedule querySchedule(String id)  //根据交易记录的ID查询交易信息，参数是交易记录的ID
	 {  
	    Connection connection = DataBase.connect();
		Statement statement=null;
		ResultSet resultset=null;
		Schedule schedule=new Schedule();
		try {
			  String sql = "select * from schedule where id="+id;
			  statement = connection.createStatement();
			  resultset = statement.executeQuery(sql);
			  while(resultset.next())
				 {
				   schedule.setId(resultset.getString("id"));
				   schedule.setTime(resultset.getString("time"));
				   schedule.setMyaccount(resultset.getString("myaccount"));
				   schedule.setSummary(resultset.getString("summary"));
				   schedule.setPlace(resultset.getString("place"));
				   schedule.setCost(resultset.getString("cost"));
				   schedule.setCurrency(resultset.getString("currency"));
				   schedule.setBalance(resultset.getString("balance"));
				   schedule.setCounteraccount(resultset.getString("counteraccount"));
				   schedule.setAccountname(resultset.getString("account_name"));
                   return schedule;
				 }
		   }	
	    catch (SQLException e) {
		      e.printStackTrace();
	       } 
	    finally {
	    	DataBase.connection(connection);
	    	DataBase.statement(statement);
	    	DataBase.resultSet(resultset);
	      }
		return null;
	 }

   public List<Schedule> queryScheduleList(String myaccount)  //根据用户账户查询交易记录，参数是用户的银行账户的卡号
     {
	   Connection connection = DataBase.connect();
	   Statement statement=null;
	   ResultSet resultset=null;
	   List<Schedule> record=new ArrayList<Schedule>();
	   try {
			 String sql = "select * from schedule where myaccount="+myaccount;
			 statement = connection.createStatement();
			 resultset = statement.executeQuery(sql);
			 while(resultset.next())
				{ 
				  Schedule schedule=new Schedule();
				  schedule.setId(resultset.getString("id"));
				  schedule.setTime(resultset.getString("time"));
				  schedule.setMyaccount(resultset.getString("myaccount"));
				  schedule.setSummary(resultset.getString("summary"));
				  schedule.setPlace(resultset.getString("place"));
				  schedule.setCost(resultset.getString("cost"));
				  schedule.setCurrency(resultset.getString("currency"));
				  schedule.setBalance(resultset.getString("balance"));
				  schedule.setCounteraccount(resultset.getString("counteraccount"));
				  schedule.setAccountname(resultset.getString("account_name"));
				  record.add(schedule);
				}
	       }     
	   catch (SQLException e) {
		     e.printStackTrace();
	       } 
	   finally { 
	        DataBase.connection(connection);
	    	DataBase.statement(statement);
	    	DataBase.resultSet(resultset);
	      }
       if(record.size()!=0)
	     return record;
       else
         return null;    
     }
   
   public void addSchedule(Schedule schedule)  //增加一条交易记录，参数是一个记录着交易信息的明细表类的对象
     {
	   Connection connection = DataBase.connect();
	   PreparedStatement preparedstatement=null;
	   try {
			 String sql = "insert into schedule(time,myaccount,summary,place,cost,currency,balance,counteraccount,account_name) "
			 		    + "values(?,?,?,?,?,?,?,?,?)";
		     preparedstatement = connection.prepareStatement(sql);	     
		     preparedstatement.setString(1,schedule.getTime());
		     preparedstatement.setString(2,schedule.getMyaccount());
		     preparedstatement.setString(3,schedule.getSummary());
		     preparedstatement.setString(4,schedule.getPlace());
		     preparedstatement.setString(5,schedule.getCost());
		     preparedstatement.setString(6,schedule.getCurrency());
		     preparedstatement.setString(7,schedule.getBalance());
		     preparedstatement.setString(8,schedule.getCounteraccount());
		     preparedstatement.setString(9,schedule.getAccountname());
		     preparedstatement.executeUpdate();
	       }     
	   catch (SQLException e) {
		     e.printStackTrace();
	       } 
	   finally { 
	    	DataBase.connection(connection);
		    DataBase.preparedStatement(preparedstatement);
	      }
     }
 }
