package com.vlocity.service.impl;

import com.vlocity.domain.Role;
import com.vlocity.dto.RoleDto;
import com.vlocity.repository.RoleRepository;
import com.vlocity.service.RoleService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Role> findAll() {
        List<Role> list = new ArrayList<>();
        roleRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Boolean delete(Long id) {
        Role existingRole = findById(id);
        if (existingRole == null)
            return false;

        entityManager.createNativeQuery("DELETE FROM User_Roles WHERE role_id=" + id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM Role WHERE id=" + id).executeUpdate();

        Role deletedRole = findById(id);
        if (deletedRole != null)
            return false;

        return true;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role findById(Long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.isPresent() ? optionalRole.get() : null;
    }

    @Override
    public Role update(RoleDto roleDto) {
        Role existingRole = findById(roleDto.getId());
        Role saved = null;
        if (existingRole != null) {
            BeanUtils.copyProperties(roleDto, existingRole);
            saved = roleRepository.save(existingRole);
        }

        return saved;
    }

    @Override
    public Role save(RoleDto roleDto) {
        Role newRole = new Role();
        newRole.setName(roleDto.getName());
        newRole.setDescription(roleDto.getDescription());
        newRole.setDeleted(roleDto.isDeleted());

        return roleRepository.save(newRole);
    }
}
