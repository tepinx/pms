package com.vlocity.service.impl;

import com.vlocity.domain.ProjectPlan;
import com.vlocity.dto.ProjectPlanDto;
import com.vlocity.repository.ProjectPlanRepository;
import com.vlocity.service.ProjectPlanService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Service(value = "projectPlanService")
public class ProjectPlanServiceImpl implements ProjectPlanService {

    @Autowired
    private ProjectPlanRepository projectPlanRepository;

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProjectPlan> findAll() {
        List<ProjectPlan> list = new ArrayList<>();
        projectPlanRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Boolean delete(Long id) {
        ProjectPlan existingProjectPlan = findById(id);
        if (existingProjectPlan == null)
            return false;

        entityManager.createNativeQuery("DELETE FROM ProjectPlan WHERE id=" + id).executeUpdate();

        ProjectPlan deletedProjectPlan = findById(id);
        if (deletedProjectPlan != null)
            return false;

        return true;
    }

    @Override
    public ProjectPlan findByName(String name) {
        return projectPlanRepository.findByName(name);
    }

    @Override
    public ProjectPlan findById(Long id) {
        Optional<ProjectPlan> optionalProjectPlan = projectPlanRepository.findById(id);
        return optionalProjectPlan.isPresent() ? optionalProjectPlan.get() : null;
    }

    @Override
    public ProjectPlan update(ProjectPlanDto projectPlanDto) {
        ProjectPlan existingProjectPlan = findById(projectPlanDto.getId());
        ProjectPlan saved = null;
        if (existingProjectPlan != null) {
            BeanUtils.copyProperties(projectPlanDto, existingProjectPlan);
            saved = projectPlanRepository.save(existingProjectPlan);
        }

        return saved;
    }

    @Override
    public ProjectPlan save(ProjectPlanDto projectPlanDto) {
        ProjectPlan newProjectPlan = new ProjectPlan();
        newProjectPlan.setName(projectPlanDto.getName());
        newProjectPlan.setDescription(projectPlanDto.getDescription());
        newProjectPlan.setDeleted(projectPlanDto.isDeleted());

        return projectPlanRepository.save(newProjectPlan);
    }
}
