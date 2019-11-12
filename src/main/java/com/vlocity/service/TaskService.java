package com.vlocity.service;

import java.util.List;

import com.vlocity.domain.Task;
import com.vlocity.dto.TaskDto;

public interface TaskService {

    Task save(TaskDto taskDto);

    List<Task> findAll();

    Boolean delete(Long id);

    Task findByName(String name);

    Task findById(Long id);

    Task update(TaskDto taskDto);
}
