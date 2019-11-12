package com.vlocity.validator;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.vlocity.domain.Task;
import com.vlocity.dto.TaskDto;
import com.vlocity.enumeration.TaskStatus;
import com.vlocity.repository.TaskRepository;
import com.vlocity.service.TaskService;
import com.vlocity.util.RegexPatternHandler;

@Component
public class TaskDtoValidator extends AbstractValidator implements Validator {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RegexPatternHandler regexPatternHandler;

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TaskDto taskDto = (TaskDto) target;

        Set<TaskDto> taskDepDtos = taskDto.getDependencies();
        if (taskDepDtos != null && !taskDepDtos.isEmpty()) {
            for (TaskDto taskDepDto : taskDepDtos) {
                Task dependencyTask = taskService.findById(taskDepDto.getId());
                if (taskDto.getTaskStatus() == TaskStatus.IN_PROGRESS && dependencyTask != null
                        && (dependencyTask.getTaskStatus() == TaskStatus.OPEN || dependencyTask.getTaskStatus() == TaskStatus.IN_PROGRESS)) {
                    StringBuilder sbmsg = new StringBuilder();
                    String errorMsg = sbmsg.append(dependencyTask.getName()).append(" ").append("is not yet completed").toString();
                    errors.reject("ERROR_DEPENDENCY_TASK_NOT_COMPLETED", errorMsg);
                }
                if (taskDto.getTaskStatus() == TaskStatus.COMPLETED && dependencyTask != null
                        && (dependencyTask.getTaskStatus() == TaskStatus.OPEN || dependencyTask.getTaskStatus() == TaskStatus.IN_PROGRESS)) {
                    StringBuilder sbmsg = new StringBuilder();
                    String errorMsg = sbmsg.append(dependencyTask.getName()).append(" ").append("is not yet completed").toString();
                    errors.reject("ERROR_DEPENDENCY_TASK_NOT_COMPLETED", errorMsg);
                }
            }
        }
    }

}
