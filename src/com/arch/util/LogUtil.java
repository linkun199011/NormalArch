package com.arch.util;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;

public class LogUtil {

	private static String sessionId = "unknown Id";
	private static String user = "unknown User";
	
	private static Log getLogger(Class<?> z){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		if(session.containsKey("sessionId")){
			sessionId = (String) session.get("sessionId");
		}
		
		if(session.containsKey("username")){
			user = (String) session.get("name");
		}
		
		return LogFactory.getLog(z);
	}
	
	public static void error(Class<?> c, String message) {
		getLogger(c).error(sessionId+" - "+user+" - "+message);
	}
	
	public static void info(Class<?> c, String message) {
		getLogger(c).info(sessionId+" - "+user+" - "+message);
	}
	
	public static void warn(Class<?> c, String message) {
		getLogger(c).warn(sessionId+" - "+user+" - "+message);
	}
	
	public static void debug(Class<?> c, String message) {
		getLogger(c).debug(sessionId+" - "+user+" - "+message);
	}
	
	public static void fatal(Class<?> c, String message) {
		getLogger(c).fatal(sessionId+" - "+user+" - "+message);
	}
	
}
