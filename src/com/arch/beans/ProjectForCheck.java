package com.arch.beans;

public class ProjectForCheck {

	private String projectId;
	private Boolean projectUnderCheck;
	private Integer numOfAppCoopForCheck;
	private Integer numOfAppCoopCompleteCheck;
	private Integer numOfAppCoopUnderCheck;
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
	 * @return the projectUnderCheck
	 */
	public Boolean getProjectUnderCheck() {
		return projectUnderCheck;
	}
	/**
	 * @param projectUnderCheck the projectUnderCheck to set
	 */
	public void setProjectUnderCheck(Boolean projectUnderCheck) {
		this.projectUnderCheck = projectUnderCheck;
	}
	/**
	 * @return the numOfAppCoopForCheck
	 */
	public Integer getNumOfAppCoopForCheck() {
		return numOfAppCoopForCheck;
	}
	/**
	 * @param numOfAppCoopForCheck the numOfAppCoopForCheck to set
	 */
	public void setNumOfAppCoopForCheck(Integer numOfAppCoopForCheck) {
		this.numOfAppCoopForCheck = numOfAppCoopForCheck;
	}
	/**
	 * @return the numOfAppCoopCompleteCheck
	 */
	public Integer getNumOfAppCoopCompleteCheck() {
		return numOfAppCoopCompleteCheck;
	}
	/**
	 * @param numOfAppCoopCompleteCheck the numOfAppCoopCompleteCheck to set
	 */
	public void setNumOfAppCoopCompleteCheck(Integer numOfAppCoopCompleteCheck) {
		this.numOfAppCoopCompleteCheck = numOfAppCoopCompleteCheck;
	}
	/**
	 * @return the numOfAppCoopUnderCheck
	 */
	public Integer getNumOfAppCoopUnderCheck() {
		return numOfAppCoopUnderCheck;
	}
	/**
	 * @param numOfAppCoopUnderCheck the numOfAppCoopUnderCheck to set
	 */
	public void setNumOfAppCoopUnderCheck(Integer numOfAppCoopUnderCheck) {
		this.numOfAppCoopUnderCheck = numOfAppCoopUnderCheck;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProjectForCheck [projectId=" + projectId
				+ ", projectUnderCheck=" + projectUnderCheck
				+ ", numOfAppCoopForCheck=" + numOfAppCoopForCheck
				+ ", numOfAppCoopCompleteCheck=" + numOfAppCoopCompleteCheck
				+ ", numOfAppCoopUnderCheck=" + numOfAppCoopUnderCheck + "]";
	}
}
