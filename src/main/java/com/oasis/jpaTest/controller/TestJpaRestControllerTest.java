package com.oasis.jpaTest.controller;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.oasis.jpaTest.repository.MemberRepository;
import com.oasis.jpaTest.service.MemberService;
import com.oasis.jpaTest.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

//@RunWith(SpringRunner.class) // ※ Junit4 사용시 
@SpringBootTest(properties = { "testId=oasis", "testName=홍길동" }
// classes = {TestJpaRestController.class, MemberService.class},
		, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@AutoConfigureMockMvc
@Slf4j
public class TestJpaRestControllerTest {
	@Value("${testId}")
	private String testId;
	@Value("${testName}")
	private String testName;
	/* @MockBean private MemberRepository memberRepository; */
	@Autowired
	MockMvc mvc;

	@Autowired
	private TestRestTemplate restTemplate;
	
	// Service로 등록하는 빈
	@Autowired
	private MemberService memberService;

	@Autowired
	private WebApplicationContext ctx;

	@BeforeEach() // Junit4의 @Before
	public void setup() {
		this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터추가
				.alwaysDo(print()).build();
	}

	@Test
	void getMember() throws Exception {
		log.info("##### Properties 테스트 #####"); 
		log.info("testId : " + testId); 
		log.info("testName : " + testName);

		/******** START : MOC MVC test **********/ 
		log.info("******** START : MOC MVC test **********");
		mvc.perform(get("/memberTest/2"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.id", is("oasis")))
		.andDo(print());
		log.info("******** END : MOC MVC test **********");
		/******** END : MOC MVC test **********/
		
		/******** START : TestRestTemplate test **********/
		log.info("******** START : TestRestTemplate test **********");
		ResponseEntity<MemberVo> response = restTemplate.getForEntity("/memberTest/2", MemberVo.class);
		then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		then(response.getBody()).isNotNull();
		log.info("******** END : TestRestTemplate test **********");
		/******** END : TestRestTemplate test **********/
		
		/******** START : MockBean test **********/
		log.info("******** START : MockBean test **********");
		
		/* MemberVo memberVo = MemberVo.builder().id(testId) .name(testName) .build(); given(memberRepository.findById(1L)) .willReturn(Optional.of(memberVo));*/
		
		Optional<MemberVo> member = memberService.findById(2L);
		if (member.isPresent()) { 
			// ※ Junit4 사용시 
			// assertThat(memberVo.getId()).isEqualTo(member.get().getId()); // assertThat(memberVo.getName()).isEqualTo(member.get().getName()); 
			
			// Junit5 BDD 사용시 
			then("oasis1").isEqualTo(member.get().getId());
			then("홍길동").isEqualTo(member.get().getName());
		}
		log.info("******** END : MockBean test **********"); 
		/******** END : MockBean test **********/
	}
	
	@MockBean
	private MemberRepository memberRepository;

	@Test
	void getVMember() throws Exception {
		/******** START : MockBean test **********/
		log.info("******** START : MockBean test **********");
		
		MemberVo memberVo = MemberVo.builder()
				.id("goddaehee2")
				.name("갓대희")
				.build();
		
		given(memberRepository.findById(1L))
		.willReturn(Optional.of(memberVo));
		
		Optional<MemberVo> member = memberService.findById(1L);
		
		if (member.isPresent()) { 
			// ※ Junit4 사용시 
			// assertThat(memberVo.getId()).isEqualTo(member.get().getId()); 
			// assertThat(memberVo.getName()).isEqualTo(member.get().getName()); 
			// Junit5 BDD 사용시
			then("goddaehee").isEqualTo(member.get().getId());
			then("갓대희").isEqualTo(member.get().getName()); 
		}
		log.info("******** END : MockBean test **********");
		/******** END : MockBean test **********/ 
	}

}
