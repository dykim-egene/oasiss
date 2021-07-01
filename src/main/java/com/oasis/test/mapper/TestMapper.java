package com.oasis.test.mapper;

import java.util.List;
import com.oasis.test.vo.TestVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestMapper {
	List<TestVo> selectTest();
}
