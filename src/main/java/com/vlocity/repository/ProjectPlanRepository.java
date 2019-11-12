package com.vlocity.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vlocity.domain.ProjectPlan;

@Repository
@Qualifier(value = "projectPlanRepository")
public interface ProjectPlanRepository extends JpaRepository<ProjectPlan, Long> {

    ProjectPlan findByName(String name);
}
