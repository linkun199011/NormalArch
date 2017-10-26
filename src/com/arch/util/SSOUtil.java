package com.arch.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.arch.beans.User;
import com.rongji.dfish.framework.handler.head.HeadRequest;
import com.rongji.dfish.framework.handler.json.PubUmsRecordType.CheckUserPwdResponse;
import com.rongji.dfish.framework.handler.json.PubUmsRecordType.FindPubUserByFlagResponse;
import com.rongji.dfish.framework.handler.json.client.PubUmsRecordClient;
import com.rongji.dfish.framework.handler.xml.PubUserInfo;

import UnionTech.JavaEsscAPI.UnionAPI;

public class SSOUtil {
	
	private static SSOUtil tool;
	private Properties prop = new Properties();
	private UnionAPI api;
	private PubUmsRecordClient client;
	
	private static final String servConf = "server-config.properties";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 实例化
	 * @throws Exception
	 */
	private SSOUtil() throws Exception{
		
		URL url = SSOUtil.class.getClassLoader().getResource(servConf);
		String path = url.getPath();
		
		InputStream in = new BufferedInputStream(new FileInputStream(path));
		prop.load(in);

		LogUtil.info(SSOUtil.class, "加载prop,"+prop);
		initMg();
		initTyyh();
	}
	
	public static SSOUtil getInstance() throws Exception{


		LogUtil.info(SSOUtil.class, "SSOUtil.gentInstance() called……");
		
		if(tool == null){
			return new SSOUtil();
		}
		return tool;
	}

	
	/**
	 * 初始化密管API
	 */
	private void initMg(){
		
		String mgptIp = prop.getProperty("mgpt.ip");
		int mgptPort = Integer.parseInt(prop.getProperty("mgpt.port"));
		int expireTime = Integer.parseInt(prop.getProperty("mgpt.expireTime"));
		String appName = prop.getProperty("mgpt.appName");
		
		try{
			api = new UnionAPI(mgptIp, mgptPort, expireTime, appName);	
			LogUtil.info(SSOUtil.class, "密管初始化成功");
		}catch(Exception e){
			LogUtil.info(SSOUtil.class, "密管初始化失败"+e.getMessage());
		}
	}
	
	/**
	 * 加密
	 * @param pin 明文
	 * @return 密文
	 * @throws Exception
	 */
	private String encrypt(String pin) throws Exception{
		
		if(api == null){
			initMg();
		}
		
		String key = prop.getProperty("mgpt.key");		
		
		if(null == pin){
			return null;
		}
		
		String pinBlock = api.UnionEncryptData (key, pin.length(), pin, "NULL", "NULL");
		
		return pinBlock;
		
	}
	
	/**
	 * 初始化统一用户
	 */
	private void initTyyh(){

		String url = prop.getProperty("tyyh.url");
		client = new PubUmsRecordClient(url);
		LogUtil.info(SSOUtil.class, "统一用户初始化成功");
	}
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码密文
	 * @return “0” 成功
	 * @throws Exception
	 */
	public User checkUser(String username, String password) throws Exception{

		LogUtil.info(SSOUtil.class, "SSOUtil.checkUser called...");
		User user = new User();
		String pinBlock = encrypt(password);
		
		if(null == pinBlock){
			return null;
		}
				
		String sysId = prop.getProperty("tyyh.sysId");
		
		HeadRequest reqHead = new HeadRequest ();
		reqHead.setFileFlag("0");
		reqHead.setOrgSysId(sysId);
		reqHead.setOrgSysSeqNo("001213155");
		reqHead.setServiceCode("000001");
		reqHead.setTranDate(sdf.format(new Date()).toString());
		CheckUserPwdResponse resp = client.checkUserPwd(reqHead, username, pinBlock, "1", sysId);
		
		if("0".equals(resp.getCheck().getStatus())){
			
			PubUserInfo pui = resp.getCheck().getUser();
			user.setUserInfo(pui);
			
			LogUtil.info(SSOUtil.class, "用户登陆成功："+user);
			
			// 验证口令
			reqHead.setFileFlag("0");
			reqHead.setOrgSysId(sysId);
			reqHead.setOrgSysSeqNo("001213155");
			reqHead.setServiceCode("000007");
			reqHead.setTranDate(sdf.format(new Date()).toString());
			
		}else{
			String respMsg = resp.getCheck().getInfo();
			user.setUserLoginFailureMsg(respMsg);
			LogUtil.error(SSOUtil.class, "用户登陆失败："+respMsg);
		}
		
		return user;
		
	}	
	
	public String getCurrentTime(){
		return sdf.format(new Date()).toString();
	}
	
	public User checkUserWithToken(String token) throws Exception{
		LogUtil.info(SSOUtil.class, "SSOUtil.checkUserWithToken called... token:"+token);
		String sysId = prop.getProperty("tyyh.sysId");
		User user = new User();
		
		HeadRequest reqHead = new HeadRequest ();
		reqHead.setFileFlag("0");
		reqHead.setOrgSysId(sysId);
		reqHead.setOrgSysSeqNo("001213155");
		reqHead.setServiceCode("000002");
		reqHead.setTranDate(sdf.format(new Date()).toString());
		FindPubUserByFlagResponse resp = client.findPubUserByFlag(reqHead, token, "3", sysId);
		
		if("000000".equals(resp.getHead().getRetCode())){
			
			PubUserInfo pui = resp.getUser();
			user.setUserInfo(pui);
			LogUtil.info(SSOUtil.class, "用户登陆成功："+user);
			
		}else{
			String respMsg = resp.getHead().getErrorMsg();
			user.setUserLoginFailureMsg(respMsg);
			LogUtil.error(SSOUtil.class, "用户登陆失败："+respMsg);
		}
		
		return user;
	}
	

	public static void main(String[] args) throws Exception {
		
		String username= "yuyonglong";
		String pwd = "ab654321";
		
		User user = SSOUtil.getInstance().checkUser(username, pwd);
		
		System.out.println(user.getUserInfo()+";"+user.getUserLoginFailureMsg());
		
	}

}
