package com.vlocity.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vlocity.domain.Role;

@Repository
@Qualifier(value = "roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long> {
	
    Role findByName(String name);
    
}
