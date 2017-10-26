/**
 * 
 */
package com.arch.daoImpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.arch.beans.UpAppRelQry;
import com.arch.beans.UpAppRelResult;
import com.arch.dao.IShowAppRelDao;
import com.arch.util.LogUtil;

/**
 * @author xiefangkai
 *
 */
public class ShowAppRelDao extends HibernateDaoSupport implements IShowAppRelDao {

	@SuppressWarnings("unchecked")
	@Override
	public String getDownAppRelInfo(Integer serviceId, String operationType) {

		// String title=null;
		StringBuffer showCode = new StringBuffer(); //
		HibernateTemplate ht = this.getHibernateTemplate();
		String sqlText = null;
		sqlText = "{call showChildLst(  ?,? )}";

		SQLQuery query = ht.getSessionFactory().getCurrentSession().createSQLQuery(sqlText);
		/*
		 * query.setParameter(0, serviceDesc); query.setParameter(1, appName);
		 * 
		 * query.setParameter(0, 28); query.setParameter(1, "11002000084-01");
		 */
		query.setParameter(0, serviceId);
		query.setParameter(1, operationType);

		LogUtil.info(ShowAppRelDao.class, "call showChildLst("+serviceId+","+operationType+")");
		
		List<Object[]> list;

		try {
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		int elementCount = 0;
		String[] str = null;
		List<String> tmpStrList = new ArrayList<String>(); // 排除连续的重复的框

		// 查询下游调用服务
		for (Object obj[] : list) {
			if (showCode.length() == 0) {
				// showCode.append("title: " + (String) obj[0] + ":"+ (String)
				// obj[1] + "\r\n");
				showCode.append("participant " + (String) obj[0]);
				showCode.append("\r\n");
			}

			str = ((String) obj[3]).split("\r\n");
			for (String s : str) {
				if (!tmpStrList.contains(s)) {
					if (elementCount == 0) {
						showCode.append("loop 起点服务\r\n");
					}

					showCode.append(s);
					showCode.append("\r\n");

					if (elementCount == 0) {
						showCode.append("end\r\n");
					}
					tmpStrList.add(s);
					elementCount = elementCount + 1;
				}
			}
		}

		LogUtil.info(ShowAppRelDao.class, "showCode:"+showCode);
		return showCode.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Object> listUpAppRelResult(UpAppRelQry qry) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Integer serviceId = qry.getAppServiceId();
		String operationType = qry.getOperationType();
		int rowCount = 0;

		HibernateTemplate ht = this.getHibernateTemplate();
		String sqlText = null;
		sqlText = "{call showParentLst(  ?,? )}";
		SQLQuery query = ht.getSessionFactory().getCurrentSession().createSQLQuery(sqlText);
		query.setParameter(0, serviceId);
		query.setParameter(1, operationType);
		
		LogUtil.info(ShowAppRelDao.class, "call showParentLst("+serviceId+","+operationType+")");


		List<Object> list = new ArrayList<Object>();
		List<Object[]> result;
		try {
			result = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		List<Object> appNameList = new ArrayList<Object>();
		Map<String, List<Object>> nameToServListMap = new HashMap<String, List<Object>>();

		for (Object obj[] : result) {

			UpAppRelResult apr = new UpAppRelResult();
			apr.setAppServiceId(((BigInteger) obj[0]).intValue());
			apr.setAppName((String) obj[1]);
			if (!appNameList.contains((String) obj[1])) {
				appNameList.add((String) obj[1]);
			}
			apr.setServiceType((String) obj[2]);
			apr.setServiceCode((String) obj[3]);
			apr.setAppInnerCode((String) obj[4]);
			apr.setServiceName((String) obj[5]);
			apr.setServiceDesc((String) obj[6]);
			apr.setCheckNoId((Integer) obj[7]);
			apr.setSeqNoId((String) obj[8]);
			apr.setOperationType((String) obj[9]);
			apr.setSourceType((String) obj[10]);
			rowCount = (Integer) obj[11];
			list.add(apr);

			LogUtil.debug(ShowAppRelDao.class, apr.toString());
			
			if (nameToServListMap.containsKey((String) obj[1])) {
				nameToServListMap.get((String) obj[1]).add(apr);
			} else {
				List<Object> a = new ArrayList<Object>();
				a.add(apr);
				nameToServListMap.put((String) obj[1], a);
			}
		}

		resultMap.put("upAppRelResult", list);
		resultMap.put("upAppNameList", appNameList);
		resultMap.put("recordCount", rowCount);
		resultMap.put("detail", nameToServListMap);
		
		LogUtil.info(ShowAppRelDao.class, rowCount+"");


		return resultMap;
	}

	@Override
	public String getUpAppRelInfo(Integer checkNoId, String seqNoId, String operationType) {

		// String title=null;
		StringBuffer showCode = new StringBuffer(); //
		// 规则代码的前缀代码,用于对系统名称进行排序
		StringBuffer prefixShowCode = new StringBuffer(); //
		HibernateTemplate ht = this.getHibernateTemplate();
		String sqlText = null;
		sqlText = "{call showParentTree(  ?,?,?)}";
		SQLQuery query = ht.getSessionFactory().getCurrentSession().createSQLQuery(sqlText);
		query.setParameter(0, checkNoId);
		query.setParameter(1, seqNoId);
		query.setParameter(2, operationType);

		LogUtil.info(ShowAppRelDao.class, "call showParentLst("+checkNoId+","+seqNoId+","+operationType+")");

		
		@SuppressWarnings("unchecked")
		List<Object[]> list = query.list();
		List<String> participant = new ArrayList<String>();

		int elementCount = 0;
		String[] str = null;
		String tmpstr = "";
		// 查询上游调用服务
		for (Object obj[] : list) {

			if (!participant.contains((String) obj[1])) {
				participant.add(0, (((String) obj[1])));
			}
			if (!participant.contains((String) obj[0])) {
				participant.add(0, (((String) obj[0])));
			}

			// 将得到的数据写入list,用于后续去重.
			// tmpList为list中上一个元素,用于和单前元素对比
			str = ((String) obj[3]).split("\r\n");
			for (String s : str) {
				elementCount = elementCount + 1;
				if (!s.equals(tmpstr)) {
					// 此处增加loop循环为了标示 服务的起点,对服务的起点增加红框
					// 前提条件:默认服务的起点为第3个元素
					if (elementCount == 3 * list.size()) {
						showCode.append("loop 终点服务\r\n" + s + "\r\n" + "end\r\n");
					} else {
						showCode.append(s + "\r\n");
					}
					tmpstr = s;
				}
			}
			// 对系统展现顺序进行排序
			for (String appName : participant) {
				prefixShowCode.append("participant " + appName + "\r\n");
			}
		}

		LogUtil.info(ShowAppRelDao.class, "showCode in ShowAppRelDao:" + prefixShowCode.toString() + showCode.toString());

		return prefixShowCode.toString() + showCode.toString();
	}
}
