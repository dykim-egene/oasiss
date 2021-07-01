package com.oasis.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

	@RequestMapping(value = "/testValue", method = RequestMethod.GET)
	public String getTestValue() {
		String TestValue = "레스트컨트롤러 테스트";
		return TestValue;
	}
}
