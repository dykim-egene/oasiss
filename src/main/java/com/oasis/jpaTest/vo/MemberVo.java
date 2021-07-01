package com.oasis.jpaTest.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
public class MemberVo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long mbrNo;
	private String id;
	private String name;

	//No default constructor for entity 예외
	private MemberVo() {
	}
	
	@Builder
	public MemberVo(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Long getMbrNo() {
		return mbrNo;
	}
	
	public void setMbrNo(Long mbrNo) {
		this.mbrNo = mbrNo;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
