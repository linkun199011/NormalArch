package com.arch.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.arch.beans.Project;
import com.arch.beans.TbAdmin;
import com.arch.beans.User;
import com.arch.service.UserManager;
import com.arch.util.LogUtil;
import com.opensymphony.xwork2.ActionContext;

public class UserAction implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String passowrd;
	private UserManager userManager;
	private String loginResp;

	private String name;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the passowrd
	 */
	public String getPassowrd() {
		return passowrd;
	}

	/**
	 * @param passowrd
	 *            the passowrd to set
	 */
	public void setPassowrd(String passowrd) {
		this.passowrd = passowrd;
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}

	/**
	 * @param userManager
	 *            the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @return the loginResp
	 */
	public String getLoginResp() {
		return loginResp;
	}

	/**
	 * @param loginResp
	 *            the loginResp to set
	 */
	public void setLoginResp(String loginResp) {
		this.loginResp = loginResp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 登陆
	 */
	public String login() {

		try {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("sessionId", UUID.randomUUID().toString());
			
			// 将用户名称放到session
			session.put("username", username);
			session.put("name", username);

			LogUtil.info(UserAction.class,"操作：UserAction.login() called……用户登陆");

			// 免登陆展示使用
			if ("testAdmin".equals(username)) {

				LogUtil.info(UserAction.class,"进入演示用户，开始生成演示项目");

				List<Project> projectList = new ArrayList<Project>();
				for (int i = 0; i < 5; i++) {
					Project proj = new Project();
					proj.setProjectName("展示项目" + i + "-增量1");
					proj.setProjectManager("项目经理" + i);
					proj.setBusiManager("业务代表" + i);
					proj.setTechManager("开发代表" + i);
					proj.setTestManager("测试代表" + i);

					LogUtil.debug(UserAction.class,"演示项目：" + proj.toString());

					projectList.add(proj);
				}

				session.put("projectList", projectList);

				for (Project project : projectList) {

					String projectName = project.getProjectName();

					session.put(projectName, project);
				}

			} else {

				User user = userManager.userLogin(username, passowrd);

				if (null != user.getUserInfo()) {

					name = user.getUserInfo().getUserName();

					session.put("name", name);

					listProject(username);
					
				} else {

					loginResp = user.getUserLoginFailureMsg();
					LogUtil.info(UserAction.class,"登陆失败");
					return "loginFail";
				}
			}

			listAdmin();
			LogUtil.info(UserAction.class,"登陆成功");
			return "loginSuccess";

		} catch (Exception e) {
			LogUtil.info(UserAction.class,"登陆失败："+e.getMessage());
			e.printStackTrace();
			loginResp = "请重新登陆！";
			return "loginFail";
		}

	}

	public String init() {
		return "input";
	}

	
	public String logout() {

		// 清除缓存
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.clear();

		return "input";
	}
	

	public String response(){
		
		
		
		return "input";
	}

	public String loginWithToken(){
		
		try{
			
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			session.put("sessionId", UUID.randomUUID().toString());
			
			ActionContext context = ActionContext.getContext();
			Map params = context.getParameters();
			// 获取token关键字
			String token = ((String[]) params.get("ticket"))[0];
			
			System.out.println(token);

			User user = userManager.userLoginWithToken(token);
			
			if (null != user.getUserInfo()) {
	
				username = user.getUserInfo().getLoginName();
				name = user.getUserInfo().getUserName();
				
				session.put("username", username);
				session.put("name", name);
	
				listProject(username);
				listAdmin();
				
			} else {
	
				loginResp = user.getUserLoginFailureMsg();
				LogUtil.info(UserAction.class,"登陆失败");
				return "loginFail";
			}
		}catch(Exception e){
			LogUtil.info(UserAction.class,"登陆失败："+e.getMessage());
			e.printStackTrace();
			loginResp = "请重新登陆！";
			return "loginFail";
		}
		
		return "loginSuccess";
	}
	
	private void listAdmin(){
		
		List<TbAdmin> adminList = userManager.listTbAdmin();
		Map<String, String> adminMap = new HashMap<String, String>();
		for (TbAdmin tbAdmin : adminList) {
			String id = tbAdmin.getId();
			adminMap.put(id, id);
		}

		ActionContext.getContext().getSession().put("adminMap", adminMap);
		
	}
	
	private void listProject(String username){
		
		Map<String, Object> session = ActionContext.getContext()
				.getSession();
		
		List<Project> projectList = userManager
				.getProjectListByUsername(username);

		// 为每个人都开放一个基线修改项目
		Project proj = new Project();
		proj.setProjectName("00-基线修改项目");
		proj.setTechManager("余永隆");
		proj.setProjectManager("余永隆");
		
		if(null == projectList){
			projectList = new ArrayList<Project>();
		}
		
		projectList.add(proj);
		
		session.put("projectList", projectList);

		for (Project project : projectList) {

			String projectName = project.getProjectName();

			session.put(projectName, project);
			LogUtil.debug(UserAction.class,"关联项目：" + project.toString());
		}
	}
}
