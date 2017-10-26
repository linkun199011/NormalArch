package com.arch.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.arch.beans.RelatedAppInfoQuery;
import com.arch.service.ShowSysRel;
import com.arch.util.LogUtil;
import com.opensymphony.xwork2.ActionContext;

public class ShowSysRelAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private String currentAppIdAndName;
	private Integer operationTypeInfo;
	private String showCode;

	// 翻页的参数
	private int pageSize = 10;
	private int pageNo = 1;
	private int recordCount;

	private String qryHint;

	private ShowSysRel showSysRel;

	private List<Object> appInfoList;

	/**
	 * @return the currentAppIdAndName
	 */
	public String getCurrentAppIdAndName() {
		return currentAppIdAndName;
	}

	/**
	 * @param currentAppIdAndName
	 *            the currentAppIdAndName to set
	 */
	public void setCurrentAppIdAndName(String currentAppIdAndName) {
		this.currentAppIdAndName = currentAppIdAndName;
	}

	/**
	 * @return the operationTypeInfo
	 */
	public Integer getOperationTypeInfo() {
		return operationTypeInfo;
	}

	/**
	 * @param operationTypeInfo
	 *            the operationTypeInfo to set
	 */
	public void setOperationTypeInfo(Integer operationTypeInfo) {
		this.operationTypeInfo = operationTypeInfo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the recordCount
	 */
	public int getRecordCount() {
		return recordCount;
	}

	/**
	 * @param recordCount
	 *            the recordCount to set
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @return the qryHint
	 */
	public String getQryHint() {
		return qryHint;
	}

	/**
	 * @param qryHint
	 *            the qryHint to set
	 */
	public void setQryHint(String qryHint) {
		this.qryHint = qryHint;
	}

	/**
	 * @return the showSysRel
	 */
	public ShowSysRel getShowSysRel() {
		return showSysRel;
	}

	/**
	 * @param showSysRel
	 *            the showSysRel to set
	 */
	public void setShowSysRel(ShowSysRel showSysRel) {
		this.showSysRel = showSysRel;
	}

	/**
	 * @return the appInfoList
	 */
	public List<Object> getAppInfoList() {
		return appInfoList;
	}

	/**
	 * @param appInfoList
	 *            the appInfoList to set
	 */
	public void setAppInfoList(List<Object> appInfoList) {
		this.appInfoList = appInfoList;
	}

	/**
	 * @return the showCode
	 */
	public String getShowCode() {
		return showCode;
	}

	/**
	 * @param showCode
	 *            the showCode to set
	 */
	public void setShowCode(String showCode) {
		this.showCode = showCode;
	}

	public String init() {
		ActionContext.getContext().getSession().remove("qryHint");
		ActionContext.getContext().getSession().remove("resultHint");
		return "input";
	}

	public String query() {
		
		LogUtil.info(ShowSysRelAction.class,"操作：ShowSysRelAction.query() called……开始查询……");

		Map<String, Object> session = ActionContext.getContext().getSession();

		// 移除错误提示
		if (session.containsKey("qryHint")) {
			session.remove("qryHint");
		}

		if (session.containsKey(currentAppIdAndName + "_showCode")) {
			session.remove(currentAppIdAndName + "_showCode");
		}

		int startIndex = 0;
		if (pageNo > 1)
			startIndex = (pageNo - 1) * pageSize;

		List<Object> relatedSysList = null;
		RelatedAppInfoQuery arq = new RelatedAppInfoQuery();

		if (null != this.currentAppIdAndName
				&& !"".equals(this.currentAppIdAndName.trim())) {
//			session.put("currentAppIdAndName", this.currentAppIdAndName.trim());
			String currentAppId = this.currentAppIdAndName.split("-")[0];
			Integer appId = Integer.parseInt(currentAppId);
			arq.setAppId(appId);
			arq.setAppName(this.currentAppIdAndName.split("-")[1]);
		} else{
			return "input";
		}

		if (0 == operationTypeInfo) {
			arq.setOperationType("修改中");
		} else {
			arq.setOperationType("基线");
		}

		LogUtil.debug(ShowSysRelAction.class,"查询系统关联关系：" + arq.toString());

		try{
			relatedSysList = showSysRel.listRelatedAppInfoResult(arq);
		}catch(Exception e){
			e.printStackTrace();
			LogUtil.error(ShowSysRelAction.class, "查询系统关联关系异常:"+e.getLocalizedMessage());
			
			qryHint = "查询系统关联关系异常,请勿重复提交查询，稍后再试。";
			session.put("qryHint", qryHint);
			LogUtil.info(ShowSysRelAction.class,qryHint);
			return "input";
			
		}

		if (null == relatedSysList || relatedSysList.size() == 0) {

			qryHint = "无关联系统！";
			session.put("qryHint", qryHint);
			LogUtil.info(ShowSysRelAction.class,qryHint);
			return "input";

		} else {
	
			// 获得展示列表信息
			recordCount = relatedSysList.size();
			int endIndex = pageNo * pageSize;

			if (startIndex + pageSize > relatedSysList.size()) {
				endIndex = relatedSysList.size();
			}

			this.appInfoList = new ArrayList<Object>();

			for (int i = startIndex; i < endIndex; i++) {
				LogUtil.debug(ShowSysRelAction.class,relatedSysList.get(i).toString());
				appInfoList.add(relatedSysList.get(i));
			}
		
		}

		return "input";
	}

	public String draw() {

		LogUtil.info(ShowSysRelAction.class,"操作：ShowSysRelAction.draw() called……开始绘制关联图……");

		showCode = (String) ActionContext.getContext().getSession()
				.get(currentAppIdAndName + "_showCode");
		
		System.out.println(showCode);

		return "draw";
	}

}
