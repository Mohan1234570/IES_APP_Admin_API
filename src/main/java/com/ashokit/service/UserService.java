package com.ashokit.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.binding.UserDashboardForm;
import com.ashokit.binding.UserForm;
import com.ashokit.entity.UserEntity;


@Component
public interface UserService {
	public String createAccount(UserForm form);
	public UserEntity getById(Integer userId);
	public List<UserEntity> getAllUsers();
	public String deleteById(Integer userId);
	public String disableUser(Integer userId);
	public String login(LoginForm loginform)throws Exception;
	public String updateAccount(UserForm form) ;
	public String unlockAccount(UnlockForm form)throws Exception; 
	public String forgotpwd(String email)throws Exception; 
	public UserDashboardForm getDashboardData();
	

}
