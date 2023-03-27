package com.example.demospringboot.repository;

import com.example.demospringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPassword(String email, String password);
}
