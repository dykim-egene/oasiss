package com.oasis.jpaTest.vo;

import org.junit.jupiter.api.Test;

public class UserTest {
  @Test  public void builderTest() {
    User user = User.builder()
                  .name("홍길동")
                  .age(19)
                  .build();
    System.out.println(user);
  }
}


