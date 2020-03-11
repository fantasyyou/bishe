package com.project.model;

/* 明细表类，包括了10个基本属性
 * 具体属性如下:
 * 用于用户查询明细表，系统添加交易记录
 * */

public class Schedule 
 {
   private String id;             //交易编号（数据库自动递增）
   private String time;           //交易时间
   private String myaccount;      //֧交易卡号
   private String summary;        //业务摘要
   private String place;          //交易场所
   private String cost;           //交易金额
   private String currency;       //交易币种
   private String balance;        //卡号余额
   private String counteraccount; //对方账户
   private String accountname;    //对方户名
   
   public String getId() {
		return id;
	}
   public void setId(String id) {
		this.id = id;
	}
   public String getTime() {
		return time;
	}
   public void setTime(String time) {
		this.time = time;
	}
   public String getMyaccount() {
		return myaccount;
	}           
   public void setMyaccount(String myaccount) {
		this.myaccount = myaccount;
	}
   public String getSummary() {
		return summary;
	}
   public void setSummary(String summary) {
		this.summary = summary;
	}
   public String getPlace() {
		return place;
	}
  public void setPlace(String place) {
		this.place = place;
	}
   public String getCost() {
		return cost;
	}
   public void setCost(String cost) {
		this.cost = cost;
	}
   public String getCurrency() {
		return currency;
	}
   public void setCurrency(String currency) {
		this.currency = currency;
	}
   public String getBalance() {
		return balance;
	}
   public void setBalance(String balance) {
		this.balance = balance;
	}
   public String getCounteraccount() {
		return counteraccount;
	}
   public void setCounteraccount(String counteraccount) {
		this.counteraccount = counteraccount;
	}
   public String getAccountname() {
		return accountname;
	}
   public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
   
 }
