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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vlocity.domain.ProjectPlan;
import com.vlocity.domain.User;
import com.vlocity.dto.ProjectPlanDto;
import com.vlocity.repository.ProjectPlanRepository;
import com.vlocity.response.ApiResponse;
import com.vlocity.service.ProjectPlanService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/projectPlans")
public class ProjectPlanRestController {

    @Autowired
    private ProjectPlanService projectPlanService;

    @Autowired
    private ProjectPlanRepository projectPlanRepository;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<User>> list() {
        return new ApiResponse<>(HttpStatus.OK.value(), "ProjectPlan list fetched successfully.", projectPlanService.findAll());
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<ProjectPlan>> paginated(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort,
            @RequestParam("sortBy") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        return new ApiResponse<>(HttpStatus.OK.value(), "ProjectPlan list fetched successfully.", projectPlanRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProjectPlan> getOne(@PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "ProjectPlan fetched successfully.", projectPlanService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProjectPlan> save(@Valid @RequestBody ProjectPlanDto projectPlanDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "ProjectPlan saved successfully.", projectPlanService.save(projectPlanDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProjectPlan> update(@Valid @RequestBody ProjectPlanDto projectPlanDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "ProjectPlan updated successfully.", projectPlanService.update(projectPlanDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "ProjectPlan fetched successfully.", projectPlanService.delete(id));
    }

}
