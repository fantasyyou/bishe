package com.backstage.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.project.model.User;
import com.project.tool.DataBase;

/* 管理用户类，包括了3个基本方法，用于对发送的验证码进行管理
 * 第一个方法queryUser用于查询用户是否存在，即查询登录的手机号是否已经注册
 * 第二个方法verificationUser用于用户登录时使用，验证用户的手机号和密码是否正确并且匹配，参数是一个User类的对象
 * 第三个方法addUser用于增加一条用户信息，即用户注册的时候使用，参数是一个User类的对象
 * 第四个方法updateUser用于修改用户的信息，即用户找回密码时，重新设置密码使用
 * */

public class ManageUser 
 {
   public User queryUser(String phone)  //根据用户的手机号查询用户是否存在，参数是用户的手机号
	 {  
	    String query="phone"+"='"+phone+"'";
	    Connection connection = DataBase.connect();
		Statement statement=null;
		ResultSet resultset=null;
		User user=new User();
		try {
			  String sql = "select * from user where "+query;
			  statement = connection.createStatement();
			  resultset = statement.executeQuery(sql);
			  while(resultset.next())
				 {
                   user.setPhone(resultset.getString("phone"));
                   user.setName(resultset.getString("name"));
                   return user;
				 }
		   }	
	    catch (SQLException e) {
		      e.printStackTrace();
			  System.out.println("用户输入的手机号为空");
			  return null;         //用户没有输入数据时，SQL语句会出现错误，导致报错，此时返回结果为null
	       } 
	    finally {
	    	DataBase.connection(connection);
	    	DataBase.statement(statement);
	    	DataBase.resultSet(resultset);
	      }
		return null;
	 }
   
   public User verificationUser(User user)  //检测用户输入的密码是否正确，参数是一个记录用户信息的用户类的对象
	 {  
	    Connection connection = DataBase.connect();
		Statement statement=null;
		ResultSet resultset=null;
		User users=new User();
		try { 
			  String sql = "select * from user where phone="+"'"+user.getPhone()+"'";
			  statement = connection.createStatement();
			  resultset = statement.executeQuery(sql);
			  while(resultset.next())
				 { 
				   if(resultset.getString("code").equals(user.getCode())) {
					   users.setPhone(resultset.getString("phone"));
					   user.setName(resultset.getString("name"));
					   return users;
				     }
                 
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
   
   public void addUser(User user) //添加一条用户信息，参数是一个记录用户信息的用户类的对象
     {
	   Connection connection = DataBase.connect();
	   PreparedStatement preparedstatement=null;
	   try {
			 String sql = "insert into user(phone,code,name) values(?,?,?)";
		     preparedstatement = connection.prepareStatement(sql);	     
		     preparedstatement.setString(1,user.getPhone());
		     preparedstatement.setString(2,user.getCode());
		     preparedstatement.setString(3,user.getName());
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
   
   public void updateUser(User user) //修改用户的登录密码，参数是一个记录用户信息的用户类的对象
     {
	   Connection connection = DataBase.connect();
	   PreparedStatement preparedstatement=null;
	   String sql = "update user set code=? where phone=?";
       try {
  	         preparedstatement = connection.prepareStatement(sql);
		     preparedstatement.setString(1,user.getCode());
		     preparedstatement.setString(2,user.getPhone());
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
