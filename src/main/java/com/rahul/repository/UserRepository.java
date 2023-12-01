package com.rahul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rahul.entity.Users;

public interface UserRepository extends JpaRepository<Users,Long > {

	Users findByEmail(String email);

	Users findByUsername(String username);

}
