package com.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.PlanEntity;

public interface PlanRepository extends JpaRepository<PlanEntity, Integer>{

}
