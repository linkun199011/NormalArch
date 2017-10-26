package com.arch.daoImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.arch.beans.RelatedAppInfoQuery;
import com.arch.beans.RelatedAppInfoResult;
import com.arch.dao.IShowSysRelDao;

public class ShowSysRelDao extends HibernateDaoSupport implements IShowSysRelDao {

	/**
	 * 列出所有关联（直连、间连）系统清单
	 */
	public List<Object> listRelatedAppInfoResult(RelatedAppInfoQuery qry){
			
		Integer appId = qry.getAppId();
		String operationType = qry.getOperationType();
		System.out.println(appId+","+operationType);
		
		HibernateTemplate ht = this.getHibernateTemplate();
		String sqlText = null;
		sqlText = "{call showRelAppLst(  ?,? )}";
		SQLQuery query = ht.getSessionFactory().getCurrentSession()
				.createSQLQuery(sqlText);
		query.setParameter(0, appId);
		query.setParameter(1, operationType);
		

		List<Object> list = new ArrayList<Object>();
		List<Object[]> result;
		try{
			result = query.list();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		for (Object obj[] : result) {

			RelatedAppInfoResult rair = new RelatedAppInfoResult();
			rair.setAppId((Integer) obj[0]);
			rair.setAppName((String) obj[1]);
			rair.setAppShortName((String) obj[2]);
			rair.setAppCode((String) obj[3]);
			rair.setServiceLevel((String)obj[4]);
			rair.setCategory_lv1((String) obj[5]);
			rair.setCategory_lv2((String) obj[6]);
			rair.setSystemLevel((String) obj[7]);
			rair.setSecurityLevel((String) obj[8]);
			rair.setServiceTime((String) obj[9]);
			rair.setRelAppPlat((String) obj[10]);
			
			list.add(rair);
			
		}
			
		return list;
	}
}
	
