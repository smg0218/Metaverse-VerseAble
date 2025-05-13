package com.deeppoem.verseable.api.user.repository;

import com.deeppoem.verseable.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
