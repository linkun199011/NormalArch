package com.arch.dao;

import java.util.List;

import com.arch.beans.RelatedAppInfoQuery;

public interface IShowSysRelDao {

	public List<Object> listRelatedAppInfoResult(RelatedAppInfoQuery qry);
}
