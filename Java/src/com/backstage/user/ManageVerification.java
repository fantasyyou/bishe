package com.backstage.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.project.model.Verification;
import com.project.tool.DataBase;

/* 管理验证类，包括了3个基本方法，用于对发送的验证码进行管理
 * 第一个方法queryVerificationCode用于检测用户输入的验证码是否正确，参数是接受的手机号和用户输入的验证码
 * 第二个方法addVerificationCode用于添加一条验证码信息，即发送验证码之后保存此条记录
 * 第三个方法deleteVerificationCode用于删除一条验证码信息，即验证完之后删除此条记录
 * */

public class ManageVerification 
  {
   public Verification queryVerificationCode(String phone,String verificationcode,String type) //检测用户输入的验证码是否正确
     {                                                                                         //参数是用户接受验证码的手机号,
	                                                                                           //输入的验证码，验证码操作类型
	   Connection connection = DataBase.connect();                                             
	   Statement statement=null;
	   ResultSet resultset=null;
	   Verification verification=new Verification();
	   try { 
			 String sql = "select * from verification where phone="+"'"+phone+"'"+
	                      " and verificationcode="+"'"+verificationcode+"'"+" and type="+"'"+type+"'";
			 statement = connection.createStatement();
			 resultset = statement.executeQuery(sql);
			 while(resultset.next())
				{ 
				  verification.setPhone(resultset.getString("phone"));
				  verification.setVerificationcode(resultset.getString("verificationcode"));
				  verification.setType(resultset.getString("type"));
				  return verification;
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
   
   
   public void addVerificationCode(Verification verification)  //添加一条验证码信息，参数是一个记录验证码信息的验证码类的对象
     {
	   Connection connection = DataBase.connect();
	   PreparedStatement preparedstatement=null;
	   try {
			 String sql = "insert into verification(phone,verificationcode,type) values(?,?,?)";
		     preparedstatement = connection.prepareStatement(sql);	     
		     preparedstatement.setString(1,verification.getPhone());
		     preparedstatement.setString(2,verification.getVerificationcode());
		     preparedstatement.setString(3,verification.getType());
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
   
   public void deleteVerificationCode(String phone)  //删除一条验证码信息，参数是一个记录验证码信息的验证码类的对象
     {
	   Connection connection = DataBase.connect();
	   PreparedStatement preparedstatement=null;
	   try {
			 String sql = "delete from verification where phone="+phone;
		     preparedstatement = connection.prepareStatement(sql);	     
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