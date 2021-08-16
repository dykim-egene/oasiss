package com.oasis.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.oasis.common.vo.UserVo;

@Repository
@Mapper
public interface UserMapper{
	List<UserVo> getUser();
	
	UserVo selectUserById(String email);
	
	int userCount(String email);
	
	
}