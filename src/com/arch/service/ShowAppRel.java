/**
 * 
 */
package com.arch.service;

import java.util.HashMap;
import com.arch.beans.UpAppRelQry;


/**
 * @author xiefangkai
 *
 */
public interface ShowAppRel {

	public String getDownAppRelInfo(Integer serviceId, String operationType);

	public HashMap<String, Object> listUpAppRelResult(UpAppRelQry qry);
	
	public String getUpAppRelInfo(Integer checkNoId, String seqNoId, String operationType);

}
