package com.oasis.test.controller; 

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.oasis.test.service.TestService;
import com.oasis.test.vo.TestVo;

import lombok.extern.slf4j.Slf4j;
//@RunWith(SpringRunner.class)// ※ Junit4 사용시
@WebMvcTest(TestRestController.class)
@Slf4j
class TestRestControllerTest {
	@Autowired
	MockMvc mvc;
	
	@MockBean
	private TestService testService;
	
	@Autowired
	private WebApplicationContext ctx;

	@BeforeEach() // Junit4의 @Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터추가
				.alwaysDo(print()).build();
	}
	
	@Test
	void getListTest() throws Exception { 
		//given
		TestVo testVo = TestVo.builder()
				.id("goddaehee")
				.name("갓대희")
				.build();
		//given
		given(testService.selectOneMember("goddaehee1"))
		.willReturn(testVo); 
		
		//when
		final ResultActions actions = mvc.perform(get("/testValue")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andDo(print());
		//then
		actions
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.name", is("갓대희")))
		.andDo(print());
	}
}

