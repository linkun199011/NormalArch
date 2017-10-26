package com.arch.beans;

public class UpAppRelQry implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer appServiceId;
	private String operationType;
	
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpAppRelQry [appServiceId=" + appServiceId + ", operationType="
				+ operationType + "]";
	}

	
	
}
