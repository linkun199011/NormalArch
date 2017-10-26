/**
 * 
 */
package com.arch.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.arch.beans.UpAppRelQry;
import com.arch.beans.UpAppRelResult;
import com.arch.service.ShowAppRel;
import com.arch.util.LogUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author xiefangkai
 *
 */
public class ShowAppRelAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private ShowAppRel showAppRel;
	private Integer serviceId;
	private String showCode;
	private String operationType;
	private List<Object> upAppRelResultList;
	private List<Object> upAppNameList;
	private String selectedAppName;
	
	// 翻页的参数
	private int pageSize = 5;
	private int pageNo = 1;
	private int recordCount;
	
	private Integer checkNoId;
	private String seqNoId;
	
	private String qryHint;

	/**
	 * @return the resultHint
	 */
	public String getQryHint() {
		return qryHint;
	}

	/**
	 * @param resultHint the resultHint to set
	 */
	public void setQryHint(String qryHint) {
		this.qryHint = qryHint;
	}

	/**
	 * @return the checkNoId
	 */
	public Integer getCheckNoId() {
		return checkNoId;
	}

	/**
	 * @param checkNoId the checkNoId to set
	 */
	public void setCheckNoId(Integer checkNoId) {
		this.checkNoId = checkNoId;
	}

	/**
	 * @return the seqNoId
	 */
	public String getSeqNoId() {
		return seqNoId;
	}

	/**
	 * @param seqNoId the seqNoId to set
	 */
	public void setSeqNoId(String seqNoId) {
		this.seqNoId = seqNoId;
	}

	/**
	 * @return the upAppRelResultList
	 */
	public List<Object> getUpAppRelResultList() {
		return upAppRelResultList;
	}

	/**
	 * @param upAppRelResultList the upAppRelResultList to set
	 */
	public void setUpAppRelResultList(List<Object> upAppRelResultList) {
		this.upAppRelResultList = upAppRelResultList;
	}

	/**
	 * @return the showAppRel
	 */
	public ShowAppRel getShowAppRel() {
		return showAppRel;
	}

	/**
	 * @param showAppRel
	 *            the showAppRel to set
	 */
	public void setShowAppRel(ShowAppRel showAppRel) {
		this.showAppRel = showAppRel;
	}

	/**
	 * @return the serviceId
	 */
	public Integer getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return the showCode
	 */
	public String getShowCode() {
		return showCode;
	}

	/**
	 * @param showCode the showCode to set
	 */
	public void setShowCode(String showCode) {
		this.showCode = showCode;
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

	/**
	 * @return the upAppNameList
	 */
	public List<Object> getUpAppNameList() {
		return upAppNameList;
	}

	/**
	 * @param upAppNameList the upAppNameList to set
	 */
	public void setUpAppNameList(List<Object> upAppNameList) {
		this.upAppNameList = upAppNameList;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
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
	 * @param pageNo the pageNo to set
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
	 * @param recordCount the recordCount to set
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @return the selectedAppName
	 */
	public String getSelectedAppName() {
		return selectedAppName;
	}

	/**
	 * @param selectedAppName the selectedAppName to set
	 */
	public void setSelectedAppName(String selectedAppName) {
		this.selectedAppName = selectedAppName;
	}

	public String init(){
		ActionContext.getContext().getSession().remove("qryHint");
		ActionContext.getContext().getSession().remove("resultHint");
		return "input";
	}
		
	public String down() {
				
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		if(session.containsKey("qryHint")){
			session.remove("qryHint");
		}
		
		if(null != serviceId){
			session.put("serviceId", serviceId);
		}else{
			
			if(session.containsKey("serviceId")){
				serviceId = (Integer) session.get("serviceId");
			}	
		}
		
		if(null != operationType && !"".equals(operationType.trim())){
			session.put("operationType", operationType);
		}else{
			
			if(session.containsKey("operationType")){
				operationType = (String) session.get("operationType");
			}
		}	

		LogUtil.info(ShowAppRelAction.class,"操作：ShowAppRelAction.down() called "+ serviceId+","+operationType);
		
		String showCode = showAppRel.getDownAppRelInfo(serviceId,operationType);
		
		if(null == showCode || "".equals(showCode.trim())){
			qryHint = "本服务无下游系统！";
			
			session.put("qryHint", qryHint);
			
		}else{
			qryHint = "";
		}
		
		setShowCode(showCode);
		LogUtil.info(ShowAppRelAction.class,"获取showcode:"+showCode);
		
		return "down";
		
	}
	
	public String up(){
		
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		int startIndex=0;
		if (pageNo>1) startIndex=(pageNo - 1)*pageSize;
		
		if(session.containsKey("qryHint")){
			session.remove("qryHint");
		}
		
		if(null != serviceId){
			session.put("serviceId", serviceId);
		}else{
			
			if(session.containsKey("serviceId")){
				serviceId = (Integer) session.get("serviceId");
			}
			
		}
		
		if(null != operationType && !"".equals(operationType.trim())){
			session.put("operationType", operationType);
		}else{
			
			if(session.containsKey("operationType")){
				operationType = (String) session.get("operationType");
			}
			
		}	
		
		LogUtil.info(ShowAppRelAction.class,"操作：ShowAppRelAction.up() called "+ serviceId+","+operationType);
		
		UpAppRelQry arq = new UpAppRelQry();
		arq.setAppServiceId(serviceId);
		arq.setOperationType(operationType);
		LogUtil.info(ShowAppRelAction.class,"查询上游条件："+arq.toString());
		
		Map<String, Object> resultMap = showAppRel.listUpAppRelResult(arq);
		List<Object> totalList = null;
		if (null == resultMap){
			
			qryHint = "系统异常，请稍后再试！";
			session.put("qryHint", qryHint);
			
		}else{
			
			totalList =  (List<Object>) resultMap.get("upAppRelResult");

			if(null == totalList){
				qryHint = "本服务无上游系统！";
				
				session.put("qryHint", qryHint);
			}else{
				
				for(Object o : totalList){
					LogUtil.info(ShowAppRelAction.class,"TotalList info:"+((UpAppRelResult)o).toString());
				}
				
				upAppNameList = (List<Object>) resultMap.get("upAppNameList");
				Map<String, List<Object>> appNameToServListMap =  (Map<String, List<Object>>) resultMap.get("detail");
						
				if(null == selectedAppName || "".equals(selectedAppName.trim()) || "所有".equals(selectedAppName)){
					recordCount = totalList.size();
					int endIndex = pageNo*pageSize;
					
					if(startIndex + pageSize > totalList.size()){
						endIndex = totalList.size();
					}

					upAppRelResultList = new ArrayList<Object>();
					
					for(int i=startIndex; i<endIndex; i++){		
						System.out.println(((UpAppRelResult)totalList.get(i)).getAppName()+":"+((UpAppRelResult)totalList.get(i)).getSourceType());
						upAppRelResultList.add(totalList.get(i));
					}
					
					
				}else{

					List<Object> tmp= new ArrayList<Object>();
					tmp.addAll(appNameToServListMap.get(selectedAppName));
					
					recordCount = tmp.size();
					int endIndex = pageNo*pageSize;
					
					if(startIndex + pageSize > tmp.size()){
						endIndex = tmp.size();
					}
					
					upAppRelResultList = new ArrayList<Object>();
					for(int i=startIndex; i<endIndex;i++){
						upAppRelResultList.add(tmp.get(i));
					}

				}

				for(Object uarl: upAppRelResultList){
					LogUtil.info(ShowAppRelAction.class,"UpAppRelResult info:"+((UpAppRelResult)uarl).toString());
				}
				
				session.put("upAppNameList", upAppNameList);
				session.put("upAppRelResultList", upAppRelResultList);
				
			}
		
		}
		
		return "up";
	}

	public String showUp(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		if(session.containsKey("qryHint")){
			session.remove("qryHint");
		}
		
		LogUtil.info(ShowAppRelAction.class,"操作：ShowAppRelAction.showUp() called: "+ checkNoId+","+seqNoId+","+operationType);
		
		String showCode = showAppRel.getUpAppRelInfo(checkNoId, seqNoId,operationType);
		LogUtil.info(ShowAppRelAction.class,"展示上游系统："+showCode);
		setShowCode(showCode);
		
		return "showUp";
	}
	
}