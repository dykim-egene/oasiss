package com.oasis.jpaTest.service;

import com.oasis.jpaTest.repository.MemberRepository;
import com.oasis.jpaTest.vo.MemberVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(MemberService.class)
class MemberServiceTest {
	@MockBean
	private MemberRepository memberRepository;

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private MemberService memberService;

	@Test
	void getMember() {
		server.expect(requestTo("/memberTest/1"))
				.andRespond(withSuccess(new ClassPathResource("/test.json", getClass()), MediaType.APPLICATION_JSON));
		// MemberVo member = memberService.getMember(1L);
		MemberVo member = memberService.getMember(1L);

		// ※ Junit4 사용시
		// assertThat("goddaehee2").isEqualTo(member.getId());
		// assertThat("갓대희"").isEqualTo(member..getName());

		// Junit5 BDD 사용시
		then("goddaehee2").isEqualTo(member.getId());
		then("갓대희2").isEqualTo(member.getName());
	}
}
