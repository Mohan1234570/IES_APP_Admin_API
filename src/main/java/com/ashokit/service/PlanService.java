package com.ashokit.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ashokit.entity.PlanEntity;

@Component
public interface PlanService {
	public String upsert(PlanEntity plan);
	public String editById(PlanEntity plan);
	public String disablePlan(Integer planId);
	public PlanEntity getPlanById(Integer planId);
	public List<PlanEntity> getAllPlans();
	public String deleteById(Integer planId);
	

	

}
