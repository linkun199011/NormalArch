package com.arch.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.arch.beans.RelatedAppInfoQuery;
import com.arch.beans.RelatedAppInfoResult;
import com.arch.dao.IShowSysRelDao;
import com.arch.service.ShowSysRel;
import com.arch.util.ListUtil;
import com.arch.util.LogUtil;
import com.opensymphony.xwork2.ActionContext;

public class ShowSysRelImpl implements ShowSysRel {

	private IShowSysRelDao showSysRelDao;

	/**
	 * @return the showSysRelDao
	 */
	public IShowSysRelDao getShowSysRelDao() {
		return showSysRelDao;
	}

	/**
	 * @param showSysRelDao
	 *            the showSysRelDao to set
	 */
	public void setShowSysRelDao(IShowSysRelDao showSysRelDao) {
		this.showSysRelDao = showSysRelDao;
	}

	@Override
	public List<Object> listRelatedAppInfoResult(RelatedAppInfoQuery qry) {

		LogUtil.info(
				ShowAppRelImpl.class,
				"listRelatedAppInfoResult called...RelatedAppInfoQuery:"
						+ qry.toString());

		List<Object> relatedAppInfoResultLst = showSysRelDao
				.listRelatedAppInfoResult(qry);

		LogUtil.debug(ShowAppRelImpl.class,
				"listRelatedAppInfoResult complete...relatedAppInfoResultLst:"
						+ ListUtil.parseList(relatedAppInfoResultLst));

		// generate showCode for diagram
		String currentAppName = qry.getAppName();
		Integer currentAppId = qry.getAppId();
		String currentAppIdAndName = currentAppId+"-"+currentAppName;
		// 去重后的展示列表
		List<Object> sortList = new ArrayList<Object>();
		List<String> appNameList = new ArrayList<String>();

		List<String> qdzl = new ArrayList<String>();
		List<String> qdjl_esb = new ArrayList<String>();
		List<String> qdjl_afe = new ArrayList<String>();
		List<String> fwzl = new ArrayList<String>();
		List<String> fwjl_esb = new ArrayList<String>();
		List<String> fwjl_afe = new ArrayList<String>();

		for (Object obj : relatedAppInfoResultLst) {

			RelatedAppInfoResult appInfo = (RelatedAppInfoResult) obj;
			LogUtil.debug(ShowSysRelImpl.class, appInfo.toString());

			if (appInfo.getAppName().equals(currentAppName)) {
				continue;
			}

			// 筛选重复的apName8	
			if (!appNameList.contains(appInfo.getAppName())) {
				appNameList.add(appInfo.getAppName());
				sortList.add(appInfo);
			}

			if (appInfo.getCategory_lv1().contains("渠道")) {

				if (null == appInfo.getRelAppPlat()) {
					if (!qdzl.contains(appInfo.getAppName())) {
						qdzl.add(appInfo.getAppName());
					}
				} else if (appInfo.getRelAppPlat().contains("企业服务总线")) {
					// 通过ESB连接
					if (!qdjl_esb.contains(appInfo.getAppName())) {
						qdjl_esb.add(appInfo.getAppName());
					}
				} else if (appInfo.getRelAppPlat().contains("内联通讯前置")) {
					// 通过AFE连接
					if (!qdjl_afe.contains(appInfo.getAppName())) {
						qdjl_afe.add(appInfo.getAppName());
					}
				}

			} else {

				if (null == appInfo.getRelAppPlat()) {
					if (!fwzl.contains(appInfo.getAppName())) {
						fwzl.add(appInfo.getAppName());
					}
				} else if (appInfo.getRelAppPlat().contains("企业服务总线")) {
					// 通过ESB连接
					if (!fwjl_esb.contains(appInfo.getAppName())) {
						fwjl_esb.add(appInfo.getAppName());
					}
				} else if (appInfo.getRelAppPlat().contains("内联通讯前置")) {
					// 通过AFE连接
					if (!fwjl_afe.contains(appInfo.getAppName())) {
						fwjl_afe.add(appInfo.getAppName());
					}
				}

			}

		}

		// 拼装 showCode
		StringBuffer sb = new StringBuffer();
		String connector = "-->";
		if (qdzl.size() != 0) {
			sb.append("直连系统*[");
			for (int i = 0; i <= qdzl.size() - 2; i++) {
				sb.append("<li>" + qdzl.get(i)
						+ "&nbsp&nbsp&nbsp&nbsp</li><br>");
			}
			sb.append("<li>" + qdzl.get(qdzl.size() - 1) + "]");
			sb.append(connector + currentAppName + "\r\n");
		}

		if (qdjl_esb.size() != 0) {
			sb.append("ESB*系统[");
			for (int i = 0; i <= qdjl_esb.size() - 2; i++) {
				sb.append("<li>" + qdjl_esb.get(i)
						+ "&nbsp&nbsp&nbsp&nbsp</li><br>");
			}
			sb.append("<li>" + qdjl_esb.get(qdjl_esb.size() - 1)
					+ "&nbsp&nbsp&nbsp&nbsp</li>]");
			sb.append(connector + "ESB*\r\n");
			sb.append("ESB*" + connector + currentAppName + "\r\n");
		}

		if (qdjl_afe.size() != 0) {
			sb.append("AFE*系统[");
			for (int i = 0; i <= qdjl_afe.size() - 2; i++) {
				sb.append("<li>" + qdjl_afe.get(i)
						+ "&nbsp&nbsp&nbsp&nbsp</li><br>");
			}
			sb.append("<li>" + qdjl_afe.get(qdjl_afe.size() - 1)
					+ "&nbsp&nbsp&nbsp&nbsp</li>]");
			sb.append(connector + "AFE*\r\n");
			sb.append("AFE*" + connector + currentAppName + "\r\n");
		}

		if (fwzl.size() != 0) {
			sb.append(currentAppName + connector + "直连系统明细[");
			for (int i = 0; i <= fwzl.size() - 2; i++) {
				sb.append("<li>" + fwzl.get(i)
						+ "&nbsp&nbsp&nbsp&nbsp</li><br>");
			}
			sb.append("<li>" + fwzl.get(fwzl.size() - 1)
					+ "&nbsp&nbsp&nbsp&nbsp</li>]\r\n");
		}

		if (fwjl_esb.size() != 0) {
			sb.append(currentAppName + connector + "ESB\r\t\n");
			sb.append("ESB" + connector + "ESB系统[");
			for (int i = 0; i <= fwjl_esb.size() - 2; i++) {
				sb.append("<li>" + fwjl_esb.get(i)
						+ "&nbsp&nbsp&nbsp&nbsp</li><br>");
			}
			sb.append("<li>" + fwjl_esb.get(fwjl_esb.size() - 1)
					+ "&nbsp&nbsp&nbsp&nbsp</li>]\r\n");
		}

		if (fwjl_afe.size() != 0) {
			sb.append(currentAppName + connector + "AFE\r\n");
			sb.append("AFE" + connector + "AFE系统[");
			for (int i = 0; i <= fwjl_afe.size() - 2; i++) {
				sb.append("<li>" + fwjl_afe.get(i)
						+ "&nbsp&nbsp&nbsp&nbsp</li><br>");
			}
			sb.append("<li>" + fwjl_afe.get(fwjl_afe.size() - 1)
					+ "&nbsp&nbsp&nbsp&nbsp</li>]\r\n");
		}

		sb.append("classDef red fill:#ff8080;\r\n");
		sb.append("class " + currentAppName + " red\r\n");

		String showCode = sb.toString();
	
		ActionContext.getContext().getSession().put(currentAppIdAndName + "_showCode", showCode);

		LogUtil.info(ShowSysRelImpl.class, showCode);

		return sortList;
	}
}
