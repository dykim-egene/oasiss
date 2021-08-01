package com.oasis.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.oasis.common.dao.LoginAuthDAO;
import com.oasis.common.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("loginSer")
public class LoginServiceImpl implements LoginService {
	
    @Resource(name="loginAuthDAO")
    private LoginAuthDAO loginAuthDAO;


    @Override
    public void countFailure(String username) {
    	log.info("countFailure >> " + username);
    	loginAuthDAO.updateFailureCount(username);
    }
 
    @Override
    public int checkFailureCount(String username) {
    	log.info("checkFailureCount >> " + username);
        return loginAuthDAO.checkFailureCount(username);
    }
 
    @Override
    public void disabledUsername(String username) {
    	log.info("disabledUsername >> " + username);
    	loginAuthDAO.updateFailureCount(username);
    }
}