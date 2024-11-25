package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
