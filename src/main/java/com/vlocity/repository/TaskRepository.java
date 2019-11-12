package com.vlocity.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vlocity.domain.Task;

@Repository
@Qualifier(value = "taskRepository")
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByName(String name);

}
