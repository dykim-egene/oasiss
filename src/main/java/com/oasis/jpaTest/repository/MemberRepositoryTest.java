package com.oasis.jpaTest.repository; import com.oasis.jpaTest.vo.MemberVo; import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase; import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest; import org.springframework.transaction.annotation.Propagation; import org.springframework.transaction.annotation.Transactional; import java.util.List; import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;
	
	@Test void findById() {
		List<MemberVo> member = memberRepository.findById("oasis");
		then(!member.isEmpty());
		for(MemberVo vo : member){
			then("oasis").isEqualTo(vo.getId());
			then("갓대희").isEqualTo(vo.getName());
		}
	}
}