package com.ashokit.rest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.binding.LoginForm;
import com.ashokit.binding.UnlockForm;
import com.ashokit.binding.UserDashboardForm;
import com.ashokit.binding.UserForm;
import com.ashokit.entity.UserEntity;
import com.ashokit.service.UserService;

@RestController
public class UserRestController {
	@Autowired
	private UserService service;
	
	
	@PostMapping(value = "/save",consumes = {"application/json"})
	public ResponseEntity<String> createUser(@RequestBody UserForm form){

		String status = service.createAccount(form);

		return new ResponseEntity<>(status,HttpStatus.CREATED); 

	}
	
	
	@GetMapping(value = "/login" ,produces = {"application/json"})
	public ResponseEntity<String> getLogin(@RequestBody LoginForm loginform) throws Exception{

		String userEntity = service.login(loginform);

		return new ResponseEntity<>(userEntity,HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/getuser/{id}" ,produces = {"application/json"})
	public ResponseEntity<UserEntity> getUser(@PathVariable Integer id){

		UserEntity userEntity = service.getById(id);

		return new ResponseEntity<>(userEntity,HttpStatus.OK);
	}
	@GetMapping(value = "/allusers",produces = {"application/json"})
	public ResponseEntity<List<UserEntity>> getAllUsers(){

		List<UserEntity> status = service.getAllUsers();
		

		return new ResponseEntity<>(status,HttpStatus.OK);
		/*status.stream().filter(e->e.getUserName().getRoleName().equals("user"))
		.collect(Collectors.toList());
*/
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> update(@RequestBody UserForm form){

		String status = service.updateAccount(form);

		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Integer id){

		String status = service.disableUser(id);
		
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	@RequestMapping(value="/unlock", method=RequestMethod.POST)
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockForm form) throws Exception{
		
		String status = service.unlockAccount(form);
		
		
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	@GetMapping(value="/forgot/{email}")
	public ResponseEntity<String> forgotpwd(@PathVariable String email) throws Exception{
		
		String status = service.forgotpwd(email);
		
		
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	@GetMapping(value="/dashboard")
	public ResponseEntity<UserDashboardForm> dashboard(){
		
		UserDashboardForm status = service.getDashboardData();
		
		
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	
	
	

}
