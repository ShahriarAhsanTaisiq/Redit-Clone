package com.dev.springreditclone.repository;

import com.dev.springreditclone.model.User;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
}
