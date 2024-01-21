package com.dev.springreditclone.repository;

import com.dev.springreditclone.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentRepository extends JpaRepository<Comment,Long> {
}
