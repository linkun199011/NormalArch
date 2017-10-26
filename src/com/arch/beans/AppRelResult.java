package com.arch.beans;

public class AppRelResult implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer appServiceId;
	private String appName;
	private String serviceType;
	private String serviceCode;
	private String appInnerCode;
	private String serviceName;
	private String serviceDesc;
	private String receiveFile;
	private String getbackFile;
	private String modifyUserId;
	private String modifyUserName;
	private String operationType;
	private String flowStatus;
	
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
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}
	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	/**
	 * @return the flowStatus
	 */
	public String getFlowStatus() {
		return flowStatus;
	}
	/**
	 * @param flowStatus the flowStatus to set
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}
	/**
	 * @return the modifyUserId
	 */
	public String getModifyUserId() {
		return modifyUserId;
	}
	/**
	 * @param modifyUserId the modifyUserId to set
	 */
	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}
	/**
	 * @return the modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	/**
	 * @param modifyUserName the modifyUserName to set
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppRelResult [appServiceId=" + appServiceId + ", appName="
				+ appName + ", serviceType=" + serviceType + ", serviceCode="
				+ serviceCode + ", appInnerCode=" + appInnerCode
				+ ", serviceName=" + serviceName + ", serviceDesc="
				+ serviceDesc + ", receiveFile=" + receiveFile
				+ ", getbackFile=" + getbackFile + ", modifyUserId="
				+ modifyUserId + ", modifyUserName=" + modifyUserName
				+ ", operationType=" + operationType + ", flowStatus="
				+ flowStatus + "]";
	}

}
