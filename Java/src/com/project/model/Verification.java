package com.project.model;

/* 验证类，包括了3个基本属性
 * 具体属性如下：
 * 用于用户身份验证
 * */

public class Verification 
 {
   private String phone;            //用户接受号码
   private String verificationcode; //验证码
   private String type;             //验证操作类别
	
   public String getPhone() {
		return phone;
	}
   public void setPhone(String phone) {
		this.phone = phone;
	}
   public String getVerificationcode() {
		return verificationcode;
	}
   public void setVerificationcode(String verificationcode) {
		this.verificationcode = verificationcode;
	}
   public String getType() {
	    return type;
    }
   public void setType(String type) {
	    this.type = type;
    }
 }
