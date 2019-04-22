package com.xly.mall.dataaccess.domain.user;


import com.xly.mall.common.base.BaseDO;

import java.util.Date;


public class UserDO  extends BaseDO {

	private Integer id;
	private Integer userType;
	private String userName;
	private String realName;
	private String password;
	private String email;
	private String emailVerifyCode;
	private Date emailVerifyTime;
	private String phone;
	private String phoneVerifyCode;
	private Date phoneVerifyTime;
	private Integer isActivated;
	private Integer isDisabled;
	private Date registerTime;
	private Date lastLoginTime;
	private String lastLoginIp;
	private String remark;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getUserType(){
		return userType;
	}

	public void setUserType(Integer userType){
		this.userType = userType;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getRealName(){
		return realName;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmailVerifyCode(){
		return emailVerifyCode;
	}

	public void setEmailVerifyCode(String emailVerifyCode){
		this.emailVerifyCode = emailVerifyCode;
	}

	public Date getEmailVerifyTime(){
		return emailVerifyTime;
	}

	public void setEmailVerifyTime(Date emailVerifyTime){
		this.emailVerifyTime = emailVerifyTime;
	}

	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhoneVerifyCode(){
		return phoneVerifyCode;
	}

	public void setPhoneVerifyCode(String phoneVerifyCode){
		this.phoneVerifyCode = phoneVerifyCode;
	}

	public Date getPhoneVerifyTime(){
		return phoneVerifyTime;
	}

	public void setPhoneVerifyTime(Date phoneVerifyTime){
		this.phoneVerifyTime = phoneVerifyTime;
	}

	public Integer getIsActivated(){
		return isActivated;
	}

	public void setIsActivated(Integer isActivated){
		this.isActivated = isActivated;
	}

	public Integer getIsDisabled(){
		return isDisabled;
	}

	public void setIsDisabled(Integer isDisabled){
		this.isDisabled = isDisabled;
	}

	public Date getRegisterTime(){
		return registerTime;
	}

	public void setRegisterTime(Date registerTime){
		this.registerTime = registerTime;
	}

	public Date getLastLoginTime(){
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp(){
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp){
		this.lastLoginIp = lastLoginIp;
	}

	public String getRemark(){
		return remark;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

}