package com.vlocity.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vlocity.domain.Task;
import com.vlocity.dto.TaskDto;
import com.vlocity.repository.TaskRepository;
import com.vlocity.response.ApiResponse;
import com.vlocity.service.TaskService;
import com.vlocity.validator.TaskDtoValidator;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/tasks")
public class TaskRestController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskDtoValidator taskDtoValidator;

    @InitBinder("taskDto")
    public void initMerchantOnlyBinder(WebDataBinder binder) {
        binder.addValidators(taskDtoValidator);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<Task>> list() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Task list fetched successfully.", taskService.findAll());
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<Task>> paginated(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort,
            @RequestParam("sortBy") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        return new ApiResponse<>(HttpStatus.OK.value(), "Task list fetched successfully.", taskRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Task> getOne(@PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Task fetched successfully.", taskService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Task> save(@Valid @RequestBody TaskDto taskDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Task saved successfully.", taskService.save(taskDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Task> update(@Valid @RequestBody TaskDto taskDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Task updated successfully.", taskService.update(taskDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "Task fetched successfully.", taskService.delete(id));
    }

}
