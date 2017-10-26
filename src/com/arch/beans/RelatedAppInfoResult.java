package com.arch.beans;

public class RelatedAppInfoResult implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer appId;
	private String appName;
	private String appShortName;
	private String appCode;
	private String serviceLevel;
	private String category_lv1;
	private String Category_lv2;
	private String systemLevel;
	private String securityLevel;
	private String serviceTime;
	private String relAppPlat;

	/**
	 * @return the relAppPlat
	 */
	public String getRelAppPlat() {
		return relAppPlat;
	}
	/**
	 * @param relAppPlat the relAppPlat to set
	 */
	public void setRelAppPlat(String relAppPlat) {
		this.relAppPlat = relAppPlat;
	}
	/**
	 * @return the appId
	 */
	public Integer getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the appShortName
	 */
	public String getAppShortName() {
		return appShortName;
	}
	/**
	 * @param appShortName the appShortName to set
	 */
	public void setAppShortName(String appShortName) {
		this.appShortName = appShortName;
	}
	/**
	 * @return the appCode
	 */
	public String getAppCode() {
		return appCode;
	}
	/**
	 * @param appCode the appCode to set
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	/**
	 * @return the serviceLevel
	 */
	public String getServiceLevel() {
		return serviceLevel;
	}
	/**
	 * @param serviceLevel the serviceLevel to set
	 */
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	
	/**
	 * @return the category_lv1
	 */
	public String getCategory_lv1() {
		return category_lv1;
	}
	/**
	 * @param category_lv1 the category_lv1 to set
	 */
	public void setCategory_lv1(String category_lv1) {
		this.category_lv1 = category_lv1;
	}
	/**
	 * @return the category_lv2
	 */
	public String getCategory_lv2() {
		return Category_lv2;
	}
	/**
	 * @param category_lv2 the category_lv2 to set
	 */
	public void setCategory_lv2(String category_lv2) {
		Category_lv2 = category_lv2;
	}
	/**
	 * @return the systemLevel
	 */
	public String getSystemLevel() {
		return systemLevel;
	}
	/**
	 * @param systemLevel the systemLevel to set
	 */
	public void setSystemLevel(String systemLevel) {
		this.systemLevel = systemLevel;
	}
	/**
	 * @return the securityLevel
	 */
	public String getSecurityLevel() {
		return securityLevel;
	}
	/**
	 * @param securityLevel the securityLevel to set
	 */
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	/**
	 * @return the serviceTime
	 */
	public String getServiceTime() {
		return serviceTime;
	}
	/**
	 * @param serviceTime the serviceTime to set
	 */
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RelatedAppInfoResult [appId=" + appId + ", appName=" + appName
				+ ", appShortName=" + appShortName + ", appCode=" + appCode
				+ ", serviceLevel=" + serviceLevel + ", category_lv1="
				+ category_lv1 + ", Category_lv2=" + Category_lv2
				+ ", systemLevel=" + systemLevel + ", securityLevel="
				+ securityLevel + ", serviceTime=" + serviceTime
				+ ", relAppPlat=" + relAppPlat + "]";
	}

	

	
	
}
