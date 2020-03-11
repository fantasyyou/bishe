package com.backstage.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.project.model.BankCard;
import com.project.tool.DataBase;

/* 管理银行卡类，包括了6个基本方法，用于对银行卡信息进行查询以及修改
 * 第一个方法queryBankCardList用于查询用户绑定的银行卡的信息，参数是用户登录的手机号
 * 第二个方法queryBankCard用于查询用户绑定的银行卡的信息，参数是银行卡卡号
 * 第三个方法verifyCode用于检测用户输入的支付密码是否正确，参数是银行卡卡号，用户输入的支付密码
 * 第四个方法verifyBankCard用于检测银行卡是否存在，参数是银行卡卡号，类型，用户名
 * 第五个方法updatePhone用于修改银行卡的绑定手机号，即添加银行卡时使用，参数是修改信息后的银行卡类对象
 * 第六个方法updateBalance用于修改银行卡的余额，即交易转账时使用，参数是修改信息后的银行卡类对象
 * 注:第一个方法返回的是数组，用户的所有银行卡信息，用于用户查询使用
 *    第二个方法返回的是单张银行卡信息，用于修改银行卡信息时使用，先读取，修改之后，重新写入
 * */

public class ManageBankCard 
 {
   public List<BankCard> queryBankCardList(String phone,String type)  //查询用户绑定的银行卡的信息，参数是用户手机号
     {
	   Connection connection = DataBase.connect();
	   Statement statement=null;
	   ResultSet resultset=null;
	   List<BankCard> record=new ArrayList<BankCard>();
	   try {
			 String sql = "select * from bank_card where "+type+"="+phone;
			 statement = connection.createStatement();
			 resultset = statement.executeQuery(sql);
			 while(resultset.next())
				{ 
				  BankCard bankcard=new BankCard();
				  bankcard.setPhone(resultset.getString("phone"));
				  bankcard.setBindphone(resultset.getString("bind_phone"));	
				  bankcard.setBank(resultset.getString("bank"));
				  bankcard.setType(resultset.getString("type"));
				  bankcard.setCardnumber(resultset.getString("card_number"));
				  bankcard.setName(resultset.getString("name"));
				  bankcard.setBalance(resultset.getString("balance"));
				  record.add(bankcard);
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

   public BankCard queryBankCard(String card_number)  //查询单张银行卡的信息，参数是银行卡卡号
     {
	   Connection connection = DataBase.connect();
	   Statement statement=null;
	   ResultSet resultset=null;
	   BankCard bankcard=new BankCard();
	   try {
			 String sql = "select * from bank_card where card_number="+card_number;
			 statement = connection.createStatement();
			 resultset = statement.executeQuery(sql);
			 while(resultset.next())
				{ 
				  bankcard.setPhone(resultset.getString("phone"));
				  bankcard.setBindphone(resultset.getString("bind_phone"));	
				  bankcard.setBank(resultset.getString("bank"));
				  bankcard.setType(resultset.getString("type"));
				  bankcard.setCardnumber(resultset.getString("card_number"));
				  bankcard.setName(resultset.getString("name"));
				  bankcard.setBalance(resultset.getString("balance"));
				  return bankcard;
				}
	       }     
	   catch (SQLException e) {
		     e.printStackTrace();
		     return null;
	       } 
	   finally { 
	        DataBase.connection(connection);
	    	DataBase.statement(statement);
	    	DataBase.resultSet(resultset);
	      }
       return null;
     }
   
   public BankCard verifyCode(String card_number,String code)  //验证用户输入的支付密码是否正确，参数是银行卡卡号和输入的密码
     {
	   Connection connection = DataBase.connect();
	   Statement statement=null;
	   ResultSet resultset=null;
	   BankCard bankcard=new BankCard();
	   try {
			 String sql = "select * from bank_card where card_number="+card_number+" and code="+"'"+code+"'";
			 statement = connection.createStatement();
			 resultset = statement.executeQuery(sql);
			 while(resultset.next())
				{ 
				  bankcard.setPhone(resultset.getString("phone"));
				  bankcard.setBindphone(resultset.getString("bind_phone"));	
				  bankcard.setBank(resultset.getString("bank"));
				  bankcard.setType(resultset.getString("type"));
				  bankcard.setCardnumber(resultset.getString("card_number"));
				  bankcard.setName(resultset.getString("name"));
				  bankcard.setBalance(resultset.getString("balance"));
				  return bankcard;
				}
	       }     
	   catch (SQLException e) {
		     e.printStackTrace();
		     return null;
	       } 
	   finally { 
	        DataBase.connection(connection);
	    	DataBase.statement(statement);
	    	DataBase.resultSet(resultset);
	      }
       return null;
     }
                                                                  
   public BankCard verifyBankCard(String card_number,String bank,String name)  //检测银行卡是否存在，参数是
     {                                                                         //银行卡卡号，类型，用户名
	   Connection connection = DataBase.connect();
	   Statement statement=null;
	   ResultSet resultset=null;
	   BankCard bankcard=new BankCard();
	   try {
			 String sql = "select * from bank_card where card_number="+"'"+card_number+"'";
			 statement = connection.createStatement();
			 resultset = statement.executeQuery(sql);
			 while(resultset.next())
				{ 
				  if(!bank.equals(resultset.getString("bank"))||!name.equals(resultset.getString("name")))
					  continue;
				  bankcard.setPhone(resultset.getString("phone"));
				  bankcard.setBindphone(resultset.getString("bind_phone"));	
				  bankcard.setBank(resultset.getString("bank"));
				  bankcard.setType(resultset.getString("type"));
				  bankcard.setCardnumber(resultset.getString("card_number"));
				  bankcard.setName(resultset.getString("name"));
				  bankcard.setBalance(resultset.getString("balance"));
				  return bankcard;
				}
	       }     
	   catch (SQLException e) {
		     e.printStackTrace();
		     return null;
	       } 
	   finally { 
	        DataBase.connection(connection);
	    	DataBase.statement(statement);
	    	DataBase.resultSet(resultset);
	      }
       return null;
     }
   
   public void updatePhone(BankCard bankcard)  //修改银行卡的绑定手机号，参数是修改信息后的银行卡类对象
     {
	   Connection connection = DataBase.connect();
	   PreparedStatement preparedstatement=null;
	   String sql = "update bank_card set phone=? where card_number=?";
	   try {
	         preparedstatement = connection.prepareStatement(sql);
		     preparedstatement.setString(1,bankcard.getPhone());
		     preparedstatement.setString(2,bankcard.getCardnumber());
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
   
   public void updateBalance(BankCard bankcard) //修改银行卡的余额，参数是修改信息后的银行卡类对象
     {
	   Connection connection = DataBase.connect();
	   PreparedStatement preparedstatement=null;
	   String sql = "update bank_card set balance=? where card_number=?";
       try {
	         preparedstatement = connection.prepareStatement(sql);
		     preparedstatement.setString(1,bankcard.getBalance());
		     preparedstatement.setString(2,bankcard.getCardnumber());
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
