package com.example.myserver.oauthserverpostgres.repository;

import com.example.myserver.oauthserverpostgres.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String name);
}
