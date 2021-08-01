package com.oasis.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oasis.common.mapper.UserMapper;
import com.oasis.common.repository.UserRepository;
import com.oasis.common.vo.UserInfo;
import com.oasis.common.vo.UserVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

	@Slf4j
	@RequiredArgsConstructor
	@Service("userAuthService")
	public class UserAuthService {
	
	@Autowired
	public UserMapper mapper;
	
	public List<UserVo> getUser(){
		return mapper.getUser();
	}
	
	public UserVo getUserById(String user_id) {
		return mapper.getUserById(user_id);
	}
	
    public void updateFailureCount(String username) {
    	log.info("updateFailureCount >> " + username);
    	mapper.updateFailureCount(username);
    }
 
    public int checkFailureCount(String username) {
    	log.info("checkFailureCount >> " + username);
        return mapper.checkFailureCount(username);
    }
 
    public void disabledUsername(String username) {
    	mapper.updateFailureCount(username);
    }	
}