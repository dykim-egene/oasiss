package com.oasis.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oasis.common.mapper.UserMapper;
import com.oasis.common.repository.UserRepository;
import com.oasis.common.vo.UserInfo;
import com.oasis.common.vo.UserVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

	@Slf4j
	@RequiredArgsConstructor
	@Service
	public class UserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	  /**
	   * Spring Security 필수 메소드 구현
	   *
	   * @param email 이메일
	   * @return UserDetails
	   * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
	   */
	@Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
	public UserInfo loadUserByUsername(String email) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
	  return userRepository.findByEmail(email)
	      .orElseThrow(() -> new UsernameNotFoundException((email)));
	}
	
	/**
	 * 회원정보 저장
	 *
	 * @param userVo
	 *            회원정보가 들어있는 DTO
	 * @return 저장되는 회원의 PK
	 */
	public Long save(UserVo userVo) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		userVo.setPasswd(encoder.encode(userVo.getPasswd()));

		return userRepository.save(
				UserInfo.builder().email(userVo.getEmail()).auth(userVo.getAuth()).passwd(userVo.getPasswd()).build())
				.getId();
	}	
	
	@Autowired
	public UserMapper mapper;
	
	public List<UserVo> getUser(){
		return mapper.getUser();
	}
	
	public UserVo getUserById(String user_id) {
		return mapper.getUserById(user_id);
	}
	
    public void updateFailureCount(String username) {
    	log.info("updateFailureCount >> " + username);
    	mapper.updateFailureCount(username);
    }
 
    public int checkFailureCount(String username) {
    	log.info("checkFailureCount >> " + username);
        return mapper.checkFailureCount(username);
    }
 
    public void disabledUsername(String username) {
    	mapper.updateFailureCount(username);
    }	
}