package com.vlocity.controller;

import com.vlocity.domain.User;
import com.vlocity.dto.UserDto;
import com.vlocity.repository.UserRepository;
import com.vlocity.response.ApiResponse;
import com.vlocity.service.UserService;
import com.vlocity.validator.UserDtoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDtoValidator userDtoValidator;

    @InitBinder("userDto")
    public void initMerchantOnlyBinder(WebDataBinder binder) {
        binder.addValidators(userDtoValidator);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<User>> list() {
        return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.", userService.findAll());
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<User>> paginated(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort,
            @RequestParam("sortBy") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        return new ApiResponse<>(HttpStatus.OK.value(), "User list fetched successfully.", userRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<User> getOne(@PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", userService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<User> save(@Valid @RequestBody UserDto userDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.", userService.save(userDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<User> update(@Valid @RequestBody UserDto userDto) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.", userService.update(userDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.", userService.delete(id));
    }

}
