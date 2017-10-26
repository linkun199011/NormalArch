package com.arch.serviceImpl;

import java.util.List;

import com.arch.beans.Project;
import com.arch.beans.TbAdmin;
import com.arch.beans.User;
import com.arch.dao.IManageUserDao;
import com.arch.service.UserManager;
import com.arch.util.LogUtil;
import com.arch.util.SSOUtil;
import com.arch.util.WebServiceUtil;

public class UserManagerImpl implements UserManager {

	private IManageUserDao manageUserDao;

	/**
	 * @return the manageUserDao
	 */
	public IManageUserDao getManageUserDao() {
		return manageUserDao;
	}

	/**
	 * @param manageUserDao
	 *            the manageUserDao to set
	 */
	public void setManageUserDao(IManageUserDao manageUserDao) {
		this.manageUserDao = manageUserDao;
	}

	public User userLogin(String username, String pwd) {

		LogUtil.info(UserManagerImpl.class, "userLogin called...username:"
				+ username + ",pwd: ******");

		try {

			User u = SSOUtil.getInstance().checkUser(username, pwd);

			if (null == u) {
				u = new User();
				u.setUserLoginFailureMsg("登陆异常，请重新登陆");
				LogUtil.info(UserManagerImpl.class,
						"userLogin complete...登录异常，User not found");

			} else {
				LogUtil.info(UserManagerImpl.class,
						"userLogin complete...登录成功，User:" + u.toString());
			}

			return u;
		} catch (Exception e) {
			e.printStackTrace();
			User u = new User();
			u.setUserLoginFailureMsg("统一用户连接异常，请与管理员联系");
			LogUtil.info(UserManagerImpl.class,
					"userLogin complete...统一用户连接异常，请与管理员联系，exception:" + e);

			return u;
		}

	}

	@Override
	public List<Project> getProjectListByUsername(String username) {

		try {
			return WebServiceUtil.getInstance().getProjectListByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<TbAdmin> listTbAdmin() {
		LogUtil.info(UserManagerImpl.class, "listTbAdmin called...");

		List<TbAdmin> lst = manageUserDao.listTbAdmin();

		LogUtil.info(UserManagerImpl.class, "listTbAdmin complete...result:"
				+ lst);

		return lst;
	}

	@Override
	public User userLoginWithToken(String token) {
		LogUtil.info(UserManagerImpl.class, "userLoginWithToken called...token:"
				+ token);
		try {

			User u = SSOUtil.getInstance().checkUserWithToken(token);

			if (null == u) {
				u = new User();
				u.setUserLoginFailureMsg("登陆异常，请重新登陆");
				LogUtil.info(UserManagerImpl.class,
						"userLogin complete...登录异常，User not found");

			} else {
				LogUtil.info(UserManagerImpl.class,
						"userLogin complete...登录成功，User:" + u.toString());
			}

			return u;
		} catch (Exception e) {
			e.printStackTrace();
			User u = new User();
			u.setUserLoginFailureMsg("统一用户连接异常，请与管理员联系");
			LogUtil.info(UserManagerImpl.class,
					"userLogin complete...统一用户连接异常，请与管理员联系，exception:" + e);

			return u;
		}
		
	}

}
