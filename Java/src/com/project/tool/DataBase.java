package com.project.tool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* 数据库连接类，包括了5个基本方法，用于对数据库的连接
 * 第一个方法connect用于数据库连接
 * 第二个方法connection用户关闭数据库连接Connection
 * 第三个方法statement用户关闭Statement
 * 第四个方法resultSet用户关闭ResultSet
 * 第五个方法preparedStatement用户关闭PreparedStatement
 * */

public class DataBase 
 {

	public static Connection connect() 
     {
	   Connection connection = null;
	   String conURL="jdbc:mysql://localhost:3306/image_recognition";
		  try {
			   Class.forName("com.mysql.jdbc.Driver");
		      }
		  catch (java.lang.ClassNotFoundException e) 
		      {
			   System.out.println(e.getMessage());
			  }	     
		  try {
			   connection=DriverManager.getConnection(conURL,"root","root");
		      } 
		  catch (SQLException e) 
		      {
			    e.printStackTrace();
		      }
	   return connection;
     }
	
   public static void connection(Connection connection)
     {
	    try { 
			  if(connection!=null)
			    connection.close();
		    } 
	    catch (SQLException e) 
	        {
			  e.printStackTrace();
	        }
     }
   
   public static void statement(Statement statement)
     {
		try {
			  if(statement!=null)
			    statement.close();
		    } 
	    catch (SQLException e) 
	        {
			  e.printStackTrace();
	        }
     }
   
   public static void resultSet(ResultSet resultset)
     {   
		try {
			  if(resultset!=null)
			    resultset.close();
		    } 
	    catch (SQLException e) 
	        {
			  e.printStackTrace();
	        }
     }
   
   public static void preparedStatement(PreparedStatement preparedstatement)
     {
		try {
			  if(preparedstatement!=null) 
			    preparedstatement.close();
		    } 
	    catch (SQLException e) 
	        {
			  e.printStackTrace();
	        }
     }
 }