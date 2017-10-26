package com.arch.serviceImpl;

import java.util.HashMap;

import com.arch.beans.UpAppRelQry;
import com.arch.dao.IShowAppRelDao;
import com.arch.service.ShowAppRel;
import com.arch.util.LogUtil;

public class ShowAppRelImpl implements ShowAppRel {

	private IShowAppRelDao showAppRelDao;

	/**
	 * @return the showAppRelDao
	 */
	public IShowAppRelDao getShowAppRelDao() {
		return showAppRelDao;
	}

	/**
	 * @param showAppRelDao the showAppRelDao to set
	 */
	public void setShowAppRelDao(IShowAppRelDao showAppRelDao) {
		this.showAppRelDao = showAppRelDao;
	}

	@Override
	public String getDownAppRelInfo(Integer serviceId, String operationType) {
		LogUtil.info(ShowAppRelImpl.class, "getDownAppRelInfo called...serviceId:"+serviceId+",operationType:"+operationType);
		
		String showCode = showAppRelDao.getDownAppRelInfo(serviceId, operationType);
		
		LogUtil.info(ShowAppRelImpl.class, "getDownAppRelInfo complete...");
		
		return showCode;
	}

	@Override
	public HashMap<String,Object> listUpAppRelResult(UpAppRelQry qry) {
		LogUtil.info(ShowAppRelImpl.class, "listUpAppRelResult called...UpAppRelQry:"+qry.toString());
		
		HashMap<String,Object> upAppRelResultMap = showAppRelDao.listUpAppRelResult(qry);
		
		LogUtil.info(ShowAppRelImpl.class, "listUpAppRelResult complete...");
	
		return upAppRelResultMap;
	}

	@Override
	public String getUpAppRelInfo(Integer checkNoId, String seqNoId, String operationType) {
		LogUtil.info(ShowAppRelImpl.class, "getUpAppRelInfo called...checkNoId:"+checkNoId+",seqNoId:"+seqNoId+",operationType:"+operationType);
		
		String showCode = showAppRelDao.getUpAppRelInfo(checkNoId, seqNoId,operationType);
		
		LogUtil.info(ShowAppRelImpl.class, "getUpAppRelInfo complete...");

		return showCode;
	}

	
}
