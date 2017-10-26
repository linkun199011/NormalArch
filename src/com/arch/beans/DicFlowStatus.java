package com.arch.beans;

public class DicFlowStatus implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer flowStatusId;
	private String flowStatus;

	
	/**
	 * @return the flowStatusId
	 */
	public Integer getFlowStatusId() {
		return flowStatusId;
	}

	/**
	 * @param flowStatusId the flowStatusId to set
	 */
	public void setFlowStatusId(Integer flowStatusId) {
		this.flowStatusId = flowStatusId;
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
		return "DicFlowStatus [flowStatusId=" + flowStatusId + ", flowStatus="
				+ flowStatus + "]";
	}

	
}
