package com.project.model;

/* 用户类，包括了3个基本属性
 * 具体属性如下：
 * 用于用户登录，识别用户身份
 * */

public class User 
 {
   private String phone;  //用户手机号
   private String code;   //用户登录密码
   private String name;   //用户名
   
   public String getPhone() {
	 return phone;
    } 
   
   public void setPhone(String phone) {
	 this.phone = phone;
    }
   
   public String getCode() {
	 return code;
    }
   
   public void setCode(String code) {
	 this.code = code;
    }

   public String getName() {
	 return name;
    }

   public void setName(String name) {
	 this.name = name;
    }
   
 }
