/**
 * 
 */
package com.arch.beans;

/**
 * @author xiefangkai
 *
 */
public class AppSrvRel implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appSrvRelText;
	private String appName;
	private String serviceDesc;
	private String method;
	
	/**
	 * @return the appSrvRelText
	 */
	public String getAppSrvRelText() {
		return appSrvRelText;
	}

	/**
	 * @param appSrvRelText
	 *            the appSrvRelText to set
	 */
	public void setAppSrvRelText(String appSrvRelText) {
		this.appSrvRelText = appSrvRelText;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the serviceDesc
	 */
	public String getServiceDesc() {
		return serviceDesc;
	}

	/**
	 * @param serviceDesc
	 *            the serviceDesc to set
	 */
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppSrvRel [appSrvRelText=" + appSrvRelText + ", appName="
				+ appName + ", serviceDesc=" + serviceDesc + ", method="
				+ method + "]";
	}
	
	
}
