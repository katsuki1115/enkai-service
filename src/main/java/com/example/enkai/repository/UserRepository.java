package com.example.enkai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.enkai.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    public User findByEmail(String email);
}