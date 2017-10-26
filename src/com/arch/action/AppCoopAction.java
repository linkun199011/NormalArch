package com.arch.action;

import com.arch.beans.*;
import com.arch.service.SysArchManager;
import com.arch.util.Constants;
import com.arch.util.JSONUtil;
import com.arch.util.LogUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppCoopAction implements Preparable, Serializable{

    HttpServletResponse response = ServletActionContext.getResponse();

	private static final long serialVersionUID = 1L;
	
	/*
	 * 定义变量
	 */
	private AppBaseInfo appBaseInfo;
	private AppService appService;
	private AppServiceTmp appServiceTmp;
	private DicAppServiceType dicAppServiceType;
	private SysArchManager sysArchManager;
	private String currentAppIdAndName;
	private String serviceName;
	private String serviceCode;
	private String appInnerCode;
	private List<AppBaseInfo> appBaseInfoList;
	private List<DicAppServiceType> dicAppServiceTypeList;
	private List<DicFlowStatus> dicFlowStatusList;
	private List<ProjectForDepCheck> projectForDepCheckList;
	private ProjectForCheck projectForCheck;
	
	//------------------用于传递查询框的值------------------------------
	private Integer operationTypeInfo; // 0=基线+修改中，1=仅修改中，2=仅基线	
	private String flowStatusInfo; // 所有，编辑中，提交待审核，审核通过，退回
	//----------------------------------------------------------

	// 将appBaseInfo 转为json格式传给前台解析
	private String appBaseInfoJson;

	// 服务步骤表
	private List<AppServiceDetail> appServiceDetailList = new ArrayList<AppServiceDetail>();
	private List<AppServiceDetailTmp> appServiceDetailTmpList = new ArrayList<AppServiceDetailTmp>();

	// 翻页的参数
	private int pageSize = 10;
	private int pageNo = 1;
	private int recordCount;
	
	private List<AppRelResult> appRelList;
	
	// 由编辑界面根据选择生成，传递给后台
	private Integer selectedAppServiceId;	
	private String selectedAppServiceOperationType;
	private String selectedAppServiceFlowStatus;
	// 由编辑界面根据选择
	private String projectName;
	
	// 由后台根据selectedAppServiceId查询获得
	private List<AppServiceDetail> editAppServiceDetail;
	
	private String editServiceType;
	
	private String resultHint;
	
	// 更新界面中删除的detailIndex,以“，”分隔
	private String removeIndex;
	
	private String underDepCheckHint = "架构部审核中，仅允许查看！";

	// 更新或删除步骤时使用
	private List<String> editAppServiceIdAndName;
	
	private String projectInfoJson;
	private Map<String, String> projectToTechManagerMap;
	
	/**
	 * @return the resultHint
	 */
 	public String getResultHint() {
		return resultHint;
	}

	/**
	 * @param resultHint the resultHint to set
	 */
	public void setResultHint(String resultHint) {
		this.resultHint = resultHint;
	}

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
	 * @return the selectedAppServiceId
	 */
	public Integer getSelectedAppServiceId() {
		return selectedAppServiceId;
	}

	/**
	 * @param selectedAppServiceId the selectedAppServiceId to set
	 */
	public void setSelectedAppServiceId(Integer selectedAppServiceId) {
		this.selectedAppServiceId = selectedAppServiceId;
	}

	/**
	 * @return the appBaseInfoJson
	 */
	public String getAppBaseInfoJson() {
		return appBaseInfoJson;
	}

	/**
	 * @param appBaseInfoJson the appBaseInfoJson to set
	 */
	public void setAppBaseInfoJson(String appBaseInfoJson) {
		this.appBaseInfoJson = appBaseInfoJson;
	}
	
	/**
	 * @return the sysArchManager
	 */
	public SysArchManager getSysArchManager() {
		return sysArchManager;
	}

	/**
	 * @param sysArchManager
	 *            the sysArchManager to set
	 */
	public void setSysArchManager(SysArchManager sysArchManager) {
		this.sysArchManager = sysArchManager;
	}

	/**
	 * @return the sysBaseInfo
	 */
	public AppBaseInfo getAppBaseInfo() {
		return appBaseInfo;
	}

	/**
	 * @param appBaseInfo
	 *            the sysBaseInfo to set
	 */
	public void setAppBaseInfo(AppBaseInfo appBaseInfo) {
		this.appBaseInfo = appBaseInfo;
	}

	/**
	 * @return the appService
	 */
	public AppService getAppService() {
		return appService;
	}

	/**
	 * @param appService
	 *            the appService to set
	 */
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	/**
	 * @return the dicAppServiceType
	 */
	public DicAppServiceType getDicAppServiceType() {
		return dicAppServiceType;
	}

	/**
	 * @param dicAppServiceType
	 *            the dicAppServiceType to set
	 */
	public void setDicAppServiceType(DicAppServiceType dicAppServiceType) {
		this.dicAppServiceType = dicAppServiceType;
	}

	/**
	 * @return the sysBaseInfoList
	 */
	public List<AppBaseInfo> getAppBaseInfoList() {
		return appBaseInfoList;
	}

	/**
	 * @param appBaseInfoList
	 *            the sysBaseInfoList to set
	 */
	public void setAppBaseInfoList(List<AppBaseInfo> appBaseInfoList) {
		this.appBaseInfoList = appBaseInfoList;
	}

	/**
	 * @return the dicAppServiceTypeList
	 */
	public List<DicAppServiceType> getDicAppServiceTypeList() {
		return dicAppServiceTypeList;
	}

	/**
	 * @param dicAppServiceTypeList
	 *            the dicAppServiceTypeList to set
	 */
	public void setDicAppServiceTypeList(
			List<DicAppServiceType> dicAppServiceTypeList) {
		this.dicAppServiceTypeList = dicAppServiceTypeList;
	}

	/**
	 * @return the appServiceDetailList
	 */
	public List<AppServiceDetail> getAppServiceDetailList() {
		return appServiceDetailList;
	}

	/**
	 * @param appServiceDetailList
	 *            the appServiceDetailList to set
	 */
	public void setAppServiceDetailList(
			List<AppServiceDetail> appServiceDetailList) {
		this.appServiceDetailList = appServiceDetailList;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the appInnerCode
	 */
	public String getAppInnerCode() {
		return appInnerCode;
	}

	/**
	 * @param appInnerCode the appInnerCode to set
	 */
	public void setAppInnerCode(String appInnerCode) {
		this.appInnerCode = appInnerCode;
	}

	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return the editServiceType
	 */
	public String getEditServiceType() {
		return editServiceType;
	}

	/**
	 * @param editServiceType the editServiceType to set
	 */
	public void setEditServiceType(String editServiceType) {
		this.editServiceType = editServiceType;
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
	 * @return the appRelList
	 */
	public List<AppRelResult> getAppRelList() {
		return appRelList;
	}

	/**
	 * @param appRelList the appRelList to set
	 */
	public void setAppRelList(List<AppRelResult> appRelList) {
		this.appRelList = appRelList;
	}

	/**
	 * @return the currentAppIdAndName
	 */
	public String getCurrentAppIdAndName() {
		return currentAppIdAndName;
	}

	/**
	 * @param currentAppIdAndName the currentAppIdAndName to set
	 */
	public void setCurrentAppIdAndName(String currentAppIdAndName) {
		this.currentAppIdAndName = currentAppIdAndName;
	}

	/**
	 * @return the editAppServiceDetail
	 */
	public List<AppServiceDetail> getEditAppServiceDetail() {
		return editAppServiceDetail;
	}

	/**
	 * @param editAppServiceDetail the editAppServiceDetail to set
	 */
	public void setEditAppServiceDetail(List<AppServiceDetail> editAppServiceDetail) {
		this.editAppServiceDetail = editAppServiceDetail;
	}

	/**
	 * @return the removeIndex
	 */
	public String getRemoveIndex() {
		return removeIndex;
	}

	/**
	 * @param removeIndex the removeIndex to set
	 */
	public void setRemoveIndex(String removeIndex) {
		this.removeIndex = removeIndex;
	}

	/**
	 * @return the editAppServiceIdAndName
	 */
	public List<String> getEditAppServiceIdAndName() {
		return editAppServiceIdAndName;
	}

	/**
	 * @param editAppServiceIdAndName the editAppServiceIdAndName to set
	 */
	public void setEditAppServiceIdAndName(List<String> editAppServiceIdAndName) {
		this.editAppServiceIdAndName = editAppServiceIdAndName;
	}

	/**
	 * @return the appServiceTmp
	 */
	public AppServiceTmp getAppServiceTmp() {
		return appServiceTmp;
	}

	/**
	 * @param appServiceTmp the appServiceTmp to set
	 */
	public void setAppServiceTmp(AppServiceTmp appServiceTmp) {
		this.appServiceTmp = appServiceTmp;
	}

	/**
	 * @return the appServiceDetailTmp
	 */
	public List<AppServiceDetailTmp> getAppServiceDetailTmpList() {
		return appServiceDetailTmpList;
	}

	/**
	 * @param operationTypeInfo the appServiceDetailTmp to set
	 */
	public void setAppServiceDetailTmpList(List<AppServiceDetailTmp> operationTypeInfo) {
		this.appServiceDetailTmpList = appServiceDetailTmpList;
	}

	/**
	 * @return the appCoopStatus
	 */
	public Integer getOperationTypeInfo() {
		return operationTypeInfo;
	}

	/**
	 * @param operationTypeInfo the appCoopStatus to set
	 */
	public void setOperationTypeInfo(Integer operationTypeInfo) {
		this.operationTypeInfo = operationTypeInfo;
	}

	/**
	 * @return the selectedAppServiceStatus
	 */
	public String getSelectedAppServiceOperationType() {
		return selectedAppServiceOperationType;
	}

	/**
	 * @param selectedAppServiceStatus the selectedAppServiceStatus to set
	 */
	public void setSelectedAppServiceOperationType(String selectedAppServiceStatus) {
		this.selectedAppServiceOperationType = selectedAppServiceStatus;
	}

	/**
	 * @return the flowStatusInfo
	 */
	public String getFlowStatusInfo() {
		return flowStatusInfo;
	}

	/**
	 * @param flowStatusInfo the flowStatusInfo to set
	 */
	public void setFlowStatusInfo(String flowStatusInfo) {
		this.flowStatusInfo = flowStatusInfo;
	}

	/**
	 * @return the dicFlowStatusList
	 */
	public List<DicFlowStatus> getDicFlowStatusList() {
		return dicFlowStatusList;
	}

	/**
	 * @param dicFlowStatusList the dicFlowStatusList to set
	 */
	public void setDicFlowStatusList(List<DicFlowStatus> dicFlowStatusList) {
		this.dicFlowStatusList = dicFlowStatusList;
	}

	/**
	 * @return the selectedAppServiceFlowStatus
	 */
	public String getSelectedAppServiceFlowStatus() {
		return selectedAppServiceFlowStatus;
	}

	/**
	 * @param selectedAppServiceFlowStatus the selectedAppServiceFlowStatus to set
	 */
	public void setSelectedAppServiceFlowStatus(String selectedAppServiceFlowStatus) {
		this.selectedAppServiceFlowStatus = selectedAppServiceFlowStatus;
	}

	/**
	 * @return the projectInfoJson
	 */
	public String getProjectInfoJson() {
		return projectInfoJson;
	}

	/**
	 * @param projectInfoJson the projectInfoJson to set
	 */
	public void setProjectInfoJson(String projectInfoJson) {
		this.projectInfoJson = projectInfoJson;
	}

	/**
	 * @return the projectToTechManagerMap
	 */
	public Map<String, String> getProjectToTechManagerMap() {
		return projectToTechManagerMap;
	}

	/**
	 * @param projectToTechManagerMap the projectToTechManagerMap to set
	 */
	public void setProjectToTechManagerMap(
			Map<String, String> projectToTechManagerMap) {
		this.projectToTechManagerMap = projectToTechManagerMap;
	}

	/**
	 * @return the projectForCheck
	 */
	public ProjectForCheck getProjectForCheck() {
		return projectForCheck;
	}

	/**
	 * @param projectForCheck the projectForCheck to set
	 */
	public void setProjectForCheck(ProjectForCheck projectForCheck) {
		this.projectForCheck = projectForCheck;
	}

	/**
	 * @return the projectForDepCheckList
	 */
	public List<ProjectForDepCheck> getProjectForDepCheckList() {
		return projectForDepCheckList;
	}

	/**
	 * @param projectForDepCheckList the projectForDepCheckList to set
	 */
	public void setProjectForDepCheckList(
			List<ProjectForDepCheck> projectForDepCheckList) {
		this.projectForDepCheckList = projectForDepCheckList;
	}

	/**
	 * @return
	 */
	public String init() {		
		ActionContext.getContext().getSession().remove("resultHint");
		return "input";
	}
	
	/**
	 * 跳转到UpdateAppCoop界面
	 * @return
	 */
	public String fwdUpdate(){

		boolean underDepCheck = this.isUnderCheck("dep");
		
		// 如果为架构部审核状态，提示已提交架构部审核
		if(underDepCheck){
			resultHint = underDepCheckHint;
			return "input";
		}
		
		return "update";
	}
	
	/**
	 * 新增或修改应用协作
	 * @return
	 */
	public String update() {
		
		try{
			
			if(isUnderCheck("dep")){
				resultHint = underDepCheckHint;
				return query();
			}
			
			// 新增
			if(null == appServiceTmp){
				return "fail";
			}
			
			if(null == appServiceTmp.getAppServiceId()){
				
				//获取appServiceId
				Integer appServiceId = sysArchManager.getTbSeqNumByName(Constants.TABLE_APPSERVICE_TMP);
				
				if(null == appServiceId){
					throw new Exception(Constants.TABLE_APPSERVICE_TMP+"获取主键失败");
				}
		
				// appServiceId赋值
				appServiceTmp.setAppServiceId(appServiceId);
			
			}
			
			// 获取系统Id
			String currentAppId = this.currentAppIdAndName.split("-")[0];
			appServiceTmp.setAppId(Integer.parseInt(currentAppId));
			
			if(null == appServiceTmp.getPubAppServiceId()){
				boolean newAdd = false;
				// 判断service_code是否为空
				if(null == appServiceTmp.getServiceCode() || 0 == appServiceTmp.getServiceCode().trim().length()){
					newAdd = true;
				}else{
					
					Integer respCode = sysArchManager.getPubAppServiceId(appServiceTmp);
					if(-1 == respCode){
						
						resultHint = "保存失败，错误原因：\""+appServiceTmp.getServiceName()+","+appServiceTmp.getServiceCode()+"\" 在基线中有多个类似的服务，请从基线中进行修改！";						
						return "fail";
						
					}else if(0 == respCode){
						
						newAdd = true;
						
					}else{				
						newAdd = false;
						appServiceTmp.setOperationType("修改");
						appServiceTmp.setPubAppServiceId(respCode);	
					}
					
				}
				
				if(newAdd){
					//没加判断
					appServiceTmp.setOperationType("新增");
					// 占用一个appServiceID，分配为tmp的pub_app_service_id,为了调度使用
					Integer pubAppServiceId = sysArchManager.getTbSeqNumByName(Constants.TABLE_APPSERVICE);
					appServiceTmp.setPubAppServiceId(pubAppServiceId);	
				}
			}else{
				appServiceTmp.setOperationType("修改");
			}
			appServiceTmp.setFlowStatus("编辑中");
			
			appServiceTmp.setProjectId(projectName);
			
			String modifyUser = (String) ActionContext.getContext().getSession().get("username");
			//获取修改的人员
			appServiceTmp.setModifyUserId(modifyUser);
			
			String modifyUserName = (String) ActionContext.getContext().getSession().get("name");
			appServiceTmp.setModifyUserName(modifyUserName);

			
			editAppServiceIdAndName = new ArrayList<String>();
			List<String> removedIndex = new ArrayList<String>();
			LogUtil.info(AppCoopAction.class, "!!!!!!!!!"+removeIndex);
			if(!"".equals(removeIndex) && null != removeIndex){
				String[] parts = this.removeIndex.split(",");
				for(String part : parts){
					removedIndex.add(part);
				}	
				
				// 删除被移除的步骤细节
				for(String remove:removedIndex){
					if("".equals(remove.trim()) || null == remove){
						
					}else{
						Integer index = Integer.parseInt(remove);
						this.sysArchManager.deleteAppServiceDetailTmpById(index);
					}
				}		
			}
			
			// 将步骤转换为AppServiceDetail类
			for (int i = 0; i<appServiceDetailTmpList.size(); i++) {
				AppServiceDetailTmp appServiceDetailTmp = appServiceDetailTmpList.get(i);
				
				Integer appServiceDetailId = appServiceDetailTmp.getAppServiceDetailId();
				
				if(null == appServiceDetailId || 0 == appServiceDetailId ){
					// 获取serviceDetail主键
					appServiceDetailId = sysArchManager.getTbSeqNumByName(Constants.TABLE_APPSERVICEDETAIL_TMP);
					// 设置主键
					appServiceDetailTmp.setAppServiceDetailId(appServiceDetailId);
					
				}
	
				// 获取步骤编号
				Integer stepCode = i+1;
				appServiceDetailTmp.setStepCode(stepCode);			
				appServiceDetailTmp.setAppServiceId(appServiceTmp.getAppServiceId());
	
				Integer appId = appServiceDetailTmp.getCalledAppId();
				String appName = this.sysArchManager.getAppBaseInfoById(appId).getAppName();
				String appIdAndName = appId+"-"+appName;
				editAppServiceIdAndName.add(appIdAndName);
			}
			
			editServiceType = sysArchManager.getServcieTypeById(appServiceTmp.getServiceTypeId()).getServiceTypeName();
			// 插库
			LogUtil.info(AppCoopAction.class, "待查库：appServiceTmp:"+appServiceTmp.toString());
			sysArchManager.addAppServiceTmp(appServiceTmp, appServiceDetailTmpList);
			sysArchManager.refresh();
		}catch(Exception e){
			LogUtil.error(AppCoopAction.class, "method update() complete with error...Exception:"+e);

			resultHint = "保存失败，错误原因：\n"+e.getMessage()+"\n\n\n 请检查后重新提交或联系管理员";
			return "fail";
		}
		
		// 如果更新成功，清空
		resultHint = "保存成功";
		
		return "success";
	}

	/**
	 * 检查该项目是否处于待架构部确认状态
	 */
	private boolean isUnderCheck(String checkLevel){	
		if("dep".equals(checkLevel))
			return sysArchManager.isProjectUnderDepCheck(projectName);
		else
			return false;
	}
	
	private boolean anyUpdateBySelf(){
		
		String modifyUser = (String) ActionContext.getContext().getSession().get("username");
		List<AppServiceTmp> list = sysArchManager.getAppServiceTmpListByProjectAndModifyUser(projectName,modifyUser);
		
		// 无本人未提交组内确认的记录
		if(null == list || list.size() == 0){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 查询已录入的系统结果
	 * @return
	 */
	public String query(){
			
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		LogUtil.info(AppCoopAction.class,"操作：query() called……开始查询……");

        int startIndex = 0;
        int endIndex = pageSize;
        if (pageNo > 1) {
            startIndex = (pageNo - 1) * pageSize;
        }

        AppRelQuery arq = new AppRelQuery();

		if(null != this.currentAppIdAndName && !"".equals(this.currentAppIdAndName.trim())){
//			session.put("currentAppIdAndName", this.currentAppIdAndName.trim());
			String temp[] = this.currentAppIdAndName.split("-");
			if(temp.length != 2){
				resultHint = "系统名"+currentAppIdAndName+"输入有误，请从下拉列表中选择";
				LogUtil.info(AppCoopAction.class,resultHint);
				return "input";
			}
			String currentAppId = temp[0];
			Integer appId = Integer.parseInt(currentAppId);
			arq.setAppId(appId);
		}
		
		arq.setStartIndex(startIndex);
		arq.setLength(endIndex);
		arq.setProjectId(projectName);
		
		if(session.containsKey("flowStatusInfo")){
			if(null == flowStatusInfo){
				flowStatusInfo = (String) session.get("flowStatusInfo");
			}else{
				session.put("flowStatusInfo", flowStatusInfo);
			}
		}else{
			if(null == flowStatusInfo){
				flowStatusInfo = "所有";
			}
			session.put("flowStatusInfo", flowStatusInfo);
		}	
		arq.setFlowStatus(flowStatusInfo);
		
		if(null != this.serviceName && !"".equals(serviceName.trim())){
			arq.setServiceName(serviceName.trim());
		}
		
		if(null != this.serviceCode && !"".equals(serviceCode.trim())){
			arq.setServiceCode(serviceCode.trim());
		}
		
		if(null != this.appInnerCode && !"".equals(appInnerCode.trim())){
			arq.setAppInnerCode(appInnerCode.trim());
		}

		if(session.containsKey("operationTypeInfo")){
			if(null == operationTypeInfo){
				operationTypeInfo = (Integer) session.get("operationTypeInfo");
			}else{
				session.put("operationTypeInfo", operationTypeInfo);
			}
		}else{
			if(null == operationTypeInfo){
				operationTypeInfo = 0;
			}
			session.put("operationTypeInfo", operationTypeInfo);
		}

		arq.setOperationType(operationTypeInfo);
		
		LogUtil.info(AppCoopAction.class,"查询要素："+arq.toString());
		
		appRelList = this.sysArchManager.listAppServiceByAppRelQuery(arq);
		recordCount = this.sysArchManager.getTotalCountByAppRelQuery(arq);
		
		LogUtil.info(AppCoopAction.class,"当前查询条数："+appRelList.size());
		LogUtil.info(AppCoopAction.class,"查询总条数："+recordCount);
		
		if(appRelList.size() == 0){
			resultHint = "无符合条件的记录";
			LogUtil.info(AppCoopAction.class,resultHint);
		}
		
		return "input";
	}

    /**
     * 查询某个系统的所有结果，该函数仅供 {@linkplain AppCoopAction#exportExcel()}
     * 使用，其他情况还是通过分页查询来实现。
     * {@linkplain AppCoopAction#query()}
     *
     * @return 返回查询的100000条结果
     */
    private List<AppRelResult> queryAll() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        LogUtil.info(AppCoopAction.class, "操作：query() called……开始查询……");

        int startIndex = 0;
        int maxLength = 100000;

        AppRelQuery arq = new AppRelQuery();

        if (null != this.currentAppIdAndName && !"".equals(this.currentAppIdAndName.trim())) {
            String temp[] = this.currentAppIdAndName.split("-");
            if (temp.length != 2) {
                resultHint = "系统名" + currentAppIdAndName + "输入有误，请从下拉列表中选择";
                LogUtil.info(AppCoopAction.class, resultHint);
                return null;
            }
            String currentAppId = temp[0];
            Integer appId = Integer.parseInt(currentAppId);
            arq.setAppId(appId);
        }

        arq.setStartIndex(startIndex);
        // 如果查询全部， 此处setLength应该设置所有的查询结果。
        arq.setLength(maxLength);
        arq.setProjectId(projectName);

        if (session.containsKey("flowStatusInfo")) {
            if (null == flowStatusInfo) {
                flowStatusInfo = (String) session.get("flowStatusInfo");
            } else {
                session.put("flowStatusInfo", flowStatusInfo);
            }
        } else {
            if (null == flowStatusInfo) {
                flowStatusInfo = "所有";
            }
            session.put("flowStatusInfo", flowStatusInfo);
        }
        arq.setFlowStatus(flowStatusInfo);

        if (null != this.serviceName && !"".equals(serviceName.trim())) {
            arq.setServiceName(serviceName.trim());
        }

        if (null != this.serviceCode && !"".equals(serviceCode.trim())) {
            arq.setServiceCode(serviceCode.trim());
        }

        if (null != this.appInnerCode && !"".equals(appInnerCode.trim())) {
            arq.setAppInnerCode(appInnerCode.trim());
        }

        if (session.containsKey("operationTypeInfo")) {
            if (null == operationTypeInfo) {
                operationTypeInfo = (Integer) session.get("operationTypeInfo");
            } else {
                session.put("operationTypeInfo", operationTypeInfo);
            }
        } else {
            if (null == operationTypeInfo) {
                operationTypeInfo = 0;
            }
            session.put("operationTypeInfo", operationTypeInfo);
        }

        arq.setOperationType(operationTypeInfo);

        LogUtil.info(AppCoopAction.class, "查询要素：" + arq.toString());

        List<AppRelResult> totalList = this.sysArchManager.listAppServiceByAppRelQuery(arq);
        int recordCount = this.sysArchManager.getTotalCountByAppRelQuery(arq);

        LogUtil.info(AppCoopAction.class, "当前查询条数：" + totalList.size());
        LogUtil.info(AppCoopAction.class, "查询总条数：" + recordCount);

        if (totalList.size() == 0) {
            resultHint = "无符合条件的记录";
            LogUtil.info(AppCoopAction.class, resultHint);
        }
        return totalList;
    }

    /**
	 * 查看服务
	 * @return
	 */
	public String view(){
		
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.view() called……查看服务信息……");
		
		try{
			editAppServiceIdAndName = new ArrayList<String>();
					
			if("基线".equals(selectedAppServiceOperationType)){

				appService = sysArchManager.qryAppServiceById(selectedAppServiceId);
				appServiceDetailList = sysArchManager.qryAppServiceDetailByServiceId(selectedAppServiceId);	
				
				// 将appService的值赋予appServiceTmp
				convertAppServiceToTmp("view");
				LogUtil.info(AppCoopAction.class,"查询基线，将appService的值赋予到appServiceTmp");
				
				// 将appServiceDetailList的指赋予到AppServiceDetailTmpList
				convertAppServiceDetailToTmp("view");
				LogUtil.info(AppCoopAction.class,"查询基线，将appServiceDetail的值赋予到appServiceDetailTmp");
				
			}else { // 发布或修改中
				
				appServiceTmp = sysArchManager.qryAppServiceTmpById(selectedAppServiceId);
				appServiceDetailTmpList = sysArchManager.qryAppServiceDetailTmpByServiceId(selectedAppServiceId);
				
				LogUtil.info(AppCoopAction.class,"查询修改中，获取appServiceTmp");
				LogUtil.info(AppCoopAction.class,"查询修改中，获取appServiceTmpList");
				
			}
			
			LogUtil.info(AppCoopAction.class,"所选服务信息如下："+appServiceTmp.toString());
				
			if(null == currentAppIdAndName || "".equals(currentAppIdAndName.trim())){
				Integer appId = appServiceTmp.getAppId();
				String appName = this.sysArchManager.getAppBaseInfoById(appId).getAppName();
				this.currentAppIdAndName = appId + "-" + appName;
			}

			for(AppServiceDetailTmp appServiceDetailTmp : appServiceDetailTmpList){
				LogUtil.info(AppCoopAction.class,"服务步骤信息："+appServiceDetailTmp.toString());

				Integer id = appServiceDetailTmp.getCalledAppId();
				AppBaseInfo appInfo = this.sysArchManager.getAppBaseInfoById(id);
				if(null != appInfo){
					String name = this.sysArchManager.getAppBaseInfoById(id).getAppName();			
					String appIdAndName = id+"-"+name;
					editAppServiceIdAndName.add(appIdAndName);
				}				
			}
			
			editServiceType = sysArchManager.getServcieTypeById(appServiceTmp.getServiceTypeId()).getServiceTypeName();
			
		}catch(Exception e){
			e.printStackTrace();
			resultHint = "系统异常，请与管理员联系";
			LogUtil.info(AppCoopAction.class,"系统异常："+e.getMessage());
			return "input";
		}
			
		return "view";
	}
	
	/**
	 * 执行修改操作
	 * @return
	 */
	public String edit(){
		
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.edit() called……开始编辑……");
		
		try{
			Map<String, Object> session = ActionContext.getContext().getSession();
			
			editAppServiceIdAndName = new ArrayList<String>();

			String modifyUser = (String)session.get("username");
					
			if("基线".equals(selectedAppServiceOperationType)){

				appService = sysArchManager.qryAppServiceById(selectedAppServiceId);
				LogUtil.info(AppCoopAction.class,"获取AppService信息："+appService.toString());
				
				// 判断是否已有人维护
				AppServiceTmp ast = sysArchManager.getAppServiceTmpByPubAppServiceId(appService.getAppServiceId());
				LogUtil.info(AppCoopAction.class,"判断是否有人维护该服务");
				boolean isModifiedBySelf = false;
				if(null != ast){
					if(ast.getModifyUserId().equals(modifyUser)){
						if(projectName.equals(ast.getProjectId())){
							isModifiedBySelf = true;
						}else{
							resultHint = "本服务已由您为项目"+ast.getProjectId()+"维护，请仔细核对";
							LogUtil.info(AppCoopAction.class,resultHint);
							return query();
						}
					}else{
						resultHint = "本服务已由"+ast.getModifyUserName()+"(id："+ast.getModifyUserId()+")维护，如需修改，请与其联系";
						LogUtil.info(AppCoopAction.class,resultHint);
						return query();
					}
				}
				
				appServiceDetailList = sysArchManager.qryAppServiceDetailByServiceId(selectedAppServiceId);	
				
				if(!isModifiedBySelf){
					// 将appService的值赋予appServiceTmp
					convertAppServiceToTmp("edit");
					LogUtil.info(AppCoopAction.class,"将appService转为appServiceTmp");
				}else{
					appServiceTmp = ast;
				}
				
				// 将appServiceDetailList的指赋予到AppServiceDetailTmpList
				convertAppServiceDetailToTmp("edit");	
				
			}else { // 新增或修改
				
				appServiceTmp = sysArchManager.qryAppServiceTmpById(selectedAppServiceId);
				appServiceDetailTmpList = sysArchManager.qryAppServiceDetailTmpByServiceId(selectedAppServiceId);
				LogUtil.info(AppCoopAction.class,"获取appServiceTmp"+appServiceTmp.toString());
				
				if(null == modifyUser){
					resultHint = "未识别到当前操作用户，请与管理员联系！";
					LogUtil.info(AppCoopAction.class,resultHint);
					return query();
				}
				
				if(null == appServiceTmp.getModifyUserId()){
					resultHint = "未识别到当前交易修改用户，请与管理员联系！";
					LogUtil.info(AppCoopAction.class,resultHint);
					return query();
				}
				// 已有人修改又非本人
				if(!modifyUser.equals(appServiceTmp.getModifyUserId())){
					resultHint = "本服务已由"+appServiceTmp.getModifyUserName()+"(id："+appServiceTmp.getModifyUserId()+")维护，如需修改，请与其联系";
					LogUtil.info(AppCoopAction.class,resultHint);
					return query();
				}
				
			}

			if(null == this.currentAppIdAndName || "".equals(currentAppIdAndName.trim())){
				Integer id = appServiceTmp.getAppId();
				String appName = this.sysArchManager.getAppBaseInfoById(id).getAppName();			
				currentAppIdAndName = id+"-"+appName;
			}
			
			for(AppServiceDetailTmp appServiceDetailTmp : appServiceDetailTmpList){
				Integer id = appServiceDetailTmp.getCalledAppId();
				String appName = this.sysArchManager.getAppBaseInfoById(id).getAppName();			
				String appIdAndName = id+"-"+appName;
				editAppServiceIdAndName.add(appIdAndName);
			}
			
			editServiceType = sysArchManager.getServcieTypeById(appServiceTmp.getServiceTypeId()).getServiceTypeName();
			
		}catch(Exception e){
			e.printStackTrace();
			resultHint = "系统异常，请检查后稍后提交";
			LogUtil.info(AppCoopAction.class,resultHint+":"+e.getMessage());
			return "input";
		}
			
		System.out.println(currentAppIdAndName);
		return "update";
	}
	
	public String down(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.down() called……查询下游");
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		session.put("serviceId", selectedAppServiceId);
		session.put("operationType", selectedAppServiceOperationType);
		LogUtil.info(AppCoopAction.class,"session更新serviceId:"+selectedAppServiceId+",operationType:"+selectedAppServiceOperationType);
		
		return "down";
	}
	
	public String up(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.up() called……查询上游");
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("serviceId", selectedAppServiceId);
		session.put("operationType", selectedAppServiceOperationType);
		LogUtil.info(AppCoopAction.class,"session更新serviceId:"+selectedAppServiceId+",operationType:"+selectedAppServiceOperationType);
		return "up";
	}
	
	/**
	 * 将AppService中的值赋予到AppServiceTmp
	 * @param operation Type view,edit,delete
	 */
	private void convertAppServiceToTmp(String operation){
		LogUtil.info(AppCoopAction.class,"进入方法：AppCoopAction.convertAppServiceToTmp,参数："+operation);
		LogUtil.info(AppCoopAction.class,"appService:"+appService.toString());
		appServiceTmp = new AppServiceTmp();
		
		Integer appServiceId = appService.getAppServiceId();
		Integer appId = appService.getAppId();
		Integer serviceTypeId = appService.getServiceTypeId();
		String serviceCode = appService.getServiceCode();
		String appInnerCode = appService.getAppInnerCode();
		String serviceName = appService.getServiceName();
		String serviceDesc = appService.getServiceDesc();
		String receiveFile = appService.getReceiveFile();
		String getbackFile = appService.getGetbackFile();
		
		if("view".equals(operation)){
			appServiceTmp.setAppServiceId(appServiceId);
		}else{
			// 编辑或删除时，主键设为null，系统将从表中获得相应的appServiceId值
			appServiceTmp.setPubAppServiceId(appServiceId);
		}
		appServiceTmp.setAppId(appId);
		appServiceTmp.setServiceTypeId(serviceTypeId);
		appServiceTmp.setServiceCode(serviceCode);
		appServiceTmp.setAppInnerCode(appInnerCode);
		appServiceTmp.setServiceName(serviceName);
		appServiceTmp.setServiceDesc(serviceDesc);
		appServiceTmp.setReceiveFile(receiveFile);
		appServiceTmp.setGetbackFile(getbackFile);
		appServiceTmp.setOperationType(selectedAppServiceOperationType);
		appServiceTmp.setFlowStatus(flowStatusInfo);

		LogUtil.info(AppCoopAction.class,"转换后的appServiceTmp:"+appServiceTmp.toString());
		LogUtil.info(AppCoopAction.class,"离开方法：convertAppServiceToTmp");
	}

	/**
	 * 将AppServiceDetailList中的值赋值到Tmp
	 * @param operationType view,edit,delete
	 */
	private void convertAppServiceDetailToTmp(String operationType){
		LogUtil.info(AppCoopAction.class,"进入方法：AppCoopAction.convertAppServiceDetailToTmp，参数"+operationType);
		appServiceDetailTmpList = new ArrayList<AppServiceDetailTmp>();
		for(AppServiceDetail appServiceDetail : appServiceDetailList){
			Integer appServiceDetailId = appServiceDetail.getAppServiceDetailId();
			Integer appServiceId = appServiceDetail.getAppServiceId();
			Integer stepCode = appServiceDetail.getStepCode();
			String stepDesc = appServiceDetail.getStepDesc();
			Integer calledAppId = appServiceDetail.getCalledAppId();
			String calledAppIntf = appServiceDetail.getCalledAppIntf();
			
			AppServiceDetailTmp asdt = new AppServiceDetailTmp();
			if("view".equals(operationType)){
				asdt.setAppServiceDetailId(appServiceDetailId);
				asdt.setAppServiceId(appServiceId);
			}else if("delete".equals(operationType)){
				// 将appServiceDetailTmp与AppServiceTmp通过appServiceId关联
				asdt.setAppServiceId(appServiceTmp.getAppServiceId());
			}else{
				// 修改的情况下，appServiceTmp中的appServiceId会在用户提交保存是赋值到appServiceDetailTmp中的appServiceId字段，本处无需赋值
			}
			asdt.setCalledAppId(calledAppId);
			asdt.setCalledAppIntf(calledAppIntf);
			asdt.setStepCode(stepCode);
			asdt.setStepDesc(stepDesc);
			
			appServiceDetailTmpList.add(asdt);
		}
		

		LogUtil.info(AppCoopAction.class,"离开方法：convertAppServiceDetailToTmp");
	}

	/**
	 * 执行删除操作
	 * @return
	 */
	public String delete(){
		LogUtil.info(AppCoopAction.class,"操作:AppCoopAction.delete() called……删除……");
		try{
			
			String modifyUser = (String) ActionContext.getContext().getSession().get("username");
			String modifyUserName = (String) ActionContext.getContext().getSession().get("name");
			
			if("基线".equals(selectedAppServiceOperationType)){

				appService = sysArchManager.qryAppServiceById(selectedAppServiceId);
				
				AppServiceTmp ast = sysArchManager.getAppServiceTmpByPubAppServiceId(appService.getAppServiceId());
				if(null != ast){
					resultHint = "本服务已由"+ast.getModifyUserName()+"(id:"+ast.getModifyUserId()+")维护，如需删除，请与其联系";
					LogUtil.info(AppCoopAction.class,resultHint);
					return query();
				}
								
				appServiceDetailList = sysArchManager.qryAppServiceDetailByServiceId(selectedAppServiceId);	
				
				// 将appService的值赋予appServiceTmp
				convertAppServiceToTmp("delete");

				//----------------------更新appserviceTmp中的appService值------------------------------------------------------------------
				//获取appServiceId
				Integer appServiceId = sysArchManager.getTbSeqNumByName(Constants.TABLE_APPSERVICE_TMP);
				// appServiceId赋值
				appServiceTmp.setAppServiceId(appServiceId);
				appServiceTmp.setModifyUserId(modifyUser);
				appServiceTmp.setModifyUserName(modifyUserName);
				appServiceTmp.setProjectId(projectName);
				//-----------------------------------------------------------------------------------------------------
				
				// 将appServiceDetailList的指赋予到AppServiceDetailTmpList
				convertAppServiceDetailToTmp("delete");
				
				for(AppServiceDetailTmp appServiceDetailTmp : appServiceDetailTmpList){
					if(null == appServiceDetailTmp.getAppServiceDetailId()){
						Integer appServiceDetailId = sysArchManager.getTbSeqNumByName(Constants.TABLE_APPSERVICEDETAIL_TMP);
						appServiceDetailTmp.setAppServiceDetailId(appServiceDetailId);
					}
				}
				LogUtil.info(AppCoopAction.class,"待删除的信息："+appServiceTmp.toString());
				sysArchManager.addAppServiceTmp(appServiceTmp, appServiceDetailTmpList);
				
				resultHint = "删除成功,在修改中的内容中已标记为待删除。";
				LogUtil.info(AppCoopAction.class,resultHint);
			}else{
				
				appServiceTmp = sysArchManager.qryAppServiceTmpById(selectedAppServiceId);
				
				if(null == modifyUser){
					resultHint = "未识别到当前操作用户，请与管理员联系！";
					LogUtil.info(AppCoopAction.class,resultHint);
					return query();
				}
				
				if(null == appServiceTmp.getModifyUserId()){
					resultHint = "未识别到当前交易修改用户，请与管理员联系！";
					LogUtil.info(AppCoopAction.class,resultHint);
					return query();
				}
				// 已有人修改又非本人
				if(!modifyUser.equals(appServiceTmp.getModifyUserId())){
					resultHint = "本服务已由"+appServiceTmp.getModifyUserName()+"(id:"+appServiceTmp.getModifyUserId()+")维护，如需删除，请与其联系";
					LogUtil.info(AppCoopAction.class,resultHint);
					return query();
				}
				
				LogUtil.info(AppCoopAction.class,"删除已维护的服务："+selectedAppServiceId);
				sysArchManager.deleteAppServiceTmpById(selectedAppServiceId);

				resultHint = "删除成功";
				LogUtil.info(AppCoopAction.class,resultHint);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			resultHint = "删除失败";
			LogUtil.info(AppCoopAction.class,resultHint+":"+e.getMessage());
			return query();
		}
				
		
		return query();
	}
	
	/**
	 * 架构部审核通过
	 * @return
	 */
	public String confirm(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.confirm() called……架构部审核通过……");
		
		sysArchManager.updateAppServiceTmpFlowStatusById(selectedAppServiceId, "确认待发布");

		LogUtil.info(AppCoopAction.class,"修改选中服务状态为确认待发布");
		
		return query();
	}
	
	/**
	 * 发布 单个服务
	 * @return
	 */
	public String publish(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.publish() called……发布单个服务……");

		LogUtil.info(AppCoopAction.class,selectedAppServiceId+"发布待发布");
		
		String result = sysArchManager.publishAppServiceTmpById(selectedAppServiceId);
		
		if("success".equals(result)){
			resultHint = "发布成功！可在基线中进行查看。";
			LogUtil.info(AppCoopAction.class,projectName+"发布成功");
		}else{
			resultHint = "发布失败："+result;
			LogUtil.info(AppCoopAction.class,projectName+"发布失败，原因："+result);
		}
		
		return query();
	}
	
	
	public String publishAll(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.publishAll() called……发布所有服务……");

		LogUtil.info(AppCoopAction.class,projectName+"发布……");
		
		String result = sysArchManager.publishAllByProjectId(projectName);
		if("success".equals(result)){
			resultHint = "发布成功！可在基线中进行查看。";
			LogUtil.info(AppCoopAction.class,projectName+"发布成功");
		}else{
			resultHint = "发布失败："+result;
			LogUtil.info(AppCoopAction.class,projectName+"发布失败，原因："+result);
		}
		
		return query();
	}

	/**
	 * 将查询到的内容导出到excel
	 */
    public void exportExcel() {
        LogUtil.info(AppCoopAction.class, "操作：AppCoopAction.exportExcel() called……导出Excel服务……");
        LogUtil.info(AppCoopAction.class, projectName + "发布……");

        // 先进行查询，获取到list, 最高设置成100000条结果
        List<AppRelResult> allResult = queryAll();

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式
		    String date = df.format(new Date());
		    String fileName;
		    if (projectName != null) {
		        fileName = date + "_" + projectName.replaceAll("\\s+", "") + ".xls";
            } else {
		        fileName = date + ".xls";
            }
            OutputStream os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment;charsets=UTF-8; filename="
                    + new String(fileName.getBytes(), "iso-8859-1"));// 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型
            response.setCharacterEncoding("UTF-8");

            WritableWorkbook workBook = Workbook.createWorkbook(os); // 建立excel文件
            WritableSheet writableSheet = workBook.createSheet(projectName, 0); // sheet名称
            // 设置每列的宽度
            writableSheet.setColumnView(0, 15);
            writableSheet.setColumnView(1, 16);
            writableSheet.setColumnView(2, 16);
            writableSheet.setColumnView(3, 16);
            writableSheet.setColumnView(4, 30);
            writableSheet.setColumnView(5, 25);
            writableSheet.setColumnView(6, 25);
            writableSheet.setColumnView(7, 11);
            writableSheet.setColumnView(8, 15);
            writableSheet.setColumnView(9, 10);
            writableSheet.setColumnView(10, 50);

            // 设置文字样式
            WritableFont writableFont;
            // 设置单元格样式
            WritableCellFormat cellFormat;

            // 设置表头字体样式
            writableFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            cellFormat = new WritableCellFormat(writableFont);

            // 生成表头
            writableSheet.addCell(new Label(0, 0, "系统名", cellFormat));
            writableSheet.addCell(new Label(1, 0, "服务发起方式", cellFormat));
            writableSheet.addCell(new Label(2, 0, "服务码", cellFormat));
            writableSheet.addCell(new Label(3, 0, "内部转化码", cellFormat));
            writableSheet.addCell(new Label(4, 0, "服务名称", cellFormat));
            writableSheet.addCell(new Label(5, 0, "请求接收文件名", cellFormat));
            writableSheet.addCell(new Label(6, 0, "返回响应文件名", cellFormat));
            writableSheet.addCell(new Label(7, 0, "服务状态", cellFormat));
            writableSheet.addCell(new Label(8, 0, "流程状态", cellFormat));
            writableSheet.addCell(new Label(9, 0, "修改人", cellFormat));
            writableSheet.addCell(new Label(10, 0, "备注", cellFormat));

            // 设置表内容字体样式
            writableFont = new WritableFont(WritableFont.COURIER, 10, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            cellFormat = new WritableCellFormat(writableFont);


            int count = 0;
            if (allResult != null && allResult.size() > 0) {
                for (AppRelResult result : allResult) {
                    writableSheet.addCell(new Label(0, count + 1, result.getAppName(), cellFormat));
                    writableSheet.addCell(new Label(1, count + 1, result.getServiceType(), cellFormat));
                    writableSheet.addCell(new Label(2, count + 1, result.getServiceCode(), cellFormat));
                    writableSheet.addCell(new Label(3, count + 1, result.getAppInnerCode(), cellFormat));
                    writableSheet.addCell(new Label(4, count + 1, result.getServiceName(), cellFormat));
                    writableSheet.addCell(new Label(5, count + 1, result.getReceiveFile(), cellFormat));
                    writableSheet.addCell(new Label(6, count + 1, result.getGetbackFile(), cellFormat));
                    writableSheet.addCell(new Label(7, count + 1, result.getOperationType(), cellFormat));
                    writableSheet.addCell(new Label(8, count + 1, result.getFlowStatus(), cellFormat));
                    writableSheet.addCell(new Label(9, count + 1, result.getModifyUserName(), cellFormat));
                    count++;
                }
            }

            // 主体内容生成结束
            workBook.write(); // 写入文件
            workBook.close();
            os.close(); // 关闭流
        } catch (WriteException writeException) {
            resultHint = "导出Excel失败!";
            LogUtil.info(AppCoopAction.class, projectName + "导出Excel失败.");
            writeException.printStackTrace();
        } catch (IOException e) {
            resultHint = "导出Excel失败!";
            LogUtil.info(AppCoopAction.class, projectName + "导出Excel失败.");
            e.printStackTrace();
        }
        resultHint = "导出Excel成功.";
        LogUtil.info(AppCoopAction.class, projectName + "导出Excel成功.");
    }

    /**
	 * 提交组内审查
	 * @return
	 */
	public String submitForGroupCheck(){
		
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.submitForGroupCheck() called……提交组内确认……");
		
		boolean underDepCheck = isUnderCheck("dep");
		
		if(underDepCheck){
			resultHint = "架构部评审中，请等待评审结果！";
			LogUtil.info(AppCoopAction.class,resultHint);
			return query();
		}	

		boolean updateBySelf = anyUpdateBySelf();
		if(!updateBySelf){
			resultHint = "未查询到您需提交组内确认的修改记录！";
			LogUtil.info(AppCoopAction.class,resultHint);
			return query();
		}
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		String modifyUser = (String) session.get("username");
		
		String result = sysArchManager.updateAppServiceTmpForGroupCheckByProjectAndUserName(projectName,modifyUser);
		
		if("success".equals(result)){
			resultHint = "提交成功";
		}else{
			resultHint = "提交失败，原因："+result;
		}
		
		LogUtil.info(AppCoopAction.class,resultHint);
		
		return query();
		
	}
	
	public String submitForDepCheck(){
		
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.submitForDepCheck() called……提交架构部确认……");
		
		boolean underDepCheck = isUnderCheck("dep");
		
		if(underDepCheck){
			resultHint = "已提交架构部确认，无需重复提交，请等待确认结果！";
			LogUtil.info(AppCoopAction.class,resultHint);
			return query();
		}
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		
		String user = (String) session.get("name");

		Project project = (Project) session.get(projectName);

		if(!user.equals(project.getTechManager())){
			resultHint = "需由技术经理确认后统一提交！";
			LogUtil.info(AppCoopAction.class,resultHint);
			return query();
		}
		
		// 检查是否所有修改内容均已提交
		List<AppServiceTmp> listNotSubmit = sysArchManager.getAppServiceListNotSubmitForGroupCheckByProject(projectName);
		if(null == listNotSubmit || 0 == listNotSubmit.size()){
			// 均已提交
			
		}else{
			
			resultHint = "有"+listNotSubmit.size()+"项修改内容未提交确认，无法提交部门审核！请查看编辑中状态的修改项！";

			LogUtil.info(AppCoopAction.class,resultHint);
			
			operationTypeInfo = 0;
			
			return query();
			
		}

		String result = sysArchManager.updateAppServiceTmpForDepCheckByProject(projectName);
		
		if("success".equals(result)){
			resultHint = "提交成功";
		}else{
			resultHint = "提交失败，原因："+result;
		}

		LogUtil.info(AppCoopAction.class,resultHint);
		return query();
	}
	
	public String returnToDeveloper(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.returnToDeveloper() called……退回开发人员……");
		sysArchManager.updateAppServiceTmpFlowStatusById(selectedAppServiceId,"编辑中");
		LogUtil.info(AppCoopAction.class,"修改服务:"+selectedAppServiceId+",状态为编辑中");
		return query();
	}
	
	public String returnToTechManager(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.returnToTechManager() called……退回技术代表……");
		sysArchManager.updateAppServiceTmpFlowStatusById(selectedAppServiceId,"待项目组确认");
		LogUtil.info(AppCoopAction.class,"修改服务:"+selectedAppServiceId+",状态为待项目组确认");
		
		return query();
	}
	
	/**
	 * QA查询
	 */
	public String checkForQA(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.QACheck() called……查询项目应用协作提交情况……");
		projectForCheck = sysArchManager.getProjectForCheck(projectName);
		projectForCheck.setProjectId(projectName);
		LogUtil.info(AppCoopAction.class,"AppCoopAction.QACheck() 查询结果……"+projectForCheck);
		
		return "QA";
	}
	
	public String updateProjectWhiteList(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.updateProjectWhiteList() called……"+projectName);
		ProjectWhiteList pwl = new ProjectWhiteList();
		pwl.setProjectId(projectName);
		sysArchManager.updatePorjectWhiteList(pwl);
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.updateProjectWhiteList() complete");
		
		return "QA";

	}
	
	public String removeFromProjectWhiteList(){
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.removeFromProjectWhiteList() called……"+projectName);
		ProjectWhiteList pwl = new ProjectWhiteList();
		pwl.setProjectId(projectName);
		sysArchManager.removeFromPorjectWhiteList(pwl);
		LogUtil.info(AppCoopAction.class,"操作：AppCoopAction.removeFromProjectWhiteList() complete");
		
		return "QA";

	}
	
	
	/**
	 * 架构部审查
	 * @return
	 */
	public String depCheck(){
		
		projectForDepCheckList = sysArchManager.getProjectUnderDepCheck();
		
		if(projectForDepCheckList.size() == 0){
			this.resultHint = "暂无待审核项目！";
		}else{
			this.resultHint = "共有"+projectForDepCheckList.size()+"个项目正维护应用协作";
		}
		
		return "depCheck";
	}
	
	/**
	 * 加载前准备
	 */
	@Override
	public void prepare(){
		try{
			if(appBaseInfoList == null){
				appBaseInfoList = sysArchManager.listAppBseInfo();
				dicAppServiceTypeList = sysArchManager.listDicAppServiceType();
				dicFlowStatusList = sysArchManager.listDicFlowStatus();
				Map<String, Object> map = new HashMap<String, Object>();
				try{			
					map.put("appBaseInfo", appBaseInfoList);	
				}catch (Exception e){
					e.printStackTrace();
				}			
				this.appBaseInfoJson = JSONUtil.toJson(map);
			}
			
			
			Map<String, Object> session = ActionContext.getContext().getSession();

			@SuppressWarnings("unchecked")
			List<Project> projectList = (List<Project>) session.get("projectList");
			if(null != projectList){
				Map<String, Object> map = new HashMap<String, Object>();
				try {
					map.put("projectList", projectList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.projectInfoJson = JSONUtil.toJson(map);
		
				projectToTechManagerMap = new HashMap<String, String>();
				for (Project proj : projectList) {
					String projName = proj.getProjectName();
					String techMana = proj.getTechManager();
		
					projectToTechManagerMap.put(projName, techMana);
				}
		
				session.put("projectToTechManagerMap", projectToTechManagerMap);
				
			}
		}catch(Exception e){
			e.printStackTrace();
			resultHint = "准备数据有误……请重新登录后再试";
			LogUtil.error(AppCoopAction.class, "prepare with Exception: "+e.getLocalizedMessage());
		}
		
	}

}
