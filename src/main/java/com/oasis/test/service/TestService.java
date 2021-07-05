package com.oasis.test.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oasis.test.mapper.TestMapper;
import com.oasis.test.vo.TestVo;

@Service
public class TestService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public TestMapper mapper;

	public List<TestVo> selectTest() {

		logger.trace("Trace Level 테스트");
		logger.debug("DEBUG Level 테스트");
		logger.info("INFO Level 테스트");
		logger.warn("Warn Level 테스트");
		logger.error("ERROR Level 테스트");

		return mapper.selectTest();
	}
	
	public TestVo selectOneMember(String id) {
		return mapper.selectOneMember(id);
	}
}
