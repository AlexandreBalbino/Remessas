package com.remessas.remessas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remessas.remessas.entity.User;

public interface UsersRepository extends JpaRepository<User, Long> {

}
