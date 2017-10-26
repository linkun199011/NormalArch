package com.arch.service;

import java.util.List;

import com.arch.beans.AppBaseInfo;
import com.arch.beans.AppRelQuery;
import com.arch.beans.AppRelResult;
import com.arch.beans.AppService;
import com.arch.beans.AppServiceDetail;
import com.arch.beans.AppServiceDetailTmp;
import com.arch.beans.AppServiceTmp;
import com.arch.beans.DicAppServiceType;
import com.arch.beans.DicFlowStatus;
import com.arch.beans.ProjectForCheck;
import com.arch.beans.ProjectForDepCheck;
import com.arch.beans.ProjectWhiteList;

public interface SysArchManager {
		
	public List<AppBaseInfo> listAppBseInfo();
	
	public List<DicAppServiceType> listDicAppServiceType();
	
	public List<AppRelResult> listAppServiceByAppRelQuery(AppRelQuery arq);
	
	public Integer getTotalCountByAppRelQuery(AppRelQuery arq);

	public void addAppService(AppService appService, List<AppServiceDetail> appServiceDetailList);
	
	public void addAppServiceTmp(AppServiceTmp appServiceTmp, List<AppServiceDetailTmp> appServiceDetailTmpList);

	public void deleteAppServiceById(Integer selectedAppServiceId);
	
	public void deleteAppServiceTmpById(Integer selectedAppServiceId);

	public AppService qryAppServiceById(Integer selectedAppServiceId);
	
	public List<AppServiceDetail> qryAppServiceDetailByServiceId(Integer appServiceId);
	
	public AppServiceTmp qryAppServiceTmpById(Integer selectedAppServiceId);

	public List<AppServiceDetailTmp> qryAppServiceDetailTmpByServiceId(Integer appServiceId);
	
	public Integer getTbSeqNumByName(String name);
	
	public DicAppServiceType getServcieTypeById(Integer serviceTypeId);
	
	public void deleteAppServiceDetailById(Integer appServiceDetailId);
	
	public void deleteAppServiceDetailTmpById(Integer appServiceDetailId);
	
	public AppBaseInfo getAppBaseInfoById(Integer appId);

	public AppServiceTmp getAppServiceTmpByPubAppServiceId(Integer appServiceId);
	
	public String updateAppServiceTmpForGroupCheckByProjectAndUserName(String projectName, String modifyUser);

	public String updateAppServiceTmpForDepCheckByProject(String projectName);
	
	public List<DicFlowStatus> listDicFlowStatus();
	
	public List<AppServiceTmp> getAppServiceListNotSubmitForGroupCheckByProject(String projectName);

	public List<AppServiceTmp> getAppServiceTmpListByProjectAndModifyUser(String projectName, String modifyUser);
	
	public void updateAppServiceTmpFlowStatusById(Integer appServiceId, String flowStatus);
		
	public String publishAppServiceTmpById(Integer appServiceId);
	
	public String publishAllByProjectId(String projectId);

	public boolean isProjectUnderDepCheck(String projectName);
	
	public ProjectForCheck getProjectForCheck(String projectName);
	
	public void updatePorjectWhiteList(ProjectWhiteList pwl);
	
	public void removeFromPorjectWhiteList(ProjectWhiteList pwl);

	public void refresh();
	
	public List<ProjectForDepCheck> getProjectUnderDepCheck();
	
	public Integer getPubAppServiceId(AppServiceTmp ast);
}

