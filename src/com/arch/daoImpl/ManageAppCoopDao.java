package com.arch.daoImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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
import com.arch.beans.TbSeqNum;
import com.arch.dao.IManageAppCoopDao;
import com.arch.util.LogUtil;

public class ManageAppCoopDao extends HibernateDaoSupport implements
		IManageAppCoopDao {

	@Override
	public AppBaseInfo getAppBaseInfoById(Integer appId) {
		return getHibernateTemplate().get(AppBaseInfo.class, appId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppBaseInfo> listAppBaseInfo() throws Exception {
		return getHibernateTemplate().find(
				"from AppBaseInfo s where isFjnxApp = 'Y'");
	}

	@Override
	public void saveOrUpdateAppService(AppService obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	@Override
	public void deleteAppServiceById(Integer appServiceId) {
		String delQry = "delete from app_service where app_service_id = "
				+ appServiceId + "";
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(delQry);

		sql.executeUpdate();
	}

	@Override
	public List<AppRelResult> queryAppService(AppRelQuery arq) {

		String projectIdRestriction = "1";
		if (null != arq.getProjectId()) {
			projectIdRestriction = "a.project_id = '" + arq.getProjectId()
					+ "'";
		}
		String appIdRestriction = "1";
		if (null != arq.getAppId()) {
			appIdRestriction = "a.app_id = " + arq.getAppId();
		}
		String serviceNameRestriction = "1";
		if (null != arq.getServiceName()) {
			serviceNameRestriction = "a.service_name like '%"
					+ arq.getServiceName() + "%'";
		}

		String serviceCodeRestriction = "1";
		if (null != arq.getServiceCode()) {
			serviceCodeRestriction = "a.service_code = '"
					+ arq.getServiceCode() + "'";
		}
		
		String appInnerCodeRestriction = "1";
		if (null != arq.getAppInnerCode()) {
			appInnerCodeRestriction = "a.app_inner_code = '"
					+ arq.getAppInnerCode() + "'";
		}

		String flowStatusRestriction = "1";
		if (null != arq.getFlowStatus() && !"所有".equals(arq.getFlowStatus())) {
			flowStatusRestriction = "a.flow_status = '" + arq.getFlowStatus()
					+ "'";
		}

		Integer operationType = arq.getOperationType();
		if (null == operationType) {
			operationType = 0;
		}

		String tmpQry = "select a.app_service_id,"
				+ "b.APP_NAME,"
				+ "c.SERVICE_TYPE_NAME,"
				+ "a.SERVICE_CODE, "
				+ "a.APP_INNER_CODE,"
				+ "a.SERVICE_NAME,"
				+ "a.SERVICE_DESC,"
				+ "a.RECEIVE_FILE,"
				+ "a.GETBACK_FILE,"
				+ "a.operation_type, "
				+ "a.flow_status, "
				+ "a.modify_user_id, "
				+ "a.modify_user_name "
				+ "from app_service_tmp a, app_base_info b, dic_app_service_type c "
				+ "where a.APP_ID = b.APP_ID "
				+ "and a.SERVICE_TYPE_ID = c.SERVICE_TYPE_ID " + "and "
				+ projectIdRestriction + " " + "and " + appIdRestriction + " "
				+ "and " + serviceNameRestriction + " " + "and " + appInnerCodeRestriction +" " + "and "
				+ serviceCodeRestriction + " " + "and " + flowStatusRestriction;

		String pubQry = "select a.app_service_id,"
				+ "b.APP_NAME,"
				+ "c.SERVICE_TYPE_NAME,"
				+ "a.SERVICE_CODE, "
				+ "a.APP_INNER_CODE,"
				+ "a.SERVICE_NAME,"
				+ "a.SERVICE_DESC,"
				+ "a.RECEIVE_FILE,"
				+ "a.GETBACK_FILE, "
				+ "'基线', "
				+ "'已发布', "
				+ "'', "
				+ "'' "
				+ "from app_service a, app_base_info b, dic_app_service_type c "
				+ "where a.APP_ID = b.APP_ID "
				+ "and a.SERVICE_TYPE_ID = c.SERVICE_TYPE_ID " + "and "
				+ appIdRestriction + " " + "and " + serviceNameRestriction
				+ " " + "and " + serviceCodeRestriction
				+ " " + "and " + appInnerCodeRestriction;

		String query = "";
		if (0 == operationType) { // 修改中
			query = tmpQry;
		} else if (1 == operationType) { // 基线
			query = pubQry;
		} else {
			query = tmpQry + " union all " + "select a.app_service_id,"
					+ "b.APP_NAME," + "c.SERVICE_TYPE_NAME,"
					+ "a.SERVICE_CODE, " + "a.APP_INNER_CODE,"
					+ "a.SERVICE_NAME," + "a.SERVICE_DESC," + "a.RECEIVE_FILE,"
					+ "a.GETBACK_FILE, " + "'基线', " + "'已发布', " + "'', "
					+ "'' " + "from " + "app_service a," + "app_base_info b,"
					+ "dic_app_service_type c " + "WHERE "
					+ "a.app_service_id NOT IN " + "(SELECT "
					+ "IFNULL(x.PUB_APP_SERVICE_ID,'@new')"
					+ "FROM app_service_tmp x " + "where " + "x.project_id = '"
					+ arq.getProjectId() + "') " + "AND a.APP_ID = b.APP_ID "
					+ "AND a.SERVICE_TYPE_ID = c.SERVICE_TYPE_ID " + "and "
					+ appIdRestriction + " " + "and " + serviceNameRestriction
					+ " " + "and " + serviceCodeRestriction
					+ " " + "and " + appInnerCodeRestriction;
		}

		LogUtil.info(ManageAppCoopDao.class, "查询服务sql:" + query);

		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(query).setFirstResult(arq.getStartIndex())
				.setMaxResults(arq.getLength());

		List<AppRelResult> list = new ArrayList<AppRelResult>();
		@SuppressWarnings("unchecked")
		List<Object[]> result = sql.list();
		LogUtil.info(ManageAppCoopDao.class, "返回条数:" + result.size());

		for (Object[] obj : result) {

			AppRelResult apr = new AppRelResult();
			apr.setAppServiceId((Integer) obj[0]);
			apr.setAppName((String) obj[1]);
			apr.setServiceType((String) obj[2]);
			apr.setServiceCode((String) obj[3]);
			apr.setAppInnerCode((String) obj[4]);
			apr.setServiceName((String) obj[5]);
			apr.setServiceDesc((String) obj[6]);
			apr.setReceiveFile((String) obj[7]);
			apr.setGetbackFile((String) obj[8]);
			apr.setOperationType((String) obj[9]);
			apr.setFlowStatus((String) obj[10]);
			apr.setModifyUserId((String) obj[11]);
			apr.setModifyUserName((String) obj[12]);
			list.add(apr);

			LogUtil.debug(ManageAppCoopDao.class, "返回信息：" + apr.toString());
		}

		return list;
	}

	@Override
	public Integer queryAppServiceCount(AppRelQuery arq) {
		String projectIdRestriction = "1";
		if (null != arq.getProjectId()) {
			projectIdRestriction = "a.project_id = '" + arq.getProjectId()
					+ "'";
		}
		String appIdRestriction = "1";
		if (null != arq.getAppId()) {
			appIdRestriction = "a.app_id = " + arq.getAppId();
		}
		String serviceNameRestriction = "1";
		if (null != arq.getServiceName()) {
			serviceNameRestriction = "a.service_name like '%"
					+ arq.getServiceName() + "%'";
		}

		String serviceCodeRestriction = "1";
		if (null != arq.getServiceCode()) {
			serviceCodeRestriction = "a.service_code = '"
					+ arq.getServiceCode() + "'";
		}
		
		String appInnerCodeRestriction = "1";
		if (null != arq.getAppInnerCode()) {
			appInnerCodeRestriction = "a.app_inner_code = '"
					+ arq.getAppInnerCode() + "'";
		}

		String flowStatusRestriction = "1";
		if (null != arq.getFlowStatus()
				&& !"所有".equals(arq.getFlowStatus().trim())) {
			flowStatusRestriction = "a.flow_status = '" + arq.getFlowStatus()
					+ "'";
		}

		Integer operationType = arq.getOperationType();
		if (null == operationType) {
			return 0;
		}

		String pubQry = "select count(*) " + "from app_service a " + "where "
				+ appIdRestriction + " " + "and " + serviceNameRestriction
				+ " " + "and " + serviceCodeRestriction
				+ " " + "and " + appInnerCodeRestriction;
		String tmpQry = "select count(*) " + "from app_service_tmp a "
				+ "where " + projectIdRestriction + " " + "and "
				+ appIdRestriction + " " + "and " + serviceNameRestriction
				+ " " + "and " + serviceCodeRestriction + " " + "and " + appInnerCodeRestriction + " " + "and "
				+ flowStatusRestriction;
		String query = "";

		if (0 == operationType) {
			query = tmpQry;
		} else if (1 == operationType) {
			query = pubQry;
		} else {
			query = "select count(*) " + "from (" + "select a.app_id "
					+ "from app_service_tmp a " + "where "
					+ projectIdRestriction
					+ " "
					+ "and "
					+ appIdRestriction
					+ " "
					+ "and "
					+ serviceNameRestriction
					+ " "
					+ "and "
					+ serviceCodeRestriction
					+ " "
					+ "and "
					+ appInnerCodeRestriction
					+ " "
					+ "and "
					+ flowStatusRestriction
					+ " "
					+ "union all "
					+ "select a.app_id "
					+ "from app_service a "
					+ "where "
					+ "a.app_service_id NOT IN( "
					+ "SELECT "
					+ "IFNULL(x.PUB_APP_SERVICE_ID,'@new')"
					+ "FROM app_service_tmp x "
					+ "where "
					+ "x.project_id = '"
					+ arq.getProjectId()
					+ "') "
					+ "and "
					+ appIdRestriction
					+ " "
					+ "and "
					+ serviceNameRestriction
					+ " "
					+ "and "
					+ serviceCodeRestriction 
					+ " "
					+ "and "
					+ appInnerCodeRestriction + ") z";
		}

		LogUtil.info(ManageAppCoopDao.class, "查询服务sql:" + query);

		SQLQuery countQry = this.getSessionFactory().getCurrentSession()
				.createSQLQuery(query);
		int count = Integer.parseInt(countQry.uniqueResult().toString());
		LogUtil.info(ManageAppCoopDao.class, "返回总条数:" + count);

		return count;
	}

	@Override
	public AppService qryAppServiceById(Integer appServiceId) {
		return getHibernateTemplate().get(AppService.class, appServiceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DicAppServiceType> listDicAppServiceType() {
		return getHibernateTemplate().find("from DicAppServiceType s");
	}

	@Override
	public DicAppServiceType getServcieTypeById(Integer serviceTypeId) {
		return getHibernateTemplate().get(DicAppServiceType.class,
				serviceTypeId);
	}

	@Override
	public void saveOrUpdateAppServiceDetail(AppServiceDetail obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	@Override
	public void deleteAppServiceDetailByAppServiceId(Integer appServiceId) {
		String delQry = "delete from app_service_detail where app_service_id = "
				+ appServiceId;
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(delQry);

		sql.executeUpdate();
	}

	@Override
	public void deleteAppServiceDetailById(Integer appServiceDetailId) {
		String delQry = "delete from app_service_detail where app_service_detail_id = "
				+ appServiceDetailId;
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(delQry);

		sql.executeUpdate();
	}

	@Override
	public Integer getSeqNumByName(String name) {

		HibernateTemplate ht = this.getHibernateTemplate();

		String hql = "from TbSeqNum where tbName = '" + name + "'";

		Query qry = ht.getSessionFactory().getCurrentSession().createQuery(hql);

		qry.setLockOptions(LockOptions.UPGRADE);

		TbSeqNum tbSeqNum = (TbSeqNum) qry.list().get(0);

		Integer seqNo = tbSeqNum.getSeqNextVal();

		tbSeqNum.setSeqNextVal(seqNo + 1);

		ht.update(tbSeqNum);

		return seqNo;

	}

	@Override
	public void saveOrUpdateAppServiceTmp(AppServiceTmp obj) {
		getHibernateTemplate().saveOrUpdate(obj);

	}

	@Override
	public void saveOrUpdateAppServiceDetailTmp(AppServiceDetailTmp obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppServiceDetail> qryAppServiceDetailByServiceId(
			Integer appServiceId) {
		return getHibernateTemplate().find(
				"from AppServiceDetail where appServiceId=?", appServiceId);
	}

	@Override
	public AppServiceTmp qryAppServiceTmpById(Integer appServiceId) {
		return getHibernateTemplate().get(AppServiceTmp.class, appServiceId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppServiceDetailTmp> qryAppServiceDetailTmpByServiceId(
			Integer appServiceId) {
		return getHibernateTemplate().find(
				"from AppServiceDetailTmp where appServiceId=?", appServiceId);
	}

	@Override
	public void deleteAppServiceTmpById(Integer appServiceId) {
		String delQry = "delete from app_service_tmp where app_service_id = "
				+ appServiceId + "";
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(delQry);

		sql.executeUpdate();
	}

	@Override
	public void deleteAppServiceDetailTmpByAppServiceId(Integer appServiceId) {
		String delQry = "delete from app_service_detail_tmp where app_service_id = "
				+ appServiceId;
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(delQry);

		sql.executeUpdate();
	}

	@Override
	public AppServiceTmp getAppServiceTmpByPubAppServiceId(
			Integer pubAppServiceId) {

		@SuppressWarnings("unchecked")
		List<AppServiceTmp> list = getHibernateTemplate()
				.find("from AppServiceTmp s where s.flowStatus != '审核通过' and s.pubAppServiceId=?",
						pubAppServiceId);

		if (null == list || 0 == list.size()) {
			return null;
		} else {

			return list.get(0);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DicFlowStatus> listDicFlowStatus() {
		return this.getHibernateTemplate().find("from DicFlowStatus s");
	}

	@Override
	public void updateAppServiceTmpForGroupCheckByProjectIdAndUser(
			String projectId, String modifyUser) {
		HibernateTemplate ht = this.getHibernateTemplate();

		String prevStatus = "编辑中";

		@SuppressWarnings("unchecked")
		List<AppServiceTmp> appServiceTmpList = ht
				.find("from AppServiceTmp s where s.projectId=? and s.modifyUserId=? and s.flowStatus=?",
						projectId, modifyUser, prevStatus);

		for (AppServiceTmp appServiceTmp : appServiceTmpList) {
			appServiceTmp.setFlowStatus("待项目组确认");

			ht.update(appServiceTmp);
		}

	}

	@Override
	public void updateAppServiceTmpForDepCheckByProjectId(String projectId,
			String submitDate) {
		HibernateTemplate ht = this.getHibernateTemplate();

		String prevStatus = "待项目组确认";

		// 只针对已在组内确认状态的服务
		@SuppressWarnings("unchecked")
		List<AppServiceTmp> appServiceTmpList = ht.find(
				"from AppServiceTmp s where s.projectId=? and s.flowStatus=?",
				projectId, prevStatus);

		for (AppServiceTmp appServiceTmp : appServiceTmpList) {

			appServiceTmp.setSubmitDate(submitDate);
			appServiceTmp.setFlowStatus("待架构部确认");

			ht.update(appServiceTmp);

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppServiceTmp> getAppServiceTmpListByProjectAndModifyUserForGroupCheck(
			String projectName, String modifyUser) {
		return getHibernateTemplate()
				.find("From AppServiceTmp a where a.projectId=? and a.modifyUserId=? and a.flowStatus!=?",
						projectName, modifyUser, "待项目组确认");
	}

	@Override
	public void updateAppServiceTmpFlowStatusById(Integer appServiceId,
			String flowStatus) {
		HibernateTemplate ht = this.getHibernateTemplate();
		AppServiceTmp appServiceTmp = (AppServiceTmp) ht.get(
				AppServiceTmp.class, appServiceId);

		appServiceTmp.setFlowStatus(flowStatus);
		ht.update(appServiceTmp);
	}

	@Override
	public String publishAppServiceTmpById(Integer appServiceId) {
		LogUtil.info(ManageAppCoopDao.class,
				"publishAppServiceTmpById called...." + appServiceId);

		HibernateTemplate ht = this.getHibernateTemplate();
		String sqlText = null;
		sqlText = "{call publishByServiceId(?)}";
		SQLQuery query = ht.getSessionFactory().getCurrentSession()
				.createSQLQuery(sqlText);
		query.setParameter(0, appServiceId);

		try {
			@SuppressWarnings("unchecked")
			List<String> result = query.list();
			return result.get(0);

		} catch (Exception e) {
			LogUtil.error(
					ManageAppCoopDao.class,
					"publishAppServiceTmpById end with exception...."
							+ e.getLocalizedMessage());

			e.printStackTrace();
			return "系统异常，请与管理员联系";
		}

	}

	@Override
	public String publishAllAppServiceTmpByProjectId(String projectId) {

		LogUtil.info(ManageAppCoopDao.class,
				"publishAllAppServiceTmpByProjectId called...." + projectId);

		HibernateTemplate ht = this.getHibernateTemplate();
		String sqlText = null;
		sqlText = "{call publishByProjectId(?)}";
		SQLQuery query = ht.getSessionFactory().getCurrentSession()
				.createSQLQuery(sqlText);
		query.setParameter(0, projectId);

		try {
			@SuppressWarnings("unchecked")
			List<String> result = query.list();
			return result.get(0);
		} catch (Exception e) {
			LogUtil.error(ManageAppCoopDao.class,
					"publishAllAppServiceTmpByProjectId end with exception...."
							+ e.getLocalizedMessage());
			e.printStackTrace();
			return "系统异常，请与管理员联系";
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppServiceTmp> getAppServiceTmpListByProjectAndStatus(
			String projectId, String flowStatus) {
		return this
				.getHibernateTemplate()
				.find("From AppServiceTmp a where a.projectId =? and a.flowStatus =?",
						projectId, flowStatus);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppServiceTmp> listAppServiceTmpNotUnderDepCheckByProjectName(
			String projectName) {
		return this
				.getHibernateTemplate()
				.find("From AppServiceTmp a where a.projectId =? and a.flowStatus not in (?,?)",
						projectName, "待架构部确认", "确认待发布");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppServiceTmp> listAppServiceTmpByProjectName(String projectName) {
		return this.getHibernateTemplate().find(
				"From AppServiceTmp a where a.projectId =?", projectName);
	}

	@Override
	public void refresh() {
		HibernateTemplate ht = this.getHibernateTemplate();
		String sqlText = null;
		sqlText = "{call refreshSrvRelDev()}";
		SQLQuery query = ht.getSessionFactory().getCurrentSession()
				.createSQLQuery(sqlText);
		ArrayList<Object> list = (ArrayList<Object>) query.list();
		for (Object o : list) {
			System.out.println(o.toString());
		}
	}

	@Override
	public ProjectForCheck getProjectForCheck(String projectName) {

		ProjectForCheck pfc = new ProjectForCheck();

		/*
		 * 查询项目是否需要检查
		 */
		String qry_projectUnderCheck = "select * from proj_white_list where project_id = '"
				+ projectName + "'";
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(qry_projectUnderCheck);
		ArrayList<Object> list = (ArrayList<Object>) sql.list();
		if (null == list || list.size() == 0) {
			// 说明该项目不在白名单中，需检查
			pfc.setProjectUnderCheck(true);
			LogUtil.info(ManageAppCoopDao.class, "查询项目：" + projectName
					+ " 不在白名单中，需检查");

			/*
			 * 查询总条数
			 */
			String qry_total = "select count(*) from app_service_tmp where project_id = '"
					+ projectName + "'";
			SQLQuery countQry = (SQLQuery) this.getSessionFactory()
					.getCurrentSession().createSQLQuery(qry_total);
			;
			int totalCount = Integer.parseInt(countQry.uniqueResult()
					.toString());
			LogUtil.info(ManageAppCoopDao.class, "查询项目：" + projectName
					+ " 已登记条数：" + totalCount);
			/*
			 * 查询已完成条数
			 */
			String qry_complete = "select count(*) from app_service_tmp where project_id = '"
					+ projectName + "' and flow_status in('确认待发布','发布')";
			SQLQuery compQry = (SQLQuery) this.getSessionFactory()
					.getCurrentSession().createSQLQuery(qry_complete);
			;
			int compCount = Integer.parseInt(compQry.uniqueResult().toString());
			LogUtil.info(ManageAppCoopDao.class, "查询项目：" + projectName
					+ " 已完成条数：" + compCount);
			/*
			 * 待审查条数
			 */
			int leftCount = totalCount - compCount;
			LogUtil.info(ManageAppCoopDao.class, "查询项目：" + projectName
					+ " 待检查条数：" + leftCount);

			pfc.setNumOfAppCoopForCheck(totalCount);
			pfc.setNumOfAppCoopCompleteCheck(compCount);
			pfc.setNumOfAppCoopUnderCheck(leftCount);

		} else {
			pfc.setProjectUnderCheck(false);
			pfc.setNumOfAppCoopForCheck(-1);
			pfc.setNumOfAppCoopCompleteCheck(-1);
			pfc.setNumOfAppCoopUnderCheck(-1);
		}

		return pfc;
	}

	@Override
	public void updatePorjectWhiteList(ProjectWhiteList pwl) {
		this.getHibernateTemplate().saveOrUpdate(pwl);
	}

	@Override
	public void removeFromPorjectWhiteList(ProjectWhiteList pwl) {
		this.getHibernateTemplate().delete(pwl);
	}

	@Override
	public void deleteAppServiceDetailTmpById(Integer appServiceDetailId) {
		String delQry = "delete from app_service_detail_tmp where app_service_detail_id = "
				+ appServiceDetailId;
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(delQry);

		sql.executeUpdate();
	}

	public List<ProjectForDepCheck> getProjectUnderDepCheck() {

		String qrySQL = "SELECT t1.project_id,"
				+ "count(*) 总数,"
				+ "sum(if (flow_status= '编辑中',1,0))编辑中,"
				+ "sum(if (flow_status= '待项目组确认 ',1,0))待项目组确认,"
				+ "sum(if (flow_status= '待架构部确认',1,0))待架构部确认,"
				+ "sum(if (flow_status= '确认待发布',1,0))确认待发布 "
				+ " FROM app_service_tmp t1 "
				+ " group by project_id"
				+ " order by 5 desc";
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(qrySQL);

		List<Object[]> result = sql.list();
		LogUtil.info(ManageAppCoopDao.class, "返回条数:" + result.size());

		List<ProjectForDepCheck> list = new ArrayList<ProjectForDepCheck>();

		for (Object[] obj : result) {

			ProjectForDepCheck pdc = new ProjectForDepCheck();
			pdc.setProjectId((String) obj[0]);
			
			pdc.setTotalCount(((BigInteger) obj[1]).intValue());
			pdc.setNumOfEdit(((BigDecimal)obj[2]).intValue());
			pdc.setNumOfGroupCheck(((BigDecimal) obj[3]).intValue());
			pdc.setNumOfDepCheck(((BigDecimal) obj[4]).intValue());
			pdc.setNumOfPassCheck(((BigDecimal) obj[5]).intValue());
			list.add(pdc);

			LogUtil.info(ManageAppCoopDao.class, pdc.toString());
		}

		return list;

	}

	
	@Override
	public Integer getPubAppServiceId(AppServiceTmp ast) {
		
		Integer appId = ast.getAppId();
		String serviceName = ast.getServiceName();
		String serviceCode = ast.getServiceCode();
		 
		String qrySQL = "SELECT getServiceId("+appId+",'"+serviceCode+"','"+serviceName+"')";
		SQLQuery sql = (SQLQuery) this.getSessionFactory().getCurrentSession()
				.createSQLQuery(qrySQL);
		
		List<Object> list = new ArrayList<Object>();
		try{
		 list = sql.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return (Integer)list.get(0);
	}
}
