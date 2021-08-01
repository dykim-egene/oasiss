package com.oasis.common.service;

public interface LoginService{
	void countFailure(String username);
	
	int checkFailureCount(String username);
	
	void disabledUsername(String username);
	
}