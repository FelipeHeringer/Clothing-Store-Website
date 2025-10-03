package com.fhcs.clothing_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    public User findByEmail(String email);

    public User findByUsername(String username);
}
