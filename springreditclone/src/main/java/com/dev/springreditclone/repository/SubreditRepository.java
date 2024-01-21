package com.dev.springreditclone.repository;

import com.dev.springreditclone.model.Subredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubreditRepository extends JpaRepository<Subredit,Long> {
}
