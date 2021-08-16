package com.oasis.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oasis.common.vo.UserVo;

@Repository("loginAuthDAO")
public class LoginAuthDAO {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
 
    public UserVo getUserById(String email) {
        return sqlSession.selectOne("com.oasis.common.mapper.UserMapper.selectUserById", email);
    }
 
    public void updateFailureCount(String email) {
        sqlSession.update("com.oasis.common.mapper.UserMapper.updateFailureCount", email);
    }
    
    public int checkFailureCount(String email) {
        return sqlSession.selectOne("com.oasis.common.mapper.UserMapper.checkFailureCount", email);
    }
}


