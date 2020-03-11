package com.project.model;

/* 银行卡类，包括了8个基本属性
 * 具体属性如下:
 * 用于用户查询账户，支付验证，绑定银行卡
 * */

public class BankCard 
 {
   private String phone;       //用户绑定手机号
   private String bindphone;   //用户预留手机号
   private String cardnumber;  //用户银行卡号
   private String code;        //用户支付密码
   private String bank;        //银行类型
   private String type;        //卡号类别
   private String name;        //用户名称
   private String balance;     //银行卡余额
   
   public String getPhone() {
		return phone;
	}
   public void setPhone(String phone) {
		this.phone = phone;
	}
   public String getBindphone() {
		return bindphone;
	}
   public void setBindphone(String bindphone) {
		this.bindphone = bindphone;
	}
   public String getCardnumber() {
		return cardnumber;
	}
   public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
   public String getCode() {
	    return code;
    }
   public void setCode(String code) {
	    this.code = code;
    }  
   public String getBank() {
		return bank;
	}
   public void setBank(String bank) {
		this.bank = bank;
	}  
   public String getType() {
		return type;
	}
   public void setType(String type) {
		this.type = type;
	}
   public String getName() {
		return name;
	}
   public void setName(String name) {
		this.name = name;
	}
   public String getBalance() {
		return balance;
	}
   public void setBalance(String balance) {
		this.balance = balance;
	}
 }
