package com.oasis.jpaTest.vo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MemberVoTest {

	@Test
	public void getId() {
		final MemberVo memberVo = MemberVo.builder().id("goddaehee").name("갓대희").build();
		final String id = memberVo.getId();
		assertEquals("goddaehee", id);
	}

	@Test
	public void getName() {
		final MemberVo memberVo = MemberVo.builder().id("goddaehee").name("갓대희").build();
		final String name = memberVo.getName();
		assertEquals("대희", name);
	}

}
