package com.arch.service;

import java.util.List;

import com.arch.beans.RelatedAppInfoQuery;

public interface ShowSysRel {

	public List<Object> listRelatedAppInfoResult(RelatedAppInfoQuery qry);
}
