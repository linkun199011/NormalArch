package com.arch.beans;

public class ProjectForDepCheck {

	private String projectId;
	private Integer totalCount;
	private Integer numOfEdit;
	private Integer numOfGroupCheck;
	private Integer numOfDepCheck;
	private Integer numOfPassCheck;
	
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
	 * @return the totalCount
	 */
	public Integer getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the numOfEdit
	 */
	public Integer getNumOfEdit() {
		return numOfEdit;
	}
	/**
	 * @param numOfEdit the numOfEdit to set
	 */
	public void setNumOfEdit(Integer numOfEdit) {
		this.numOfEdit = numOfEdit;
	}
	/**
	 * @return the numOfGroupCheck
	 */
	public Integer getNumOfGroupCheck() {
		return numOfGroupCheck;
	}
	/**
	 * @param numOfGroupCheck the numOfGroupCheck to set
	 */
	public void setNumOfGroupCheck(Integer numOfGroupCheck) {
		this.numOfGroupCheck = numOfGroupCheck;
	}
	/**
	 * @return the numOfDepCheck
	 */
	public Integer getNumOfDepCheck() {
		return numOfDepCheck;
	}
	/**
	 * @param numOfDepCheck the numOfDepCheck to set
	 */
	public void setNumOfDepCheck(Integer numOfDepCheck) {
		this.numOfDepCheck = numOfDepCheck;
	}
	/**
	 * @return the numOfPassCheck
	 */
	public Integer getNumOfPassCheck() {
		return numOfPassCheck;
	}
	/**
	 * @param numOfPassCheck the numOfPassCheck to set
	 */
	public void setNumOfPassCheck(Integer numOfPassCheck) {
		this.numOfPassCheck = numOfPassCheck;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProjectForDepCheck [projectId=" + projectId + ", numOfEdit="
				+ numOfEdit + ", numOfGroupCheck=" + numOfGroupCheck
				+ ", numOfDepCheck=" + numOfDepCheck + ", numOfPassCheck="
				+ numOfPassCheck + "]";
	}
	
	
	
	
}
