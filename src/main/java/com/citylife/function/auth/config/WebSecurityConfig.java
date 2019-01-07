package com.citylife.function.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.citylife.function.auth.provider.PhoneAuthenticationProvider;
import com.citylife.function.auth.service.PhoneUserDetailService;

@Configuration
// @Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(phoneAuthenticationProvider());
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(6);
  }

  @Bean
  public PhoneAuthenticationProvider phoneAuthenticationProvider() {
    PhoneAuthenticationProvider provider = new PhoneAuthenticationProvider();
    // 设置userDetailsService
    provider.setUserDetailsService(phoneUserDetailService());
    // 禁止隐藏用户未找到异常
    provider.setHideUserNotFoundExceptions(false);
    // 使用BCrypt进行密码的hash
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public PhoneUserDetailService phoneUserDetailService() {
    PhoneUserDetailService bean = new PhoneUserDetailService();
    return bean;
  }

}
