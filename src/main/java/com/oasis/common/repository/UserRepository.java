package com.oasis.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oasis.common.vo.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
	  Optional<UserInfo> findByEmail(String email);
}