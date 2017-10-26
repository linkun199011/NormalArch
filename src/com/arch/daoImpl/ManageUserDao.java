package com.arch.daoImpl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.arch.beans.TbAdmin;
import com.arch.dao.IManageUserDao;

public class ManageUserDao extends HibernateDaoSupport implements IManageUserDao{


	@SuppressWarnings("unchecked")
	@Override
	public List<TbAdmin> listTbAdmin() {
		return this.getHibernateTemplate().find("from TbAdmin s");
	}
	
}
