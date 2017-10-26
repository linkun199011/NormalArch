package com.arch.beans;

import com.rongji.dfish.framework.handler.xml.PubUserInfo;

public class User implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PubUserInfo userInfo;
	private String userLoginFailureMsg;
	/**
	 * @return the userInfo
	 */
	public PubUserInfo getUserInfo() {
		return userInfo;
	}
	/**
	 * @param userInfo the userInfo to set
	 */
	public void setUserInfo(PubUserInfo userInfo) {
		this.userInfo = userInfo;
	}
	/**
	 * @return the userLoginFailureMsg
	 */
	public String getUserLoginFailureMsg() {
		return userLoginFailureMsg;
	}
	/**
	 * @param userLoginFailureMsg the userLoginFailureMsg to set
	 */
	public void setUserLoginFailureMsg(String userLoginFailureMsg) {
		this.userLoginFailureMsg = userLoginFailureMsg;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userInfo=" + userInfo + ", userLoginFailureMsg="
				+ userLoginFailureMsg + "]";
	} 
	
	
	
}
