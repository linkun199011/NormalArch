package com.arch.beans;

public class DicAppServiceType implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer serviceTypeId;
	private String serviceTypeName;
	private String serviceTypeDesc;
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
	 * @return the serviceTypeName
	 */
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	/**
	 * @param serviceTypeName the serviceTypeName to set
	 */
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	/**
	 * @return the serviceTypeDesc
	 */
	public String getServiceTypeDesc() {
		return serviceTypeDesc;
	}
	/**
	 * @param serviceTypeDesc the serviceTypeDesc to set
	 */
	public void setServiceTypeDesc(String serviceTypeDesc) {
		this.serviceTypeDesc = serviceTypeDesc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DicAppServiceType [serviceTypeId=" + serviceTypeId
				+ ", serviceTypeName=" + serviceTypeName + ", serviceTypeDesc="
				+ serviceTypeDesc + "]";
	}

}
