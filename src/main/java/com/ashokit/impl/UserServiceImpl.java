package com.ashokit.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.appconstants.AppConstant;
import com.ashokit.binding.LoginForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.binding.UserDashboardForm;
import com.ashokit.binding.UserForm;
import com.ashokit.entity.AccountType;
import com.ashokit.entity.UserEntity;
import com.ashokit.repo.PlanRepository;
import com.ashokit.repo.UserRepository;
import com.ashokit.service.UserService;
import com.ashokit.utils.EmailUtils;
import com.ashokit.utils.PasswordEncrypter;
import com.ashokit.utils.UserPwdUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private HttpSession session;
	@Autowired
	private EmailUtils emailutils;
	@Autowired
	private PlanRepository planrepo;
	
	


	@Override
	public String createAccount(UserForm form) {
		
		 UserEntity users = repo.findByEmail(form.getEmail());
		if(users!= null ){
			return "failed";
		}
	     UserEntity userEntity = new  UserEntity();
		BeanUtils.copyProperties(form, userEntity);
		String tempPwd = UserPwdUtils.generateRandomPwd();
		
		userEntity.setPassword(tempPwd);
		userEntity.setUserStatus("Locked");
		
		AccountType entity = new AccountType();
		entity.setRoleId(2);
		userEntity.setRole(entity);
	
		userEntity.setUserStatus(AppConstant.ACTIVE_MSG);
		repo.save(userEntity);
		String to = form.getEmail();
		String subject = "unlock your account";
		StringBuffer body = new StringBuffer("");
		body.append("<h1>Hey, Ravali</h1>");
		body.append("<h2>Use below temporary password to Unlock your accout</h2>");
		body.append("Temporary password : "+tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" +to +"\"> Click here to unlock your Acount></a>");

		emailutils.sendEmail(to,subject,body.toString());

		return AppConstant.SUCCESS_MSG;
	}
	
	
	@Override
	public String unlockAccount(UnlockForm form) throws Exception {
		UserEntity entity = repo.findByEmail(form.getEmail());
		
		if(entity==null) {
			return "failed";
		}
		if(entity.getPassword().equalsIgnoreCase(form.getTemppwd())) {
			String encryptPassword = PasswordEncrypter.encrypt(form.getPassword());
			entity.setPassword(encryptPassword);
			entity.setUserStatus(AppConstant.ACTIVE_MSG);
			repo.save(entity);
			return AppConstant.SUCCESS_MSG;
		}
			return null;
		}

	@Override
	public UserEntity getById(Integer userId) {
		Optional<UserEntity> findById = repo.findById(userId);
		if(findById.isPresent()) {
			UserEntity user = findById.get();
			return user;
		}
		return null;
	}

	@Override
	public List<UserEntity> getAllUsers() {
		List<UserEntity> findAll = repo.findAll();
		return findAll;
	}

	@Override
	public String deleteById(Integer userId) {
		if(repo.existsById(userId)) {
			repo.deleteById(userId);
			return AppConstant.DELETE_MSG;
		}else {
			return "no records found";
		}
	}

	
	@Override
	public String disableUser(Integer userId) {
		UserEntity user = null;
		Optional<UserEntity> findById = repo.findById(userId);
		if(findById.isPresent()) {
			user = findById.get();
			user.setUserStatus(AppConstant.INACTIVE_MSG);
		}
		repo.save(user);
		return AppConstant.SUCCESS_MSG;
	}
	

	@Override
	public String login(LoginForm loginform) throws Exception {
		UserEntity entity = repo.findByEmailAndPassword(loginform.getEmail(),loginform.getPassword());
		if(entity ==null) {
			return AppConstant.INVALID_MSG;
		}
		session.setAttribute("userId",entity.getUserId());
		return AppConstant.SUCCESS_MSG;

	}

	
	
	@Override
	public String updateAccount(UserForm form) {
		if(form.getEmail()!=null) {
			UserEntity user = new  UserEntity();
			BeanUtils.copyProperties(form, user);
			user.setUserStatus(AppConstant.ACTIVE_MSG);
			repo.save(user);
			
		}
		return AppConstant.SUCCESS_MSG;
	}


	@Override
	public String forgotpwd(String email) throws Exception {
		
		UserEntity entity = repo.findByEmail(email);
		
		if(entity == null){
			return "failed";
		} 
		
		String  subject = "recover password";
		String dcryptPassword = PasswordEncrypter.decrypt(entity.getPassword());
		String body = "your password:: "+ dcryptPassword;
		emailutils.sendEmail(email,subject,body);
		return AppConstant.SUCCESS_MSG;
	}


	@Override
	public UserDashboardForm getDashboardData() {
		UserDashboardForm response = new UserDashboardForm();

		long plan = planrepo.count();
		long plan1 = planrepo.count();
		long plan2	=planrepo.count();
		long plan3	=planrepo.count();
		
		
		response.setNoOfPlans(plan);
		response.setCitizensApproved(plan1);
		response.setCitizensDenied(plan2);
		response.setBenefitAmount(plan3);

		return response;
	}

}
