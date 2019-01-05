package com.citylife.function.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

  public static void main(String[] args) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(6);
    System.out.println(encoder.encode("1234"));

  }

}
