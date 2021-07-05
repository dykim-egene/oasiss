package com.oasis.test.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {

	@RequestMapping(value = "/testValue", produces = { MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.GET)
	public String getTestValue() {
		String TestValue = "{\"name\":\"갓대희1\"}";
		return TestValue;
	}
}
