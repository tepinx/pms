package com.vlocity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.vlocity.enumeration.TaskStatus;

public class TaskDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4766695603885462845L;

    private Long id;

    private String name;

    private String description;

    private ProjectPlanDto projectPlan;

    private Date startDate;

    private Date endDate;

    private Set<TaskDto> dependencies;

    private TaskStatus taskStatus;

    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectPlanDto getProjectPlan() {
        return projectPlan;
    }

    public void setProjectPlan(ProjectPlanDto projectPlan) {
        this.projectPlan = projectPlan;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<TaskDto> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<TaskDto> dependencies) {
        this.dependencies = dependencies;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
