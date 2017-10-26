package com.arch.test;


import org.apache.axis2.AxisFault;

import com.arch.util.LogUtil;
/**
 *@Function: 访问本地发布
 */
public class Test {
	
	public Test(){
		LogUtil.error(Test.class, "testlog");
		testService();
	}
	
	public void testService(){
		
		
		
	}
	
	public static void main(String[] args) throws AxisFault {
	   
		new Test();
		
	}
}
