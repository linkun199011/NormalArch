package com.arch.service;

import java.util.List;

import com.arch.beans.Project;
import com.arch.beans.TbAdmin;
import com.arch.beans.User;

public interface UserManager {

	public User userLogin(String username, String pwd);
	
	public User userLoginWithToken(String token);
	
	public List<Project> getProjectListByUsername(String username);
	
	public List<TbAdmin> listTbAdmin();
}
