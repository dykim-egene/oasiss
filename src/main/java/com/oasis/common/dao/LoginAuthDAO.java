package com.oasis.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.oasis.common.vo.UserVo;

@Repository("loginAuthDAO")
public class LoginAuthDAO {
    
    @Autowired
    private SqlSessionTemplate sqlSession;
 
    public UserVo getUserById(String username) {
        return sqlSession.selectOne("com.oasis.common.mapper.UserMapper.selectUserById", username);
    }
 
    public void updateFailureCount(String username) {
        sqlSession.update("com.oasis.common.mapper.UserMapper.updateFailureCount", username);
    }
    
    public int checkFailureCount(String username) {
        return sqlSession.selectOne("com.oasis.common.mapper.UserMapper.checkFailureCount", username);
    }
 
}


