package com.ashokit.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.appconstants.AppConstant;
import com.ashokit.entity.PlanEntity;
import com.ashokit.repo.PlanRepository;
import com.ashokit.service.PlanService;

@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanRepository planrepo;

	@Override
	public String upsert(PlanEntity plan) {
		plan.setPlanStatus(AppConstant.ACTIVE_MSG);
		planrepo.save(plan);

		return AppConstant.SUCCESS_MSG;
	}

	@Override
	public PlanEntity getPlanById(Integer planId) {
		Optional<PlanEntity> findById = planrepo.findById(planId);
		if(findById.isPresent()) {
			PlanEntity user = findById.get();
			return user;
		}
		return null;
	}
	
	@Override
	public List<PlanEntity> getAllPlans() {
		List<PlanEntity> findAll = planrepo.findAll();
		return findAll;
	}

	@Override
	public String deleteById(Integer planId) {
		planrepo.deleteById(planId);
		return AppConstant.DELETE_MSG;
	}
	
	
	@Override
	public String disablePlan(Integer planId) {
		PlanEntity user = null;
		Optional<PlanEntity> findById = planrepo.findById(planId);
		if(findById.isPresent()) {
			user = findById.get();
			user.setPlanStatus(AppConstant.INACTIVE_MSG);
		}
		planrepo.save(user);
		return AppConstant.SUCCESS_MSG;
	}

	@Override
	public String editById(PlanEntity plan) {

		if(plan.getPlanId()!=null) {
			PlanEntity user = new  PlanEntity();
			BeanUtils.copyProperties(plan, user);
			user.setPlanStatus(AppConstant.ACTIVE_MSG);
			planrepo.save(user);
			
		}
		return AppConstant.SUCCESS_MSG;
	}
}