package com.oasis.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.oasis.common.vo.UserVo;

@Repository
@Mapper
public interface UserMapper{
	List<UserVo> getUser();
	
	UserVo getUserById(String user_id);
	
	void updateFailureCount(String username);
	
	int checkFailureCount(String username);
	
	void disabledUsername(String username);	
	
	
	
}