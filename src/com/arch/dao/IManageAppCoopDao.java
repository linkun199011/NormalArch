package com.arch.dao;

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

public interface IManageAppCoopDao {
	
	// 获取AppBaseInfo
	public AppBaseInfo getAppBaseInfoById(Integer appId);

	// 列出所有AppBaseInfo
	public List<AppBaseInfo> listAppBaseInfo() throws Exception;
	
	// 保存或更新AppService
	public void saveOrUpdateAppService(AppService obj);
	
	// 删除AppService
	public void deleteAppServiceById(Integer appServiceId);
	
	// 获取AppRel的结果集
	public List<AppRelResult> queryAppService(AppRelQuery arq);
	
	// 获取AppService的数量
	public Integer queryAppServiceCount(AppRelQuery arq);
	
	// 获取AppService
	public AppService qryAppServiceById(Integer appServiceId);
	
	//列出所有AppServiceType
	public List<DicAppServiceType> listDicAppServiceType();
	
	// 通过serviceTypeId获取ServiceType
	public DicAppServiceType getServcieTypeById(Integer serviceTypeId);
	
	// 保存或更新AppServiceDetail
	public void saveOrUpdateAppServiceDetail(AppServiceDetail obj);
	
	// 通过AppServiceId删除AppServiceDetail
	public void deleteAppServiceDetailByAppServiceId(Integer appServiceId);
	
	// 通过Id删除AppServiceDetail
	public void deleteAppServiceDetailById(Integer appServiceDetailId);
	
	// 通过appServiceId
	public List<AppServiceDetail> qryAppServiceDetailByServiceId(Integer appServiceId);
	
	// 根据表名查询当前ID
	public Integer getSeqNumByName(String name);
	
	// 保存或更新AppServiceTmp
	public void saveOrUpdateAppServiceTmp(AppServiceTmp obj);
	
	// 获取AppService
	public AppServiceTmp qryAppServiceTmpById(Integer appServiceId);	
	
	// 删除AppServiceTmp
	public void deleteAppServiceTmpById(Integer appServiceId);
	
	// 保存或更新AppServiceDetailTmp
	public void saveOrUpdateAppServiceDetailTmp(AppServiceDetailTmp obj);
	
	// 通过appServiceId获取AppServiceDetailTmp
	public List<AppServiceDetailTmp> qryAppServiceDetailTmpByServiceId(Integer appServiceId);
	
	// 通过AppServiceId删除AppServiceDetailTmp
	public void deleteAppServiceDetailTmpByAppServiceId(Integer appServiceId);
	
	public void deleteAppServiceDetailTmpById(Integer appServiceDetailId);

	
	// 通过pubAppServiceId查询AppServiceTmp
	public AppServiceTmp getAppServiceTmpByPubAppServiceId(Integer pubAppServiceId);
	
	// 将相通projectIdAppServiceTmp内容提交后进行审查
	public void updateAppServiceTmpForGroupCheckByProjectIdAndUser(String projectId,String modifyUser);
	
	public void updateAppServiceTmpForDepCheckByProjectId(String projectId, String submitDate);
	
	public List<DicFlowStatus> listDicFlowStatus();
	
	public List<AppServiceTmp> getAppServiceTmpListByProjectAndModifyUserForGroupCheck(String projectName, String modifyUser);
	
	public List<AppServiceTmp> getAppServiceTmpListByProjectAndStatus(String projectId, String flowStatus);
	
	public void updateAppServiceTmpFlowStatusById(Integer appServiceId, String flowStatus);
	
	public String publishAppServiceTmpById(Integer appServiceId);
	
	public String publishAllAppServiceTmpByProjectId(String projectId);

	public List<AppServiceTmp> listAppServiceTmpNotUnderDepCheckByProjectName(String projectName);
	
	public List<AppServiceTmp> listAppServiceTmpByProjectName(String projectName);
	
	public ProjectForCheck getProjectForCheck(String projectName);
	
	public void updatePorjectWhiteList(ProjectWhiteList pwl);
	
	public void removeFromPorjectWhiteList(ProjectWhiteList pwl);

	public List<ProjectForDepCheck> getProjectUnderDepCheck();
		
	public void refresh();
	
	public Integer getPubAppServiceId(AppServiceTmp ast);

}
