package com.arch.beans;

public class AppServiceDetail implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer appServiceDetailId;
	private Integer appServiceId;
	private Integer stepCode;
	private String stepDesc;
	private Integer calledAppId;
	private String calledAppIntf;
	
	/**
	 * @return the appServiceDetailId
	 */
	public Integer getAppServiceDetailId() {
		return appServiceDetailId;
	}
	/**
	 * @param appServiceDetailId the appServiceDetailId to set
	 */
	public void setAppServiceDetailId(Integer appServiceDetailId) {
		this.appServiceDetailId = appServiceDetailId;
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
	 * @return the stepCode
	 */
	public Integer getStepCode() {
		return stepCode;
	}
	/**
	 * @param stepCode the stepCode to set
	 */
	public void setStepCode(Integer stepCode) {
		this.stepCode = stepCode;
	}
	/**
	 * @return the stepDesc
	 */
	public String getStepDesc() {
		return stepDesc;
	}
	/**
	 * @param stepDesc the stepDesc to set
	 */
	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}
	/**
	 * @return the calledAppId
	 */
	public Integer getCalledAppId() {
		return calledAppId;
	}
	/**
	 * @param calledAppId the calledAppId to set
	 */
	public void setCalledAppId(Integer calledAppId) {
		this.calledAppId = calledAppId;
	}
	/**
	 * @return the calledAppIntf
	 */
	public String getCalledAppIntf() {
		return calledAppIntf;
	}
	/**
	 * @param calledAppIntf the calledAppIntf to set
	 */
	public void setCalledAppIntf(String calledAppIntf) {
		this.calledAppIntf = calledAppIntf;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppServiceDetail [appServiceDetailId=" + appServiceDetailId
				+ ", appServiceId=" + appServiceId + ", stepCode=" + stepCode
				+ ", stepDesc=" + stepDesc + ", calledAppId=" + calledAppId
				+ ", calledAppIntf=" + calledAppIntf + "]";
	}

}
