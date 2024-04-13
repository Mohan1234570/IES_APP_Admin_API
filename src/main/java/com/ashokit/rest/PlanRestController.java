package com.ashokit.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.entity.PlanEntity;
import com.ashokit.service.PlanService;

@RestController
public class PlanRestController {
	@Autowired
	private PlanService planservice;
	/*@Autowired
	private PlanRepository planrepo;*/
	
	
	//save user
	@PostMapping(value = "/saveplan",consumes = {"application/json"})
	public ResponseEntity<String> createPlan(@RequestBody PlanEntity user){
		
		String status = planservice.upsert(user);
		
		return new ResponseEntity<>(status,HttpStatus.CREATED); 
		
	}
	@GetMapping(value = "/getplan/{planId}" ,produces = {"application/json"})
	public ResponseEntity<PlanEntity> getPlan(@PathVariable Integer planId){
		
		PlanEntity userEntity = planservice.getPlanById(planId);
		
		return new ResponseEntity<>(userEntity,HttpStatus.OK);
	}
	// get all users
	@GetMapping(value = "/allplans",produces = {"application/json"})
	public ResponseEntity<List<PlanEntity>> getAllPlans(){
		
		List<PlanEntity> status = planservice.getAllPlans();
		
		return new ResponseEntity<>(status,HttpStatus.OK);
		
	}
	
	//update users
	@PutMapping(value = "/planupdate/{planId}")
	public ResponseEntity<String> update(@RequestBody PlanEntity plan){
		
		String plans = planservice.editById(plan);
		
		return new ResponseEntity<>(plans,HttpStatus.OK);
	}
	
	
	//delete the user
	@DeleteMapping(value = "/plandelete/{planId}")
	public ResponseEntity<String> delete(@PathVariable Integer planId){
		
		String deleteById = planservice.disablePlan(planId);
		
		return new ResponseEntity<>(deleteById,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteplan/{planId}")
	public ResponseEntity<String> deleteplan(@PathVariable Integer planId){
		
		String deleteById = planservice.deleteById(planId);
		
		return new ResponseEntity<>(deleteById,HttpStatus.OK);
	}

}
