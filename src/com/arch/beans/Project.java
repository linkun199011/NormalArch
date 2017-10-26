package com.arch.beans;

public class Project implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String projectNo;
	private String projectName;
	private String projectManager;
	private String techManager;
	private String busiManager;
	private String testManager;
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectManager
	 */
	public String getProjectManager() {
		return projectManager;
	}
	/**
	 * @param projectManager the projectManager to set
	 */
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	/**
	 * @return the techManager
	 */
	public String getTechManager() {
		return techManager;
	}
	/**
	 * @param techManager the techManager to set
	 */
	public void setTechManager(String techManager) {
		this.techManager = techManager;
	}
	/**
	 * @return the busiManager
	 */
	public String getBusiManager() {
		return busiManager;
	}
	/**
	 * @param busiManager the busiManager to set
	 */
	public void setBusiManager(String busiManager) {
		this.busiManager = busiManager;
	}
	/**
	 * @return the testManager
	 */
	public String getTestManager() {
		return testManager;
	}
	/**
	 * @param testManager the testManager to set
	 */
	public void setTestManager(String testManager) {
		this.testManager = testManager;
	}
	/**
	 * @return the projectNo
	 */
	public String getProjectNo() {
		return projectNo;
	}
	/**
	 * @param projectNo the projectNo to set
	 */
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Project [projectName=" + projectName + ", projectManager="
				+ projectManager + ", techManager=" + techManager
				+ ", busiManager=" + busiManager + ", testManager="
				+ testManager + "]";
	}

}
