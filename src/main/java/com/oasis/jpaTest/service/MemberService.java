package com.oasis.jpaTest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oasis.jpaTest.repository.MemberRepository;
import com.oasis.jpaTest.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;

	public List<MemberVo> findAll() {
		List<MemberVo> members = new ArrayList<>();
		memberRepository.findAll().forEach(e -> members.add(e));
		return members;
	}

	public Optional<MemberVo> findById(Long mbrNo) {
		Optional<MemberVo> member = memberRepository.findById(mbrNo);
		return member;
	}

	public void deleteById(Long mbrNo) {
		memberRepository.deleteById(mbrNo);
	}

	public MemberVo save(MemberVo member) {
		memberRepository.save(member);
		return member;
	}

	public void updateById(Long mbrNo, MemberVo member) {
		Optional<MemberVo> e = memberRepository.findById(mbrNo);
		if (e.isPresent()) {
			e.get().setMbrNo(member.getMbrNo());
			e.get().setId(member.getId());
			e.get().setName(member.getName());
			memberRepository.save(member);
		}
	}
	
	private RestTemplate restTemplate;
	public MemberService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	public MemberVo getMember(Long mbrNo) { 
		MemberVo response = restTemplate.getForObject("/memberTest/" + mbrNo, MemberVo.class); 
		log.info("getMember2 : " + response); return response; 
	}
}

