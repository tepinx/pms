package com.vlocity.service;

import java.util.List;

import com.vlocity.domain.User;
import com.vlocity.dto.UserDto;

public interface UserService {

    User save(UserDto userDto);
    
    List<User> findAll();
    
    Boolean delete(Long id);

    User findByUsername(String username);

    User findById(Long id);

    User update(UserDto userDto);
}
