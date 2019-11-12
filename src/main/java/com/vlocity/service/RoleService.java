package com.vlocity.service;

import java.util.List;

import com.vlocity.domain.Role;
import com.vlocity.dto.RoleDto;

public interface RoleService {

    Role save(RoleDto roleDto);

    List<Role> findAll();

    Boolean delete(Long id);

    Role findByName(String name);

    Role findById(Long id);

    Role update(RoleDto roleDto);
}
