package com.arch.beans;

public class UpAppRelResult implements java.io.Serializable{

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
	private Integer checkNoId;
	private String seqNoId;
	private String operationType;
	private String sourceType;
		
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
	 * @return the checkNoId
	 */
	public Integer getCheckNoId() {
		return checkNoId;
	}
	/**
	 * @param checkNoId the checkNoId to set
	 */
	public void setCheckNoId(Integer checkNoId) {
		this.checkNoId = checkNoId;
	}
	/**
	 * @return the seqNoId
	 */
	public String getSeqNoId() {
		return seqNoId;
	}
	/**
	 * @param seqNoId the seqNoId to set
	 */
	public void setSeqNoId(String seqNoId) {
		this.seqNoId = seqNoId;
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
	 * @return the sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}
	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpAppRelResult [appServiceId=" + appServiceId + ", appName="
				+ appName + ", serviceType=" + serviceType + ", serviceCode="
				+ serviceCode + ", appInnerCode=" + appInnerCode
				+ ", serviceName=" + serviceName + ", serviceDesc="
				+ serviceDesc + ", checkNoId=" + checkNoId + ", seqNoId="
				+ seqNoId + ", operationType=" + operationType
				+ ", sourceType=" + sourceType + "]";
	}
	

	
}
