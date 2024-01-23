package com.dev.springreditclone.repository;

import com.dev.springreditclone.model.User;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

