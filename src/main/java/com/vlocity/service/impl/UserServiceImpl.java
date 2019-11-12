package com.vlocity.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vlocity.domain.Role;
import com.vlocity.domain.User;
import com.vlocity.dto.RoleDto;
import com.vlocity.dto.UserDto;
import com.vlocity.repository.UserRepository;
import com.vlocity.service.RoleService;
import com.vlocity.service.UserService;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;
    
    private EntityManager entityManager;
    
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this. entityManager = entityManager;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Boolean delete(Long id) {
        
        User existingUser = findById(id);
        if (existingUser == null)
            return false;

        entityManager.createNativeQuery("DELETE FROM User_Roles WHERE user_id="+id).executeUpdate();
        entityManager.createNativeQuery("DELETE FROM User WHERE id="+id).executeUpdate();
        
        User deletedUser = findById(id);
        if (deletedUser != null)
            return false;

        return true;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }

    @Override
    public User update(UserDto userDto) {
        User existingUser = findById(userDto.getId());
        User saved = null;
        if (existingUser != null) {
            BeanUtils.copyProperties(userDto, existingUser, "password");
            Set<RoleDto> roleDtos = userDto.getRoles();
            if (roleDtos != null && !roleDtos.isEmpty()) {
                Set<Role> newRoles = new HashSet<Role>();
                for (RoleDto roleDto : roleDtos) {
                    newRoles.add(roleService.findById(roleDto.getId()));
                }
                existingUser.setRoles(newRoles);
            }
            
            if (userDto.getPassword() != null && userDto.getPassword().trim().length() > 0) {
                existingUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            }
            
            saved = userRepository.save(existingUser);
            
        }

        return saved;
    }

    @Override
    public User save(UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        newUser.setUserType(userDto.getUserType());
        newUser.setDeleted(userDto.isDeleted());

        Set<RoleDto> roleDtos = userDto.getRoles();
        if (roleDtos != null && !roleDtos.isEmpty()) {
            Set<Role> newRoles = new HashSet<Role>();
            for (RoleDto roleDto : roleDtos) {
                newRoles.add(roleService.findById(roleDto.getId()));
            }
            newUser.setRoles(newRoles);
        }

        return userRepository.save(newUser);
    }
}
