package com.fhcs.clothing_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
    
}
