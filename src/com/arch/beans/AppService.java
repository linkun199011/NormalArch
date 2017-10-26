package com.arch.beans;

public class AppService implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer appServiceId;
	private Integer appId;
	private Integer serviceTypeId;
	private String serviceCode;
	private String appInnerCode;
	private String serviceName;
	private String serviceDesc;
	private String receiveFile;
	private String getbackFile;
	
	/**
	 * @return the appServiceId
	 */
	public Integer getAppServiceId() {
		return appServiceId;
	}
	/**
	 * @param appServiceId the appServiceId to set
	 */
	public void setAppServiceId(Integer appServiceId) {
		this.appServiceId = appServiceId;
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
	 * @return the serviceTypeId
	 */
	public Integer getServiceTypeId() {
		return serviceTypeId;
	}
	/**
	 * @param serviceTypeId the serviceTypeId to set
	 */
	public void setServiceTypeId(Integer serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}
	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	/**
	 * @return the appInnerCode
	 */
	public String getAppInnerCode() {
		return appInnerCode;
	}
	/**
	 * @param appInnerCode the appInnerCode to set
	 */
	public void setAppInnerCode(String appInnerCode) {
		this.appInnerCode = appInnerCode;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the serviceDesc
	 */
	public String getServiceDesc() {
		return serviceDesc;
	}
	/**
	 * @param serviceDesc the serviceDesc to set
	 */
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	/**
	 * @return the receiveFile
	 */
	public String getReceiveFile() {
		return receiveFile;
	}
	/**
	 * @param receiveFile the receiveFile to set
	 */
	public void setReceiveFile(String receiveFile) {
		this.receiveFile = receiveFile;
	}
	/**
	 * @return the getbackFile
	 */
	public String getGetbackFile() {
		return getbackFile;
	}
	/**
	 * @param getbackFile the getbackFile to set
	 */
	public void setGetbackFile(String getbackFile) {
		this.getbackFile = getbackFile;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppService [appServiceId=" + appServiceId + ", appId=" + appId
				+ ", serviceTypeId=" + serviceTypeId + ", serviceCode="
				+ serviceCode + ", appInnerCode=" + appInnerCode
				+ ", serviceName=" + serviceName + ", serviceDesc="
				+ serviceDesc + ", receiveFile=" + receiveFile
				+ ", getbackFile=" + getbackFile + "]";
	}
		
	
	
}
