package com.vlocity.service.impl;

import com.vlocity.domain.Task;
import com.vlocity.dto.ProjectPlanDto;
import com.vlocity.dto.TaskDto;
import com.vlocity.repository.TaskRepository;
import com.vlocity.service.ProjectPlanService;
import com.vlocity.service.TaskService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Service(value = "taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectPlanService projectPlanService;

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Task> findAll() {
        List<Task> list = new ArrayList<>();
        taskRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Boolean delete(Long id) {
        Task existingTask = findById(id);
        if (existingTask == null)
            return false;

        entityManager.createNativeQuery("DELETE FROM Task_Dependencies WHERE task_id=" + id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM Task_Dependencies WHERE dependency_id=" + id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM Task WHERE id=" + id).executeUpdate();

        Task deletedTask = findById(id);
        if (deletedTask != null)
            return false;

        return true;
    }

    @Override
    public Task findByName(String name) {
        return taskRepository.findByName(name);
    }

    @Override
    public Task findById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.isPresent() ? optionalTask.get() : null;
    }

    @Override
    public Task update(TaskDto taskDto) {
        Task existingTask = findById(taskDto.getId());
        Task saved = null;
        if (existingTask != null) {
            BeanUtils.copyProperties(taskDto, existingTask);

            ProjectPlanDto projectPlanDto = taskDto.getProjectPlan();
            if (projectPlanDto != null) {
                existingTask.setProjectPlan(projectPlanService.findById(projectPlanDto.getId()));
            } else {
                existingTask.setProjectPlan(null);
            }

            Set<TaskDto> taskDepDtos = taskDto.getDependencies();
            if (taskDepDtos != null && !taskDepDtos.isEmpty()) {
                Set<Task> newTaskDeps = new HashSet<Task>();
                for (TaskDto taskDepDto : taskDepDtos) {
                    newTaskDeps.add(findById(taskDepDto.getId()));
                }
                existingTask.setDependencies(newTaskDeps);
            }

            saved = taskRepository.save(existingTask);
        }

        return saved;
    }

    @Override
    public Task save(TaskDto taskDto) {
        Task newTask = new Task();
        newTask.setName(taskDto.getName());
        newTask.setDescription(taskDto.getDescription());
        newTask.setStartDate(taskDto.getStartDate());
        newTask.setEndDate(taskDto.getEndDate());
        newTask.setTaskStatus(taskDto.getTaskStatus());
        newTask.setDeleted(taskDto.isDeleted());

        ProjectPlanDto projectPlanDto = taskDto.getProjectPlan();
        if (projectPlanDto != null) {
            newTask.setProjectPlan(projectPlanService.findById(projectPlanDto.getId()));
        }

        Set<TaskDto> taskDepDtos = taskDto.getDependencies();
        if (taskDepDtos != null && !taskDepDtos.isEmpty()) {
            Set<Task> newTaskDeps = new HashSet<Task>();
            for (TaskDto taskDepDto : taskDepDtos) {
                newTaskDeps.add(findById(taskDepDto.getId()));
            }
            newTask.setDependencies(newTaskDeps);
        }

        return taskRepository.save(newTask);
    }
}
