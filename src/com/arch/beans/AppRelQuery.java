package com.arch.beans;

public class AppRelQuery implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int startIndex;
	private int length;
	private Integer appId;
	private String serviceName;
	private String serviceCode;
	private String appInnerCode;
	private Integer operationType;
	private String projectId;
	private String flowStatus;
	
	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
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
	 * @return the operationType
	 */
	public Integer getOperationType() {
		return operationType;
	}
	/**
	 * @param operationType the operationType to set
	 */
	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the appId
	 */
	public Integer getAppId(){	
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(Integer appId) {
		this.appId = appId;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppRelQuery [startIndex=" + startIndex + ", length=" + length
				+ ", appId=" + appId + ", serviceName=" + serviceName
				+ ", serviceCode=" + serviceCode + ", appInnerCode="
				+ appInnerCode + ", operationType=" + operationType
				+ ", projectId=" + projectId + ", flowStatus=" + flowStatus
				+ "]";
	}

	
	
	
	
}
