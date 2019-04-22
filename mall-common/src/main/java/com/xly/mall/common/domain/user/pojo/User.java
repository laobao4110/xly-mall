package com.xly.mall.common.domain.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xly.mall.common.domain.base.BasePO;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BasePO {

	private Integer userId;   //唯一标识
	private Integer userType;   //用户类型，1为总部人员
	private String userName;   //用户名
	private String realName;   //真实姓名,1为普通用户
	private String password;   //密码
	private String email;   //email
	private String emailVerifyCode;   //email验证码
	private Date emailVerifyTime;   //email验证时间
	private String phone;   //手机号
	private String phoneVerifyCode;   //手机号验证码
	private Date phoneVerifyTime;   //手机验证时间
	private Integer isActivated;   //是否激活，0不可用；1可用
	private Integer isDisabled;   //是否禁用，0启用；1禁用
	private Date registerTime;   //注册时间
	private Date lastLoginTime;   //最后登录时间
	private String lastLoginIp;   //最后登录IP
	private String remark;   //备注
	private Date createTime;   //添加时间
	private String createUser;   //添加人
	private Date updateTime;   //修改时间
	private String updateUser;   //修改人


	public Integer getUserId(){
		return userId;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
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

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public String getCreateUser(){
		return createUser;
	}

	public void setCreateUser(String createUser){
		this.createUser = createUser;
	}

	public Date getUpdateTime(){
		return updateTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateUser(){
		return updateUser;
	}

	public void setUpdateUser(String updateUser){
		this.updateUser = updateUser;
	}

}