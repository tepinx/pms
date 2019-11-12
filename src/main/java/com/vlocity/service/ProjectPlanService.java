package com.vlocity.service;

import java.util.List;

import com.vlocity.domain.ProjectPlan;
import com.vlocity.dto.ProjectPlanDto;

public interface ProjectPlanService {

    ProjectPlan save(ProjectPlanDto projectPlanDto);

    List<ProjectPlan> findAll();

    Boolean delete(Long id);

    ProjectPlan findByName(String name);

    ProjectPlan findById(Long id);

    ProjectPlan update(ProjectPlanDto projectPlanDto);
}
