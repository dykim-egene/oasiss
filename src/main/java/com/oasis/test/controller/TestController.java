package com.oasis.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oasis.test.service.TestService;
import com.oasis.test.vo.TestVo;

@Controller
public class TestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/home")
	public String home() {
		return "index.html";
	}

	@ResponseBody
	@RequestMapping("/valueTest")
	public String valueTest() {
		String value = "테스트 String";
		return value;
	}

	@RequestMapping("/test1")
	public ModelAndView test1() throws Exception {
		ModelAndView mav = new ModelAndView("test1");
		mav.addObject("name", "oasis");
		List<String> testList = new ArrayList<String>();
		testList.add("a");
		testList.add("b");
		testList.add("c");
		mav.addObject("list", testList);
		return mav;
	}

	@Autowired
	TestService testService;

	@RequestMapping(value = "/test2")
	public ModelAndView test2() throws Exception {
		logger.trace("Trace Level 테스트");
		logger.debug("DEBUG Level 테스트");
		logger.info("INFO Level 테스트");
		logger.warn("Warn Level 테스트");
		logger.error("ERROR Level 테스트");		
		
		ModelAndView mav = new ModelAndView("test2");
		List<TestVo> testList = testService.selectTest();
		mav.addObject("list", testList);
		return mav;
	}

}