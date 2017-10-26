package com.arch.serviceImpl;

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
import com.arch.dao.IManageAppCoopDao;
import com.arch.service.SysArchManager;
import com.arch.util.ListUtil;
import com.arch.util.LogUtil;
import com.arch.util.SSOUtil;

public class SysArchManagerImpl implements SysArchManager {

	private IManageAppCoopDao manageAppCoopDao;

	/**
	 * @return the manageAppCoopDao
	 */
	public IManageAppCoopDao getManageAppCoopDao() {
		return manageAppCoopDao;
	}

	/**
	 * @param manageAppCoopDao the manageAppCoopDao to set
	 */
	public void setManageAppCoopDao(IManageAppCoopDao manageAppCoopDao) {
		this.manageAppCoopDao = manageAppCoopDao;
	}

	@Override
	public List<AppBaseInfo> listAppBseInfo() {
		
		LogUtil.info(SysArchManagerImpl.class, "listAppBseInfo called...");
		
		try{
			
			List<AppBaseInfo> lst = manageAppCoopDao.listAppBaseInfo();
			
			LogUtil.info(SysArchManagerImpl.class, "listAppBseInfo complete...");

			return lst;
		}catch(Exception e){
			e.printStackTrace();
			LogUtil.info(SysArchManagerImpl.class, "listAppBseInfo complete...Exception:"+e);

			return null;
		}
	}

	@Override
	public void addAppService(AppService appService, List<AppServiceDetail> appServiceDetailList) {
		LogUtil.info(SysArchManagerImpl.class, "addAppService called...appService:"+appService.toString()+",\nappServiceDetailList："+ListUtil.parseList(appServiceDetailList));
		try{
			manageAppCoopDao.saveOrUpdateAppService(appService);		
			if(appServiceDetailList != null){
				for(AppServiceDetail appServiceDetail : appServiceDetailList)
					manageAppCoopDao.saveOrUpdateAppServiceDetail(appServiceDetail);
			}
			LogUtil.info(SysArchManagerImpl.class, "addAppService complete successfully...");

		}catch(Exception e){
			LogUtil.info(SysArchManagerImpl.class, "addAppService complete with Error...Exception:"+e);
		}
	}
	
	@Override
	public void deleteAppServiceById(Integer appServiceId) {
		LogUtil.info(SysArchManagerImpl.class, "deleteAppServiceById called...appServiceId:"+appServiceId);
		
		try{
			manageAppCoopDao.deleteAppServiceById(appServiceId);
			manageAppCoopDao.deleteAppServiceDetailByAppServiceId(appServiceId);
			LogUtil.info(SysArchManagerImpl.class, "deleteAppServiceById complete successfully...");

		}catch(Exception e){
			LogUtil.info(SysArchManagerImpl.class, "deleteAppServiceById complete with Error...Exception:"+e);
		}
		
	}

	@Override
	public List<DicAppServiceType> listDicAppServiceType() {
		LogUtil.info(SysArchManagerImpl.class, "listDicAppServiceType called...");
		List<DicAppServiceType> lst = null;
		try{
			lst = manageAppCoopDao.listDicAppServiceType();
			LogUtil.info(SysArchManagerImpl.class, "listDicAppServiceType complete successfully...");			
		}catch(Exception e){
			LogUtil.info(SysArchManagerImpl.class, "listDicAppServiceType complete with Error...Exception:"+e);
		}
		return lst;
	}

	@Override
	public Integer getTbSeqNumByName(String name) {
		
		LogUtil.info(SysArchManagerImpl.class, "getTbSeqNumByName called...table name:"+name);

		Integer seqNum = null;
		try{
			seqNum = manageAppCoopDao.getSeqNumByName(name);
			LogUtil.info(SysArchManagerImpl.class, "getTbSeqNumByName complete successfully... seqNum:"+seqNum);
		}catch(Exception e){
			LogUtil.error(SysArchManagerImpl.class, "getTbSeqNumByName complete with error...Exception:"+e);
		}
		
		return seqNum;				
	}

	@Override
	public List<AppRelResult> listAppServiceByAppRelQuery(AppRelQuery arq) {
		return manageAppCoopDao.queryAppService(arq);
	}

	@Override
	public Integer getTotalCountByAppRelQuery(AppRelQuery arq) {
		return manageAppCoopDao.queryAppServiceCount(arq);
	}

	@Override
	public AppService qryAppServiceById(Integer appServiceId) {
		return manageAppCoopDao.qryAppServiceById(appServiceId);
	}

	@Override
	public List<AppServiceDetail> qryAppServiceDetailByServiceId(
			Integer appServiceId) {
		return manageAppCoopDao.qryAppServiceDetailByServiceId(appServiceId);
	}

	@Override
	public DicAppServiceType getServcieTypeById(Integer serviceTypeId) {
		return manageAppCoopDao.getServcieTypeById(serviceTypeId);
	}

	@Override
	public void deleteAppServiceDetailById(Integer appServiceDetailId) {
		this.manageAppCoopDao.deleteAppServiceDetailById(appServiceDetailId);		
	}

	@Override
	public AppBaseInfo getAppBaseInfoById(Integer appId) {
		return manageAppCoopDao.getAppBaseInfoById(appId);
	}

	@Override
	public void addAppServiceTmp(AppServiceTmp appServicetmp,
			List<AppServiceDetailTmp> appServiceDetailTmpList) {
			
		manageAppCoopDao.saveOrUpdateAppServiceTmp(appServicetmp);		
		
		if(appServiceDetailTmpList != null){
			for(AppServiceDetailTmp appServiceDetailTmp : appServiceDetailTmpList)
				manageAppCoopDao.saveOrUpdateAppServiceDetailTmp(appServiceDetailTmp);
		}
		
	}

	@Override
	public AppServiceTmp qryAppServiceTmpById(Integer selectedAppServiceId) {
		return manageAppCoopDao.qryAppServiceTmpById(selectedAppServiceId);
	}

	@Override
	public List<AppServiceDetailTmp> qryAppServiceDetailTmpByServiceId(
			Integer appServiceId) {
		return manageAppCoopDao.qryAppServiceDetailTmpByServiceId(appServiceId);
	}

	@Override
	public void deleteAppServiceTmpById(Integer selectedAppServiceId) {
		manageAppCoopDao.deleteAppServiceTmpById(selectedAppServiceId);
		manageAppCoopDao.deleteAppServiceDetailTmpByAppServiceId(selectedAppServiceId);
	}

	@Override
	public AppServiceTmp getAppServiceTmpByPubAppServiceId(Integer appServiceId) {
		
		return manageAppCoopDao.getAppServiceTmpByPubAppServiceId(appServiceId);
		
	}

	@Override
	public List<DicFlowStatus> listDicFlowStatus() {
		return manageAppCoopDao.listDicFlowStatus();
	}

	@Override
	public String updateAppServiceTmpForGroupCheckByProjectAndUserName(
			String projectName,String modifyUser) {
		try{
			manageAppCoopDao.updateAppServiceTmpForGroupCheckByProjectIdAndUser(projectName, modifyUser);
		}catch(Exception e){
			return e.getMessage();
		}
		
		return "success";
	}

	@Override
	public String updateAppServiceTmpForDepCheckByProject(
			String projectName) {
		try{
			manageAppCoopDao.updateAppServiceTmpForDepCheckByProjectId(projectName, SSOUtil.getInstance().getCurrentTime());
		}catch(Exception e){
			return e.getMessage();
		}
		
		return "success";
	}

	@Override
	public List<AppServiceTmp> getAppServiceListNotSubmitForGroupCheckByProject(String projectName) {		
		return manageAppCoopDao.getAppServiceTmpListByProjectAndStatus(projectName,"编辑中");
	}

	@Override
	public List<AppServiceTmp> getAppServiceTmpListByProjectAndModifyUser(String projectName,
			String modifyUser) {
		return manageAppCoopDao.getAppServiceTmpListByProjectAndModifyUserForGroupCheck(projectName,modifyUser);
	}

	@Override
	public void updateAppServiceTmpFlowStatusById(Integer appServiceId, String flowStatus) {
		manageAppCoopDao.updateAppServiceTmpFlowStatusById(appServiceId,flowStatus);
	}

	@Override
	public String publishAppServiceTmpById(Integer serviceId) {
		return manageAppCoopDao.publishAppServiceTmpById(serviceId);
	}

	@Override
	public String publishAllByProjectId(String projectId) {		
		return manageAppCoopDao.publishAllAppServiceTmpByProjectId(projectId);
	}

	@Override
	public boolean isProjectUnderDepCheck(String projectName) {
		
		List<AppServiceTmp> tmpList = manageAppCoopDao.listAppServiceTmpByProjectName(projectName);
		if(null == tmpList || 0 == tmpList.size()){
			return false;
		}
		
		// 列出该项目下所有状态为待架构部审查的服务
		List<AppServiceTmp> list = manageAppCoopDao.listAppServiceTmpNotUnderDepCheckByProjectName(projectName);
		
		// 如果没有找到不在“待架构部确认”、“确认待发布”的服务，则该项目处于待架构部审查状态
		if(null == list || 0 == list.size()){
			return true;
		}
		
		return false;
	}

	@Override
	public void refresh() {
		manageAppCoopDao.refresh();
	}

	@Override
	public ProjectForCheck getProjectForCheck(String projectName) {
		return manageAppCoopDao.getProjectForCheck(projectName);
	}

	@Override
	public void updatePorjectWhiteList(ProjectWhiteList pwl) {
		manageAppCoopDao.updatePorjectWhiteList(pwl);		
	}
	
	@Override
	public void removeFromPorjectWhiteList(ProjectWhiteList pwl) {
		manageAppCoopDao.removeFromPorjectWhiteList(pwl);		
	}

	@Override
	public void deleteAppServiceDetailTmpById(Integer appServiceDetailId) {
		manageAppCoopDao.deleteAppServiceDetailTmpById(appServiceDetailId);
	}

	@Override
	public List<ProjectForDepCheck> getProjectUnderDepCheck() {
		return manageAppCoopDao.getProjectUnderDepCheck();
	}

	@Override
	public Integer getPubAppServiceId(AppServiceTmp ast) {
		// TODO Auto-generated method stub
		return manageAppCoopDao.getPubAppServiceId(ast);
	}
	
}
