package com.deeppoem.verseable.api.result.repository;

import com.deeppoem.verseable.model.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllByUser_UserId(String userUserId);
}
